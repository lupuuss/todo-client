package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.api.core.user.User
import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.auth.AuthManager
import com.github.lupuuss.todo.client.js.react.globalStyle
import com.github.lupuuss.todo.client.js.react.home.home
import com.github.lupuuss.todo.client.js.react.login.login
import com.github.lupuuss.todo.client.js.react.topbar.topBar
import org.kodein.di.instance
import react.*
import react.router.dom.*
import styled.css
import styled.styledDiv

external interface TodoAppState : RState {
    var isUserLoggedIn: Boolean
}

class TodoApp(props: dynamic) : RComponent<dynamic, TodoAppState>(props), AuthManager.OnAuthStatusChangedListener {

    private val di = TodoKodein.di

    private val authManager: AuthManager by di.instance()

    init {
        state.isUserLoggedIn = authManager.isUserLoggedIn()
    }

    override fun componentDidMount() {
        authManager.addOnAuthChangedListener(this)
    }

    override fun componentWillUnmount() {
        authManager.removeOnAuthChangedListener(this)
    }

    override fun onAuthChanged(user: User?) {
        val status = state.isUserLoggedIn
        val realStatus = user != null

        if (status != realStatus) {
            setState {
                isUserLoggedIn = realStatus
            }
        }
    }

    override fun RBuilder.render() {
        globalStyle()

        browserRouter {

            topBar()

            styledDiv {

                css { +TodoStyles.container }

                switch {

                    route("/", exact = true) {
                        requireAuthenticated { home() }
                    }

                    route("/login") {
                        requireUnauthenticated { login() }
                    }
                }
            }
        }
    }

    private fun RBuilder.requireAuthenticated(component: RBuilder.() -> ReactElement): ReactElement {

        if (state.isUserLoggedIn) {
            return component()
        }

        return redirect(to = "/login")
    }

    private fun RBuilder.requireUnauthenticated(component: RBuilder.() -> ReactElement): ReactElement {

        if (!state.isUserLoggedIn) {
            return component()
        }

        return redirect(to = "/")
    }

}