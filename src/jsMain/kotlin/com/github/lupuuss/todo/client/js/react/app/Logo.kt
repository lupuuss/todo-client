package com.github.lupuuss.todo.client.js.react.app

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
            css { +LogoStyle.logo }
            +props.title
        }
    }
}

fun RBuilder.logo(handler: LogoProps.() -> Unit) = child(Logo::class) { attrs(handler) }