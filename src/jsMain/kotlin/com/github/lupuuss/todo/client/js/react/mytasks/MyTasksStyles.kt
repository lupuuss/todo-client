package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*

object MyTasksStyles : NamedStylesheet() {
    val container by css {

        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = Align.center
        height = LinearDimension.fitContent
        width = 700.px

        marginTop = 2.rem
        marginBottom = 2.rem

        child(":not(:first-child)") {
            marginTop = 2.rem
        }
    }
}