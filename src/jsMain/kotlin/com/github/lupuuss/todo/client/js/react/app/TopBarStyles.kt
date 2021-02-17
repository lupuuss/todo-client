package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.js.react.Colors
import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*

object TopBarStyles : NamedStylesheet() {

    val container by css {
        backgroundColor = Colors.primary
        borderWidth = 0.0.px
        borderBottomWidth = 0.5.rem
        borderColor = Colors.contrastSecondary
        borderStyle = BorderStyle.solid
        display = Display.flex
        flexDirection = FlexDirection.row
    }
}