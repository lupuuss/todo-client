package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.repository.MyTaskRepository
import com.github.lupuuss.todo.client.core.repository.TaskListener
import kotlinx.coroutines.*
import org.kodein.di.instance
import react.RBuilder
import react.RComponent
import react.ReactElement
import react.dom.li
import react.dom.ul
import styled.css
import styled.styledDiv

class MyTasks : RComponent<dynamic, dynamic>(), CoroutineScope by MainScope(), TaskListener {

    private val repository: MyTaskRepository by TodoKodein.di.instance()

    private val tasks = mutableListOf<Task>()

    override fun componentDidMount() {

        repository.addTasksListener(this@MyTasks)
        repository.loadMore()
    }

    override fun componentWillUnmount() {
        repository.removeTasksListener(this)
        cancel()
    }

    override fun onTasksAppend(tasks: List<Task>) {
        this.tasks.addAll(tasks)
        forceUpdate()
    }

    override fun onNewTask(task: Task) {
        this.tasks.add(0, task)
        forceUpdate()
    }

    override fun onTaskChanged(task: Task) {

        val index = tasks.indexOfFirst { it.id == task.id }

        tasks[index] = task

        forceUpdate()
    }

    override fun onTasksRemoved(ids: List<String>) {
        this.tasks.removeAll { ids.contains(it.id) }
        forceUpdate()
    }

    override fun RBuilder.render() {

        styledDiv {
            css { +MyTasksStyles.container }

            tasks.map {
                taskItem {
                    key = it.id
                    task = it
                    onClickDelete = this@MyTasks::onClickDeleteTask
                    onClickEdit = this@MyTasks::onClickEditTask
                    onClickStatus = this@MyTasks::onClickStatusTask
                }
            }

        }
    }

    private fun onClickStatusTask(id: String, status: Task.Status) {
        TODO("Not yet implemented")
    }

    private fun onClickEditTask(id: String) {
        TODO("Not yet implemented")
    }

    private fun onClickDeleteTask(id: String) {
        TODO("Not yet implemented")
    }
}

fun RBuilder.myTasks(): ReactElement = child(MyTasks::class) {}