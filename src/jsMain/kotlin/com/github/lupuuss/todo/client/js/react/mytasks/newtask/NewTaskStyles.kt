package com.github.lupuuss.todo.client.js.react.mytasks.newtask

import com.github.lupuuss.todo.client.js.react.Colors
import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*

object NewTaskStyles : NamedStylesheet() {

    val icon by css {
        height = 2.rem
        width = 2.rem
        fontSize = 2.rem
        margin(1.rem)
    }

    fun container(available: Boolean): RuleSet = {

        cursor = if (available) Cursor.default else Cursor.pointer

        width = 100.pct
        backgroundColor = Colors.primary

        active {
            backgroundColor = Colors.primary
        }
    }
}