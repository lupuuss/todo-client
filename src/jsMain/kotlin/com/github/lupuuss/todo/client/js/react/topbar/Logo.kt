package com.github.lupuuss.todo.client.js.react.topbar

import react.RBuilder
import react.RComponent
import react.RProps
import styled.css
import styled.styledDiv
import styled.styledP

external interface LogoProps : RProps {
    var title: String
}

class Logo : RComponent<LogoProps, dynamic>() {

    override fun RBuilder.render() {
        styledP {
            css { +LogoStyle.logo }
            +props.title
        }
    }
}

fun RBuilder.logo(handler: LogoProps.() -> Unit) = child(Logo::class) { attrs(handler) }