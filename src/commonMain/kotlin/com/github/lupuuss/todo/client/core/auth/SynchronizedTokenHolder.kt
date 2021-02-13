package com.github.lupuuss.todo.client.core.auth

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SynchronizedTokenHolder(tokenInit: String? = null) : TokenHolder {

    private val mutex = Mutex()
    private var tokenValue: String? = tokenInit

    override suspend fun getToken(): String? = mutex.withLock {
        tokenValue
    }

    override suspend fun setToken(token: String?) = mutex.withLock {
        tokenValue = token
    }
}