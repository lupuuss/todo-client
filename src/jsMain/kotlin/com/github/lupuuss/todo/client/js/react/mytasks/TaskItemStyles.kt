package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.client.js.react.Colors
import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*

object TaskItemStyles : NamedStylesheet() {

    val nameLine by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.spaceBetween

        child(":not(:first-child)") {
            marginTop = 1.rem
        }
    }

    val container by css {
        fontSize = 2.rem
        backgroundColor = Colors.primary
        borderRadius = 2.rem
        width = 100.pct
        padding(2.rem)
    }

    val font by css {
        fontSize = 2.rem
    }
}