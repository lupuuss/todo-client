package com.github.lupuuss.todo.client.js.react.login

import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.auth.AuthManager
import kotlinx.coroutines.*
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import org.kodein.di.instance
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.div
import styled.*

external interface LoginProps : RProps {
    var onSuccess: () -> Unit
}

external interface LoginState : RState {
    var isLoading: Boolean
    var error: String
    var login: String
    var password: String
}

class Login : RComponent<LoginProps, LoginState>(), CoroutineScope by MainScope() {

    private val di = TodoKodein.di

    private val authManager: AuthManager by di.instance()

    init {
        state.login = ""
        state.password = ""
        state.error = ""
        state.isLoading = false
    }

    override fun componentWillUnmount() {
        cancel()
    }

    private fun login(event: Event) {

        event.preventDefault()

        launch {

            setState {
                isLoading = true
            }

            try {

                authManager.login(state.login, state.password)

                props.onSuccess()

            } catch (e: Exception) {

                e.printStackTrace()

                setState {
                    error = "Authentication failed :("
                    login = ""
                    password = ""
                }
            }

            setState {
                isLoading = false
            }
        }
    }

    private fun onLoginChange(event: Event) {

        val value = event.target.asDynamic().value as String
        setState {
            login = value
        }
    }

    private fun onPasswordChange(event: Event) {
        val value = event.target.asDynamic().value as String
        setState {
            password = value
        }
    }

    override fun RBuilder.render() {
        div {

            styledForm {

                attrs {
                    onSubmitFunction = this@Login::login
                }

                css {
                    +LoginStyles.form
                }

                styledInput(InputType.text) {

                    css { +LoginStyles.login }

                    attrs {
                        value = state.login
                        onChangeFunction = this@Login::onLoginChange
                    }
                }

                styledInput(InputType.password) {

                    css { +LoginStyles.password }

                    attrs {
                        value = state.password
                        onChangeFunction = this@Login::onPasswordChange
                    }
                }

                button {

                    attrs {
                        type = ButtonType.submit
                    }

                    +"LOGIN"
                }
            }
        }
    }
}

fun RBuilder.login(handler: LoginProps.() -> Unit): ReactElement = child(Login::class) {
    this.attrs(handler)
}