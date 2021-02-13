package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.js.react.Colors
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import styled.css
import styled.styledDiv

class TopBar : RComponent<dynamic, dynamic>() {

    override fun RBuilder.render() {

        styledDiv {

            css {
                backgroundColor = Colors.primary
                width = 100.pct
                display = Display.inlineBlock
            }

            child(Logo::class) {
                attrs { value = "TO-DO" }
            }
        }
    }
}