package com.github.lupuuss.todo.client.js.react.login

import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.auth.AuthManager
import com.github.lupuuss.todo.client.js.react.common.iconInput
import kotlinx.coroutines.*
import kotlinx.css.*
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import org.kodein.di.instance
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.div
import react.dom.i
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

                iconInput {
                    iconName = "fa-user"
                    LoginStyles.iconInput(this)

                    attrs = {
                        it.type = InputType.text
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

                styledButton {

                    css { +LoginStyles.submitButton }

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