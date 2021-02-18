package com.github.lupuuss.todo.client.core.repository

import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.client.core.api.me.CurrentUserApi
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface TaskListener : CoroutineScope {

    fun onTasksAppend(tasks: List<Task>, group: Task.Status)
    fun onNewTask(task: Task)
    fun onTaskChanged(task: Task, oldGroup: Task.Status, newGroup: Task.Status?)
    fun onTaskRemoved(id: String, group: Task.Status)
}

class MyTaskRepository(
    private val taskApi: CurrentUserApi,
    context: CoroutineContext
): CoroutineScope by CoroutineScope(context) {

    private val listeners = mutableListOf<TaskListener>()

    private val statusByPriority = listOf(Task.Status.IN_PROGRESS, Task.Status.NOT_STARTED, Task.Status.DONE)

    private val tasksGroups = statusByPriority.map { it to LinkedHashMap<String, Task>() }.toMap()

    private var currentStatusIndex = 0

    private val pageSize = 10
    private var currentPage = 0

    private var currentJob: Job? = null

    fun addTasksListener(listener: TaskListener) {
        listeners.add(listener)

        for ((status, group) in tasksGroups) {

            listener.launch {
                notifyAppendItems(group.values.toList(), status)
            }
        }
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

        var availableMore: Boolean

        var inserted = 0
        do {

            val currentStatus = statusByPriority[currentStatusIndex]

            val page = taskApi.getMyTasks(currentPage, pageSize, currentStatus)

            val group = tasksGroups[currentStatus]!!

            val insertedList = mutableListOf<Task>()

            for (task in page.elements) {

                if (!group.containsKey(task.id)) {

                    group[task.id] = task
                    insertedList += task

                    inserted++
                }
            }

            notifyAppendItems(insertedList, currentStatus)

            availableMore = page.nextPage != null || currentStatusIndex != statusByPriority.lastIndex

            if (page.nextPage == null && currentStatusIndex != statusByPriority.lastIndex) {
                currentStatusIndex++
            }

        } while(availableMore && inserted < pageSize)

    }

    private fun notifyAppendItems(insertedList: List<Task>, status: Task.Status) {

        if (insertedList.isEmpty()) return

        for (listener in listeners) {

            listener.launch {
                listener.onTasksAppend(insertedList, status)
            }
        }
    }

}