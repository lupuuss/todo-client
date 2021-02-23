package com.github.lupuuss.todo.client.js.react.mytasks.newtask

import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*

object NewTaskFormStyles : NamedStylesheet() {

    val input by css {
        padding(2.rem)
        resize = Resize.none
        overflow = Overflow.hidden
    }

    val container by css {

        display = Display.flex
        flexDirection = FlexDirection.column
        margin(2.rem)

        child(":not(:first-child)") {
            marginTop = 2.rem
        }
    }

    val buttons by css {
        display = Display.flex
        flexDirection = FlexDirection.row

        margin(vertical = 2.rem)

        child(":not(:first-child)") {
            marginLeft = 2.rem
        }
    }

}
