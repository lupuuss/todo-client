package com.github.lupuuss.todo.client.core.storage

interface Storage {

    fun init()

    operator fun get(key: String): String?

    operator fun set(key: String, value: String?)

    fun close()
}