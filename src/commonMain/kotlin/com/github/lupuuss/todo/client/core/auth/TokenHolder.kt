package com.github.lupuuss.todo.client.core.auth

interface TokenHolder {
    fun getToken(): String?
    fun setToken(token: String?)
    fun isAnyTokenAvailable(): Boolean
}