package com.github.lupuuss.todo.client.js.react.common

import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.INPUT
import kotlinx.html.classes
import react.RBuilder
import react.RComponent
import react.RProps
import react.ReactElement
import react.dom.div
import react.dom.i
import styled.css
import styled.styledInput

external interface IconInputProps : RProps {
    var iconSettings: Set<String>
    var attrs: (INPUT) -> Unit
}

class IconInput : RComponent<IconInputProps, dynamic>() {

    override fun RBuilder.render() {
        div {

            i {
                attrs { classes = props.iconSettings }
            }

            styledInput {
                css {
                    display = Display.inline
                }
                attrs(props.attrs)
            }
        }
    }

}

fun RBuilder.iconInput(handler: IconInputProps.() -> Unit): ReactElement = child(IconInput::class) { this.attrs(handler) }