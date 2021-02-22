package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.repository.MyTaskRepository
import com.github.lupuuss.todo.client.js.react.common.iconButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.rem
import org.kodein.di.instance
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import styled.css
import styled.styledDiv
import kotlin.js.Date

external interface TaskItemProps : RProps {
    var task: Task
}

class TaskItem : RComponent<TaskItemProps, dynamic>(), CoroutineScope by MainScope() {

    private val repository: MyTaskRepository by TodoKodein.di.instance()

    override fun RBuilder.render() {
        styledDiv {

            css { +TaskItemStyles.container }

            statusSwitch {
                size = 3.rem
                status = props.task.status
                onClick = this@TaskItem::onClickStatus
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
                    onClick = this@TaskItem::onClickDelete
                }
                iconButton {
                    iconSize = 2.rem
                    iconStyle = "fas"
                    iconName = "fa-pen"
                    onClick = this@TaskItem::onClickEdit
                }
            }
        }
    }

    private fun onClickStatus(event: Event) {
        launch {
            try {
                repository.changeTaskStatus(props.task.id, props.task.status.next())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun onClickEdit(event: Event) {

    }


    private fun onClickDelete(event: Event) {

    }


    private fun formatDate(timestamp: Long): String {

        return Date(timestamp).toISOString()
    }
}

fun RBuilder.taskItem(props: TaskItemProps.() -> Unit) = child(TaskItem::class) { attrs(props) }