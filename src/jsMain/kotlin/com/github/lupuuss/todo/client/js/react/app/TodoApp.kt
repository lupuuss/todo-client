package com.github.lupuuss.todo.client.js.react.app

import com.github.lupuuss.todo.client.js.react.Colors
import com.github.lupuuss.todo.client.js.react.globalStyle
import com.github.lupuuss.todo.client.js.react.login.login
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import styled.css
import styled.styledDiv

class TodoApp : RComponent<dynamic, dynamic>() {

    private fun routeToHome() {
        println("LOGGED IN :D")
    }

    override fun RBuilder.render() {

        globalStyle()

        topBar()

        styledDiv {

            css {
                width = LinearDimension.auto
                height = 100.pct
                backgroundColor = Colors.secondary
            }

            login {
                onSuccess = this@TodoApp::routeToHome
            }
        }
    }
}