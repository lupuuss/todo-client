package com.github.lupuuss.todo.client.js.react.login

import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import com.github.lupuuss.todo.client.js.react.common.IconInputProps
import kotlinx.css.*

object LoginStyles : NamedStylesheet() {

    val form by css {

        padding(3.rem)
        width = 500.px
        margin(LinearDimension.auto)

        child(":not(:first-child)") {
            margin(top = 2.rem)
        }
    }

    val iconInput: (IconInputProps).() -> Unit = {
        iconStyle = "fa"
        iconSize = 3.rem
        iconSpacing = 1.rem
        iconColor = Color.white
    }

    val submitButton by css {
        display = Display.block
        width = 100.pct
    }
}