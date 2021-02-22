package com.github.lupuuss.todo.client.js.react.common

import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*

object LoadingButtonStyles : NamedStylesheet() {

    val loadingIcon by css {
        fontSize = 1.em
    }

    val loadingStyle by css {
        cursor = Cursor.notAllowed
        pointerEvents = PointerEvents.none
        padding(2.rem)
    }

    val readyStyle by css {
        cursor = Cursor.pointer
        pointerEvents = PointerEvents.initial
        padding(2.rem)
    }
}