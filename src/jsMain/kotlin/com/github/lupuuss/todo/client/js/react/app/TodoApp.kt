package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.auth.AuthManager
import com.github.lupuuss.todo.client.js.react.Colors
import com.github.lupuuss.todo.client.js.react.globalStyle
import com.github.lupuuss.todo.client.js.react.home.home
import com.github.lupuuss.todo.client.js.react.login.login
import kotlinx.css.*
import org.kodein.di.instance
import react.RBuilder
import react.RComponent
import react.router.dom.*
import styled.css
import styled.styledDiv

class TodoApp(props: dynamic) : RComponent<dynamic, dynamic>(props) {

    private val di = TodoKodein.di

    private val authManager: AuthManager by di.instance()

    private fun onLoggedIn(history: RouteResultHistory) {
        history.push("/")
    }

    override fun RBuilder.render() {
        globalStyle()

        browserRouter {

            topBar()

            styledDiv {

                css {
                    width = LinearDimension.auto
                    height = 100.pct
                    backgroundColor = Colors.secondary
                }

                switch {

                    route("/", exact = true) {
                        if (authManager.isUserLoggedIn()) home() else redirect(to = "/login")
                    }

                    route("/login") { props: RouteResultProps<dynamic> ->

                        login {
                            onSuccess = {
                                onLoggedIn(props.history)
                            }
                        }
                    }
                }
            }
        }
    }
}