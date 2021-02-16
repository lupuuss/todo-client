package com.github.lupuuss.todo.client.js.react

import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.ReactElement
import styled.injectGlobal

class GlobalStyle : RComponent<dynamic, dynamic>() {

    private fun CSSBuilder.style() {

        rule("*") {
            margin(0.px)
            padding(0.px)
            boxSizing = BoxSizing.borderBox
        }

        rule("#root") {
            display = Display.flex
            flexDirection = FlexDirection.column
            height = 100.pct
        }

        html {
            fontSize = 10.px
            height = 100.pct
        }

        body {
            height = 100.pct
        }

        input {
            borderRadius = 5.px
            borderColor = Color.transparent
            fontSize = 2.rem
            padding(2.rem)
        }

        button {
            borderRadius = 5.px
            borderColor = Color.transparent
            fontSize = 2.rem
            padding(2.rem)
        }

    }

    override fun RBuilder.render() {
        injectGlobal { style() }
    }
}

fun RBuilder.globalStyle(): ReactElement = child(GlobalStyle::class) {}