package com.github.lupuuss.todo.client.js.react.topbar

import com.github.lupuuss.todo.client.js.react.Colors
import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*
import kotlinx.css.properties.LineHeight

object UserInfoStyles : NamedStylesheet() {

    val userName by css {
        color = Colors.fontColor
        fontSize = 3.rem
    }

    val userIcon by css {
        color = Colors.fontColor

        val size = 3.rem

        fontSize = size
        width =  size
        height =  size
        lineHeight = LineHeight(size.toString())
        textAlign = TextAlign.center
        verticalAlign = VerticalAlign.middle
    }

    val container by css {

        margin(2.rem)
        display = Display.flex
        flexDirection = FlexDirection.row
        alignItems = Align.center

        child("*") {
            margin(right = 2.rem)
        }
    }

}
