package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.client.js.react.Colors
import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*

object TaskItemStyles : NamedStylesheet() {

    val actions by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        child(":not(:first-child)") {
            marginTop = 1.rem
        }
    }

    val content by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        width = 100.pct
    }
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
        display = Display.flex
        flexDirection = FlexDirection.row
        alignItems = Align.center
        padding(2.rem)

        child(":not(:first-child)") {
            marginLeft = 2.rem
        }
    }

    val font by css {
        fontSize = 2.rem
    }
}