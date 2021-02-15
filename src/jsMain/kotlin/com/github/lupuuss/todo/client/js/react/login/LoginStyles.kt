package com.github.lupuuss.todo.client.js.react.login

import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*

object LoginStyles : NamedStylesheet() {

    val form by css {

        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = Align.center

        child(":not(:first-child)") {
            margin(top = 2.rem)
        }
    }
}