package com.github.lupuuss.todo.client.js.react.mytasks.newtask

import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RState
import react.setState
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledInput

external interface NewTaskState : RState {
    var available: Boolean
}

class NewTask : RComponent<dynamic, NewTaskState>() {

    init {
        state.available = false
    }

    override fun RBuilder.render() {
        styledButton {
            css { +NewTaskStyles.container(state.available) }

            if (state.available) {

                newTaskForm() {
                    onFormFinished = this@NewTask::onFormFinished
                }

            } else {
                styledDiv {
                    css {
                        classes = mutableListOf("fas", "fa-plus")
                        +NewTaskStyles.icon
                    }
                }
            }

            attrs {
                disabled = state.available
                onClickFunction = this@NewTask::onComponentClick
            }
        }
    }

    private fun onFormFinished() {
        setState {
            available = false
        }
    }

    private fun onComponentClick(event: Event) {
        setState {
            available = true
        }
    }
}

fun RBuilder.newTask() = child(NewTask::class) {}