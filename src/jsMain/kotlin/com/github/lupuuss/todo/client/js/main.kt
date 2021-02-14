package com.github.lupuuss.todo.client.js

import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.auth.JsTokenHolder
import com.github.lupuuss.todo.client.core.auth.TokenHolder
import com.github.lupuuss.todo.client.js.react.app.TodoApp
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import org.kodein.di.bind
import org.kodein.di.singleton


fun main() {

    TodoKodein.init {
        bind<TokenHolder>() with singleton { JsTokenHolder() }
    }

    window.onload = {
        render(document.getElementById("root")) {
            child(TodoApp::class) {}
        }
    }
}