package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.api.core.task.PatchTask
import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.repository.MyTaskRepository
import com.github.lupuuss.todo.client.core.repository.OperationFailed
import com.github.lupuuss.todo.client.js.react.common.editableText
import com.github.lupuuss.todo.client.js.react.common.iconButton
import com.github.lupuuss.todo.client.js.react.mytasks.newtask.NewTaskFormStyles
import io.ktor.util.Identity.decode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.fontSize
import kotlinx.css.padding
import kotlinx.css.rem
import kotlinx.html.js.onChangeFunction
import org.kodein.di.instance
import org.w3c.dom.events.Event
import react.*
import styled.css
import styled.styledDiv
import styled.styledInput
import kotlin.js.Date

external interface TaskItemProps : RProps {
    var task: Task
}

external interface TaskItemState : RState {
    var isEditable: Boolean
    var newName: String
    var newDescription: String?
}

class TaskItem(props: TaskItemProps): RComponent<TaskItemProps, TaskItemState>(), CoroutineScope by MainScope() {

    init {
        state.isEditable = false
        state.newName = props.task.name
        state.newDescription = props.task.description
    }

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

                editableText {
                    isEditable = state.isEditable
                    value = state.newName
                    placeholder = "Name..."
                    onChange = this@TaskItem::onNameChanged
                    textArea = false
                    css = TaskItemStyles.editableText
                }

                editableText {
                    isEditable = state.isEditable
                    value = state.newDescription ?: ""
                    placeholder = "Description..."
                    onChange = this@TaskItem::onDescriptionChanged
                    textArea = true
                    css = TaskItemStyles.editableText
                }
            }

            styledDiv {
                css { +TaskItemStyles.date }
                +formatDate(props.task.timestamp)
            }


            styledDiv {

                css { +TaskItemStyles.actions }

                if (state.isEditable) {

                    iconButton {
                        iconSize = 2.rem
                        iconStyle = "fas"
                        iconName = "fa-check"
                        onClick = this@TaskItem::onClickSave
                    }

                    iconButton {
                        iconSize = 2.rem
                        iconStyle = "fas"
                        iconName = "fa-times"
                        onClick = this@TaskItem::onClickReject
                    }

                } else {

                    iconButton {
                        iconSize = 2.rem
                        iconStyle = "fas"
                        iconName = "fa-pen"
                        onClick = this@TaskItem::onClickEdit
                    }

                    iconButton {
                        iconSize = 2.rem
                        iconStyle = "fas"
                        iconName = "fa-trash-alt"
                        onClick = this@TaskItem::onClickDelete
                    }
                }

            }
        }
    }

    private fun onClickReject(event: Event) {
        setState {
            isEditable = false
            newName = props.task.name
            newDescription = props.task.description
        }
    }

    private fun onClickSave(event: Event) {

        if (props.task.name == state.newName && props.task.description == state.newDescription) {
            setState {
                isEditable = false
            }
            return
        }


        launch {

            try {

                val patch = PatchTask()

                patch.name = state.newName
                patch.description = state.newDescription

                repository.changeTask(props.task.id, patch)

            } catch (e: OperationFailed) {

                e.printStackTrace()
            }

            setState {
                isEditable = false
            }
        }
    }

    private fun onNameChanged(value: String) {
        setState {
            newName = value
        }
    }

    private fun onDescriptionChanged(value: String) {
        setState {
            newDescription = value
        }
    }

    private fun onClickStatus(event: Event) {
        launch {

            try {
                repository.changeTaskStatus(props.task.id, props.task.status.next())
            } catch (e: OperationFailed) {
                e.printStackTrace()
            }

        }
    }

    private fun onClickEdit(event: Event) {
        setState {
            isEditable = true
        }
    }


    private fun onClickDelete(event: Event) {
        launch {

            try {
                repository.deleteTask(props.task.id)
            } catch (e: OperationFailed) {
                e.printStackTrace()
            }
        }
    }


    private fun formatDate(timestamp: Long): String {

        return "2 days ago"
    }
}

fun RBuilder.taskItem(props: TaskItemProps.() -> Unit) = child(TaskItem::class) { attrs(props) }