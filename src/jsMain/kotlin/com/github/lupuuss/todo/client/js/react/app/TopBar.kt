package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.js.react.Colors
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.ReactElement
import styled.css
import styled.styledDiv

class TopBar : RComponent<dynamic, dynamic>() {

    override fun RBuilder.render() {

        styledDiv {

            css {
                backgroundColor = Colors.primary
                display = Display.flex
                flexDirection = FlexDirection.row
            }

            logo {
                title = "TO-DO"
            }
        }
    }
}

fun RBuilder.topBar(): ReactElement = child(TopBar::class) {}