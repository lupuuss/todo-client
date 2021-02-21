package com.github.lupuuss.todo.client.core.storage

import kotlinx.browser.localStorage

class BrowserStorage : Storage {

    override fun init() = Unit

    override fun get(key: String): String? = localStorage.getItem(key)

    override fun set(key: String, value: String?) {

        if (value == null) {
            localStorage.removeItem(key)
            return
        }

        localStorage.setItem(key, value)
    }

    override fun close() = Unit
}