package com.github.lupuuss.todo.client.js.react.common

import kotlinx.css.CSSBuilder
import kotlinx.html.ButtonType
import react.RBuilder
import react.RComponent
import react.RProps
import react.ReactElement
import styled.css
import styled.styledButton
import styled.styledDiv

external interface LoadingButtonProps : RProps{
    var text: String
    var isLoading: Boolean
    var type: ButtonType
    var css: ((CSSBuilder) -> Unit)?
}

class LoadingButton : RComponent<LoadingButtonProps, dynamic>() {

    override fun RBuilder.render() {

        styledButton {

            css { props.css?.invoke(this) }

            if (!props.isLoading) {

                +props.text

            } else {

                styledDiv {
                    css {
                        classes = mutableListOf("fas", "fa-spinner", "fa-pulse")
                    }
                }

            }

            attrs {
                type = props.type
            }
        }
    }

}

fun RBuilder.loadingButton(handler: LoadingButtonProps.() -> Unit): ReactElement =
    child(LoadingButton::class) { attrs(handler) }