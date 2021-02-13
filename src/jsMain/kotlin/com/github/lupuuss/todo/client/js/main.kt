package com.github.lupuuss.todo.client.js

import com.github.lupuuss.todo.client.js.react.app.TodoApp
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window


fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            child(TodoApp::class) {}
        }
    }
}