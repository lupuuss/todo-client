package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.repository.TaskRepository
import com.github.lupuuss.todo.client.js.react.Colors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.css.*
import org.kodein.di.direct
import org.kodein.di.instance
import react.RBuilder
import react.RComponent
import styled.css
import styled.styledDiv

class TodoApp : RComponent<dynamic, dynamic>() {

    override fun componentWillMount() {
        val repo = TodoKodein.di.direct.instance<TaskRepository>()

        GlobalScope.launch {
            async {
                println(repo.getTodoTasks(0, 10))
            }
        }
    }

    override fun RBuilder.render() {

        child(TopBar::class) {}

        styledDiv {
            css {
                width = LinearDimension.auto
                height = 100.pct
                backgroundColor = Colors.secondary
            }
        }
    }
}