package com.github.lupuuss.todo.client.core

import kotlinx.coroutines.runBlocking

actual fun runHere(block: suspend () -> Unit) = runBlocking {
    block()
}