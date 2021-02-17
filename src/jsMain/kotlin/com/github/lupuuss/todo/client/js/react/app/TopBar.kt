package com.github.lupuuss.todo.client.js.react.app

import react.RBuilder
import react.RComponent
import react.ReactElement
import react.router.dom.routeLink
import styled.css
import styled.styledDiv

class TopBar : RComponent<dynamic, dynamic>() {

    override fun RBuilder.render() {

        styledDiv {

            css { +TopBarStyles.container }

            routeLink("/") {
                logo {
                    title = "TO-DO"
                }
            }
        }
    }
}

fun RBuilder.topBar(): ReactElement = child(TopBar::class) {}