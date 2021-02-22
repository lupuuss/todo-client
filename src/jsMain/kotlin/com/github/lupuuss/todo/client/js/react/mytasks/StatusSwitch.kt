package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.api.core.task.Task
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import styled.css
import styled.styledButton
import styled.styledP

external interface StatusSwitchProps : RProps {
    var status: Task.Status
    var size: LinearDimension
    var onClick: (Event) -> Unit
}

class StatusSwitch : RComponent<StatusSwitchProps, dynamic>() {
    override fun RBuilder.render() {

        styledButton {

            css { +StatusSwitchStyles.button(props) }

            styledP {

                css { +StatusSwitchStyles.icon(props) }
            }

            attrs {
                onClickFunction = props.onClick
            }
        }

    }
}

fun RBuilder.statusSwitch(props: StatusSwitchProps.() -> Unit) = child(StatusSwitch::class) { attrs(props) }