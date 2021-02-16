package com.github.lupuuss.todo.client.js.react.common

import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.Cursor
import kotlinx.css.PointerEvents
import kotlinx.css.cursor
import kotlinx.css.pointerEvents

object LoadingButtonStyles : NamedStylesheet() {
    val loadingStyle by css {
        cursor = Cursor.notAllowed
        pointerEvents = PointerEvents.none
    }

    val readyStyle by css {
        cursor = Cursor.pointer
        pointerEvents = PointerEvents.initial
    }
}