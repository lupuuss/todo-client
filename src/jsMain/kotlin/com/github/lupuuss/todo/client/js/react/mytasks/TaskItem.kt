package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.api.core.task.Task
import react.RBuilder
import react.RComponent
import react.RProps
import styled.css
import styled.styledDiv
import kotlin.js.Date

external interface TaskItemProps : RProps {
    var task: Task
}

class TaskItem : RComponent<TaskItemProps, dynamic>() {

    override fun RBuilder.render() {
        styledDiv {
            css { +TaskItemStyles.container }

            styledDiv {

                css { +TaskItemStyles.nameLine }

                styledDiv {
                    css { +TaskItemStyles.font }
                    +props.task.name
                }

                styledDiv {
                    css { +TaskItemStyles.font }
                    +formatDate(props.task.timestamp)
                }
            }

            styledDiv {
                css { +TaskItemStyles.font }
                +(props.task.description ?: "-")
            }
        }
    }


    private fun formatDate(timestamp: Long): String {

        return Date(timestamp).toISOString()
    }
}

fun RBuilder.taskItem(props: TaskItemProps.() -> Unit) = child(TaskItem::class) { attrs(props) }