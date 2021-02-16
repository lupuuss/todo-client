package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.js.react.Colors
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.ReactElement
import react.router.dom.routeLink
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

            routeLink("/") {
                logo {
                    title = "TO-DO"
                }
            }
        }
    }
}

fun RBuilder.topBar(): ReactElement = child(TopBar::class) {}