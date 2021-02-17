package com.github.lupuuss.todo.client.js.react.topbar

import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.auth.AuthManager
import org.kodein.di.instance
import react.RBuilder
import react.RComponent
import react.ReactElement
import react.router.dom.routeLink
import styled.css
import styled.styledDiv

class TopBar : RComponent<dynamic, dynamic>() {

    private val authManager: AuthManager by TodoKodein.di.instance()

    override fun RBuilder.render() {

        styledDiv {

            css { +TopBarStyles.container }

            routeLink("/") {
                logo {
                    title = "TO-DO"
                }
            }

            if (authManager.isUserLoggedIn()) {
                userInfo()
            }
        }
    }
}

fun RBuilder.topBar(): ReactElement = child(TopBar::class) {}