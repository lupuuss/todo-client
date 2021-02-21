package com.github.lupuuss.todo.client.core.repository

import com.github.lupuuss.todo.api.core.live.ItemChange
import com.github.lupuuss.todo.api.core.live.Operation.*
import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.api.core.user.User
import com.github.lupuuss.todo.client.core.api.live.LiveApi
import com.github.lupuuss.todo.client.core.api.me.MyTasksApi
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface TaskListener : CoroutineScope {

    fun onTasksAppend(tasks: List<Task>)
    fun onNewTask(task: Task)
    fun onTaskChanged(task: Task)
    fun onTasksRemoved(ids: List<String>)
}

class MyTaskRepository(
    private val taskApi: MyTasksApi,
    private val liveApi: LiveApi,
    context: CoroutineContext
): CoroutineScope by CoroutineScope(context), LiveApi.Listener {

    private val listeners = mutableListOf<TaskListener>()

    private var tasks = LinkedHashMap<String, Task>()

    private val pageSize = 10
    private var currentPage = 0

    private var currentJob: Job? = null

    fun init() {
        liveApi.addOnChangeListener(this)
    }

    fun close() {
        liveApi.removeOnChangeListener(this)
    }

    private fun callListeners(calls: TaskListener.() -> Unit) = launch {
        listeners.forEach { listener ->

            withContext(listener.coroutineContext) {

                listener.calls()
            }
        }
    }

    override fun onTaskChange(change: ItemChange<Task>) {

        when (change.opType) {
            DELETE -> handleDelete(change)
            DELETE_ALL -> handleAllDelete()
            INSERT -> handleInsert(change)
            UPDATE -> handleUpdate(change)
        }
    }

    override fun onUserChange(change: ItemChange<User>) = Unit

    private fun handleUpdate(change: ItemChange<Task>) {
        val id = change.id!!
        val task = change.item!!

        if (tasks.containsKey(id)) {
            tasks[id] = task

            callListeners { onTaskChanged(task) }
        }
    }

    private fun handleInsert(change: ItemChange<Task>) {

        val newTasks = LinkedHashMap<String, Task>(tasks.size + 1)

        newTasks[change.id!!] = change.item!!
        newTasks.putAll(tasks)

        tasks = newTasks

        callListeners {
            onNewTask(change.item!!)
        }
    }

    private fun handleDelete(change: ItemChange<Task>) {
        val removedTask = tasks.remove(change.id) ?: return

        callListeners {
            onTasksRemoved(listOf(removedTask.id))
        }
    }

    private fun handleAllDelete() {

        callListeners {
            onTasksRemoved(tasks.map { it.value.id })
        }
        tasks.clear()
    }

    fun addTasksListener(listener: TaskListener) {

        listeners.add(listener)

        notifyAppendItems(tasks.values.toList())
    }

    fun removeTasksListener(listener: TaskListener) {
        listeners.remove(listener)
    }

    fun loadMore() {
        if (currentJob != null) {
            return
        }

        currentJob = startLoadMore()
    }

    private fun startLoadMore() = launch {

        var inserted = 0
        do {


            val page = taskApi.getMyTasks(currentPage, pageSize)

            val insertedList = mutableListOf<Task>()

            for (task in page.elements) {

                if (!tasks.containsKey(task.id)) {

                    tasks[task.id] = task
                    insertedList += task

                    inserted++
                }
            }

            notifyAppendItems(insertedList)

            page.nextPage?.let {
                currentPage = it
            }

        } while(page.nextPage != null && inserted < pageSize)

    }

    private fun notifyAppendItems(insertedList: List<Task>) {

        if (insertedList.isEmpty()) return

        callListeners {
            onTasksAppend(insertedList)
        }
    }

}