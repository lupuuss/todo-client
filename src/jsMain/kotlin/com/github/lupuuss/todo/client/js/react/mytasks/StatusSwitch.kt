package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.api.core.task.Task
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import react.RBuilder
import react.RComponent
import react.RProps
import styled.css
import styled.styledButton
import styled.styledP

external interface StatusSwitchProps : RProps {
    var status: Task.Status
    var size: LinearDimension
}

class StatusSwitch : RComponent<StatusSwitchProps, dynamic>() {
    override fun RBuilder.render() {

        val iconColorBackground = when(props.status) {
            Task.Status.NOT_STARTED -> Color("#909090")
            Task.Status.IN_PROGRESS -> Color("#EADF00")
            Task.Status.DONE -> Color("#64E74D")
        }

        val iconColor = Color("#FFF")

        val iconClasses = when (props.status) {
            Task.Status.NOT_STARTED -> listOf("fas", "fa-check")
            Task.Status.IN_PROGRESS -> listOf("fas", "fa-hourglass-half")
            Task.Status.DONE -> listOf("fas", "fa-check")
        }.toMutableList()

        styledButton {

            css {
                backgroundColor = iconColorBackground
                borderRadius = 50.pct

                active { backgroundColor = iconColorBackground }
            }

            styledP {

                css {
                    classes = iconClasses
                    backgroundColor = Color.transparent
                    color = iconColor

                    width = props.size
                    height = props.size
                    fontSize = props.size
                    lineHeight = LineHeight(props.size.toString())
                    verticalAlign = VerticalAlign.middle
                    textAlign = TextAlign.center
                    margin(1.rem)
                }
            }
        }

    }
}

fun RBuilder.statusSwitch(props: StatusSwitchProps.() -> Unit) = child(StatusSwitch::class) { attrs(props) }