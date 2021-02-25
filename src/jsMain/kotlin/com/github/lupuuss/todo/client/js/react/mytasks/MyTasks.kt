package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.repository.MyTaskRepository
import com.github.lupuuss.todo.client.core.repository.TaskListener
import com.github.lupuuss.todo.client.js.react.mytasks.newtask.newTask
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.*
import org.kodein.di.instance
import org.w3c.dom.events.Event
import react.*
import styled.css
import styled.styledDiv

external interface MyTasksState : RState {
    var tasks: MutableList<Task>
}

class MyTasks : RComponent<dynamic, MyTasksState>(), CoroutineScope by MainScope(), TaskListener {

    private val repository: MyTaskRepository by TodoKodein.di.instance()

    init {
        state.tasks = mutableListOf()
    }

    override fun componentDidMount() {

        repository.addTasksListener(this@MyTasks)
        window.onscroll = this::onScroll

        repository.requireInitialLoad()
    }

    private fun onScroll(event: Event) {
        if (window.scrollY > (document.body!!.offsetHeight - window.outerHeight)) {
            repository.loadMore()
        }
    }

    override fun componentWillUnmount() {
        repository.removeTasksListener(this)
        cancel()
    }

    override fun onTasksAppend(tasks: List<Task>) {
        setState {
            this.tasks.addAll(tasks)
        }
    }

    override fun onNewTask(task: Task) {
        setState {
            this.tasks.add(0, task)
        }
    }

    override fun onTaskChanged(task: Task) {

        val index = state.tasks.indexOfFirst { it.id == task.id }

        setState {
            tasks[index] = task
        }
    }

    override fun onTasksRemoved(ids: List<String>) {
        setState {
            this.tasks.removeAll { ids.contains(it.id) }
        }
    }

    override fun RBuilder.render() {

        styledDiv {
            css { +MyTasksStyles.container }

            newTask()

            state.tasks.map {
                taskItem {
                    key = it.id
                    task = it
                }
            }

        }
    }
}

fun RBuilder.myTasks(): ReactElement = child(MyTasks::class) {}