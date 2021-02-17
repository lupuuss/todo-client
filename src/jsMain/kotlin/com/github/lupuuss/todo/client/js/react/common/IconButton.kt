package com.github.lupuuss.todo.client.js.react.common

import kotlinx.css.LinearDimension
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import react.ReactElement
import styled.css
import styled.styledButton
import styled.styledDiv

external interface IconButtonProps : RProps {
    var onClick: ((Event) -> Unit)?
    var iconSize: LinearDimension
    var iconStyle: String
    var iconName: String
}

class IconButton : RComponent<IconButtonProps, dynamic>() {
    override fun RBuilder.render() {
        styledButton {

            css { +IconButtonStyles.button }

            styledDiv {
                css {
                    +IconButtonStyles.icon(props)
                    classes = mutableListOf(props.iconName, props.iconStyle)
                }
            }

            attrs {
                props.onClick?.let {
                    onClickFunction = it
                }
            }
        }
    }
}

fun RBuilder.iconButton(handler: IconButtonProps.() -> Unit): ReactElement = child(IconButton::class) { attrs(handler) }