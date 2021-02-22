package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.client.js.react.common.iconButton
import kotlinx.css.Contain
import kotlinx.css.px
import kotlinx.css.rem
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.div
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

            statusSwitch {
                size = 3.rem
                status = props.task.status
            }

            styledDiv {

                css { +TaskItemStyles.content }

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

            styledDiv {

                css { +TaskItemStyles.actions }

                iconButton {
                    iconSize = 2.rem
                    iconStyle = "fas"
                    iconName = "fa-trash-alt"
                }
                iconButton {
                    iconSize = 2.rem
                    iconStyle = "fas"
                    iconName = "fa-pen"
                }
            }
        }
    }


    private fun formatDate(timestamp: Long): String {

        return Date(timestamp).toISOString()
    }
}

fun RBuilder.taskItem(props: TaskItemProps.() -> Unit) = child(TaskItem::class) { attrs(props) }