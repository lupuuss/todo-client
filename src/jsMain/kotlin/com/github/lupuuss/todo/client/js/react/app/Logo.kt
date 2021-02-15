package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.js.react.Colors
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import styled.css
import styled.styledDiv

external interface LogoProps : RProps {
    var title: String
}

class Logo(props: LogoProps) : RComponent<LogoProps, dynamic>(props) {

    override fun RBuilder.render() {
        styledDiv {
            css {
                fontSize = 6.rem
                color = Colors.contrast
                padding(2.rem)
            }
            +props.title
        }
    }
}

fun RBuilder.logo(handler: LogoProps.() -> Unit) = child(Logo::class) { attrs(handler) }