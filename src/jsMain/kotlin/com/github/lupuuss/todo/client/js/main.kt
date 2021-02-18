package com.github.lupuuss.todo.client.js

import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.auth.JsTokenHolder
import com.github.lupuuss.todo.client.core.auth.TokenHolder
import com.github.lupuuss.todo.client.js.react.app.TodoApp
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.Dispatchers
import org.kodein.di.bind
import org.kodein.di.provider
import org.kodein.di.singleton
import kotlin.coroutines.CoroutineContext


fun main() {

    TodoKodein.init {
        bind<TokenHolder>() with singleton { JsTokenHolder() }
        bind<CoroutineContext>(tag = "Networking") with provider { Dispatchers.Default }
    }

    window.onload = {
        render(document.getElementById("root")) {
            child(TodoApp::class) {}
        }
    }
}