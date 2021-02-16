package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.js.react.Colors
import kotlinx.css.*
import kotlinx.css.properties.*
import react.RBuilder
import react.RComponent
import react.RProps
import styled.css
import styled.styledDiv

external interface LogoProps : RProps {
    var title: String
}

class Logo : RComponent<LogoProps, dynamic>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                fontSize = 6.rem
                color = Colors.contrast
                margin(2.rem)

                transition("all", Time("200ms"), Timing.easeInOut)

                hover {
                    color = Colors.contrastSecondary
                    cursor = Cursor.pointer
                    letterSpacing = 1.rem
                }
            }
            +props.title
        }
    }
}

fun RBuilder.logo(handler: LogoProps.() -> Unit) = child(Logo::class) { attrs(handler) }