package com.github.lupuuss.todo.client.js.react

import kotlinx.css.*
import kotlinx.css.properties.*
import react.RBuilder
import react.RComponent
import react.ReactElement
import styled.animation
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
            height = 100.pct
        }

        rule("html, body, div") {
            color = Colors.fontColor
            fontSize = 10.px
            fontFamily = "'Roboto', sans-serif"
        }

        body {
            height = 100.pct
        }

        a {
            textDecoration = TextDecoration.none
        }

        rule("button, input") {
            boxShadow(color = rgba(0,0,0,0.12), offsetX = 0.px, offsetY = 3.px, blurRadius = 5.px)
            boxShadow(color =  rgba(0,0,0,0.24), offsetX = 0.px, offsetY = 3.px, blurRadius = 4.px)
        }

        rule("button") {
            hover {
                boxShadow(color = rgba(0,0,0,0.25), offsetX = 0.px, offsetY = 14.px, blurRadius = 28.px)
                boxShadow(color =  rgba(0,0,0,0.22), offsetX = 0.px, offsetY = 10.px, blurRadius = 10.px)
            }
        }

        rule("button, input") {
            outline = Outline.none
            borderRadius = 3.rem
            borderColor = Color.transparent
            fontFamily = "'Roboto', sans-serif"
            fontSize = 2.rem
            padding(2.rem)
        }

        rule("p, input, button") {
            animation(duration = Time("200ms"), timing = Timing.easeInOut) {
                from {
                    opacity = 0
                }

                to {
                    opacity = 1
                }
            }
        }

        input {

            color = Colors.primary
            borderWidth = 1.px
            transition("all",Time("200ms"), Timing.easeInOut)

            rule(":focus, :hover") {
                boxShadow(offsetX = 0.px, offsetY = 0.5.px, blurRadius = 5.px, color = Colors.contrast)
                border(1.px, BorderStyle.solid, Colors.contrast)
            }
        }

        button {

            color = Colors.fontColor
            cursor = Cursor.pointer
            backgroundColor = Colors.contrast

            transition("all", Time("200ms"), Timing("cubic-bezier(.25,.8,.25,1)"))

            active {
                backgroundColor = Colors.contrastSecondary
            }
        }

    }

    override fun RBuilder.render() {
        injectGlobal { style() }
    }
}

fun RBuilder.globalStyle(): ReactElement = child(GlobalStyle::class) {}