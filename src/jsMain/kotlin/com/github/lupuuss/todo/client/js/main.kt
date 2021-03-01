package com.github.lupuuss.todo.client.js

import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.storage.BrowserStorage
import com.github.lupuuss.todo.client.core.storage.Storage
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
        pre {
            bind<Storage>() with singleton { BrowserStorage() }
            bind<CoroutineContext>(tag = "Networking") with provider { Dispatchers.Default }
        }
    }

    window.onload = {
        render(document.getElementById("root")) {
            child(TodoApp::class) {}
        }
    }
}