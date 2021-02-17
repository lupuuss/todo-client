package com.github.lupuuss.todo.client.js.react.topbar

import com.github.lupuuss.todo.api.core.user.User
import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.auth.AuthManager
import com.github.lupuuss.todo.client.js.react.common.iconButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.rem
import org.kodein.di.instance
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RState
import react.setState
import styled.css
import styled.styledDiv

external interface UserInfoState : RState {
    var currentUser: User?
}

class UserInfo : RComponent<dynamic, UserInfoState>(), AuthManager.OnAuthStatusChangedListener, CoroutineScope by MainScope() {

    private val di = TodoKodein.di

    private val authManager: AuthManager by di.instance()

    override fun componentDidMount() {
        authManager.addOnAuthChangedListener(this)

        launch {
            try {

                val user = authManager.currentUser()

                setState {
                    currentUser = user
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun componentWillUnmount() {
        authManager.removeOnAuthChangedListener(this)
    }

    override fun onAuthChanged(user: User?) {

        setState {
            currentUser = user
        }
        forceUpdate()
    }

    private fun logout(@Suppress("UNUSED_PARAMETER") event: Event) {
        authManager.logout()
    }

    override fun RBuilder.render() {

        if (!authManager.isUserLoggedIn()) return

        styledDiv {

            css { +UserInfoStyles.container }

            state.currentUser?.let { user ->
                styledDiv {
                    css { +UserInfoStyles.userName }
                    +user.login
                }

                styledDiv {

                    css {
                        classes = when (user.role) {
                            User.Role.USER -> mutableListOf("fa", "fa-user")
                            User.Role.ADMIN -> mutableListOf("fa", "fa-user-shield")
                        }
                        +UserInfoStyles.userIcon
                    }
                }
            }

            iconButton {
                iconName = "fa-sign-out-alt"
                iconStyle = "fas"
                iconSize = 2.rem
                onClick = this@UserInfo::logout
            }
        }
    }
}

fun RBuilder.userInfo() = child(UserInfo::class) {}