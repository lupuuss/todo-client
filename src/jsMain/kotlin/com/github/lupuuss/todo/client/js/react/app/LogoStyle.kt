package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.js.react.Colors
import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*
import kotlinx.css.properties.Time
import kotlinx.css.properties.Timing
import kotlinx.css.properties.transition

object LogoStyle : NamedStylesheet() {
    val logo by css {
        fontSize = 6.rem
        color = Colors.contrast
        margin(2.rem)

        transition("all", Time("200ms"), Timing.easeInOut)

        hover {
            color = Colors.contrastSecondary
            cursor = Cursor.pointer
            letterSpacing = 1.rem
        }
    }
}