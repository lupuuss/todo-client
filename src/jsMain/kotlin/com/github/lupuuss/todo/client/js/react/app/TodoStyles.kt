package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.js.react.Colors
import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*

object TodoStyles : NamedStylesheet() {

    val container by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = Align.center
        width = LinearDimension.auto
        height = 100.pct
        backgroundColor = Colors.secondary
    }
}