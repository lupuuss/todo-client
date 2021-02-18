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

    private val tasksGroups = mapOf<Task.Status, MutableList<Task>>(
        Task.Status.IN_PROGRESS to mutableListOf(),
        Task.Status.NOT_STARTED to mutableListOf(),
        Task.Status.DONE to mutableListOf()
    )

    override fun componentDidMount() {

        repository.addTasksListener(this@MyTasks)
        repository.loadMore()
    }

    override fun componentWillUnmount() {
        repository.removeTasksListener(this)
        cancel()
    }

    override fun onTasksAppend(tasks: List<Task>, group: Task.Status) {
        tasksGroups[group]!!.addAll(tasks)
        forceUpdate()
    }

    override fun onNewTask(task: Task) {
        tasksGroups[task.status]!!.add(task)
        forceUpdate()
    }

    override fun onTaskChanged(task: Task, oldGroup: Task.Status, newGroup: Task.Status?) {

        val group = newGroup ?: oldGroup

        tasksGroups[oldGroup]!!.removeAll { it.id == task.id }
        tasksGroups[group]!!.add(task)

        forceUpdate()
    }

    override fun onTaskRemoved(id: String, group: Task.Status) {
        tasksGroups[group]!!.removeAll { it.id == id }
        forceUpdate()
    }

    override fun RBuilder.render() {

        styledDiv {
            css { +MyTasksStyles.container }

            +"IN PROGRESS"
            ul {
                tasksGroups[Task.Status.IN_PROGRESS]!!.map { li { +it.name } }
            }

            +"NOT STARTED"
            ul {
                tasksGroups[Task.Status.NOT_STARTED]!!.map { li { +it.name } }
            }

            +"DONE"
            ul {
                tasksGroups[Task.Status.DONE]!!.map { li { +it.name } }
            }

        }
    }
}

fun RBuilder.myTasks(): ReactElement = child(MyTasks::class) {}