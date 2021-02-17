package com.github.lupuuss.todo.client.js.react.topbar

import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*
import kotlinx.css.properties.LineHeight

object UserInfoStyles : NamedStylesheet() {

    val userContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        alignItems = Align.center
        child(":not(:last-child)") {
            margin(right = 2.rem)
        }
    }
    val userName by css {
        fontSize = 3.rem
    }

    val userIcon by css {

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
