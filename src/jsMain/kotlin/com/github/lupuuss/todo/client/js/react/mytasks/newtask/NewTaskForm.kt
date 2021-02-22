package com.github.lupuuss.todo.client.js.react.mytasks.newtask

import com.github.lupuuss.todo.api.core.task.NewTask
import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.repository.MyTaskRepository
import com.github.lupuuss.todo.client.js.react.common.loadingButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.pct
import kotlinx.css.width
import kotlinx.html.ButtonType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import org.kodein.di.instance
import org.w3c.dom.events.Event
import react.*
import styled.*
import styled.styledDiv

external interface NewTaskFormProps : RProps {
    var onFormFinished: () -> Unit
}

external interface NewTaskFormState : RState {
    var isLoading: Boolean
    var name: String
    var description: String
}

class NewTaskForm : RComponent<NewTaskFormProps, NewTaskFormState>(), CoroutineScope by MainScope() {

    private val repository: MyTaskRepository by TodoKodein.di.instance()

    init {
        state.isLoading = false
        state.name = ""
        state.description = ""
    }

    override fun RBuilder.render() {
        styledForm {
            css { +NewTaskFormStyles.container }

            attrs {
                onSubmitFunction = this@NewTaskForm::onSubmit
            }

            styledInput {
                css { +NewTaskFormStyles.input }
                attrs {
                    value = state.name
                    placeholder = "Name..."
                    onChangeFunction = this@NewTaskForm::onNameChanged
                }
            }

            styledInput {
                css { +NewTaskFormStyles.input }
                attrs {
                    value = state.description
                    placeholder = "Description..."
                    onChangeFunction = this@NewTaskForm::onDescriptionChanged
                }
            }

            styledDiv {
                css { +NewTaskFormStyles.buttons }

                loadingButton {
                    text = "Save"
                    isLoading = state.isLoading
                    type = ButtonType.submit
                    css = {
                        it.width = 100.pct
                    }
                }

                styledButton {

                    css { width = 100.pct }

                    +"Reject"

                    attrs {
                        onClickFunction = this@NewTaskForm::onClickReject
                    }
                }
            }
        }
    }

    private fun onNameChanged(event: Event) {
        val value = event.target.asDynamic().value as String
        setState {
            name = value
        }
    }

    private fun onDescriptionChanged(event: Event) {
        val value = event.target.asDynamic().value as String
        setState {
            description = value
        }
    }

    private fun onSubmit(event: Event) {

        event.preventDefault()

        launch {

            setState {
                isLoading = true
            }


            val ok = try {

                val newTask = NewTask(state.name, state.description)
                repository.addNewTask(newTask)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }

            setState {
                isLoading = false
            }

            if (ok) {
                props.onFormFinished()
            }
        }
    }

    private fun onClickReject(event: Event) {
        props.onFormFinished()
    }
}

fun RBuilder.newTaskForm(props: NewTaskFormProps.() -> Unit) = child(NewTaskForm::class) { attrs(props) }