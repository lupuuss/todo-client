package com.github.lupuuss.todo.client.core

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun runHere(block: suspend () -> Unit): dynamic = GlobalScope.promise {
    block()
}