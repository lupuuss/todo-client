package com.github.lupuuss.todo.client.js.react

import kotlinx.css.*
import kotlinx.css.properties.*
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
            fontFace {
                color = Colors.fontColor
            }
            fontSize = 10.px
            height = 100.pct
            fontFamily = "'Roboto', sans-serif"
        }

        body {
            height = 100.pct
        }

        input {
            outline = Outline.none
            borderRadius = 1.rem
            borderColor = Color.transparent
            borderWidth = 1.px

            fontSize = 2.rem
            color = Colors.primary
            fontFamily = "'Roboto', sans-serif"
            padding(2.rem)

            transition("all",Time("200ms"), Timing.easeInOut)

            focus {
                boxShadow(offsetX = 0.px, offsetY = 0.5.px, blurRadius = 5.px, color = Colors.contrastSecondary)
                border(1.px, BorderStyle.solid, Colors.contrastSecondary)
            }

            hover {
                boxShadow(offsetX = 0.px, offsetY = 0.5.px, blurRadius = 5.px, color = Colors.contrast)
                border(1.px, BorderStyle.solid, Colors.contrast)
            }
        }

        button {
            outline = Outline.none
            borderRadius = 1.rem
            borderColor = Color.transparent
            color = Colors.fontColor
            fontFamily = "'Roboto', sans-serif"
            fontSize = 2.rem
            cursor = Cursor.pointer
            padding(2.rem)

            backgroundColor = Colors.contrast

            transition("all", Time("200ms"), Timing.easeInOut)

            hover {
                backgroundColor = Colors.contrastSecondary
                transform {
                    scale(1.02)
                }
            }
        }

    }

    override fun RBuilder.render() {
        injectGlobal { style() }
    }
}

fun RBuilder.globalStyle(): ReactElement = child(GlobalStyle::class) {}