package com.github.lupuuss.todo.client.core.auth

import kotlinx.browser.localStorage

class JsTokenHolder : TokenHolder {

    private var token: String? = null
    private val tokenKey = "DefinitelyNotJwtToken"

    init {
        token = localStorage.getItem(tokenKey)
    }

    override fun getToken(): String? {
        return token
    }

    override fun setToken(token: String?) {
        this.token = token

        if (token == null) {
            localStorage.removeItem(tokenKey)
            return
        }

        localStorage.setItem(tokenKey, token)
    }

    override fun isAnyTokenAvailable(): Boolean {
        return getToken() != null
    }
}