package com.github.lupuuss.todo.client.core.auth

import kotlinx.browser.localStorage
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class JsTokenHolder : TokenHolder {

    private var token: String? = null
    private val mutex = Mutex()

    private val tokenKey = "DefinitelyNotJwtToken"

    init {
        token = localStorage.getItem(tokenKey)
    }

    override suspend fun getToken(): String? = mutex.withLock { token }

    override suspend fun setToken(token: String?) = mutex.withLock {
        this.token = token

        if (token == null) {
            localStorage.removeItem(tokenKey)
            return
        }

        localStorage.setItem(tokenKey, token)
    }

    override suspend fun isAnyTokenAvailable(): Boolean {
        return getToken() != null
    }
}