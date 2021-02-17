package com.github.lupuuss.todo.client.js.react.login

import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.auth.AuthManager
import com.github.lupuuss.todo.client.js.react.common.iconInput
import com.github.lupuuss.todo.client.js.react.common.loadingButton
import kotlinx.coroutines.*
import kotlinx.css.*
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import kotlinx.html.spellCheck
import org.kodein.di.instance
import org.w3c.dom.events.Event
import react.*
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

                css { +LoginStyles.form }

                iconInput {
                    iconName = "fa-user"
                    LoginStyles.iconInput(this)

                    attrs = {
                        it.type = InputType.text
                        it.spellCheck = false
                        it.value = state.login
                        it.onChangeFunction = this@Login::onLoginChange
                    }
                }

                iconInput {

                    iconName = "fa-unlock-alt"
                    LoginStyles.iconInput(this)

                    attrs = {
                        it.type = InputType.password
                        it.value = state.password
                        it.onChangeFunction = this@Login::onPasswordChange
                    }
                }

                loadingButton {
                    isLoading = state.isLoading
                    text = "LOGIN"
                    type = ButtonType.submit
                    css = LoginStyles.submitButton
                }
            }
        }
    }
}

fun RBuilder.login(handler: LoginProps.() -> Unit): ReactElement = child(Login::class) {
    this.attrs(handler)
}