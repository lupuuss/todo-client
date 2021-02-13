package com.github.lupuuss.todo.client.core.auth

interface TokenHolder {
    suspend fun getToken(): String?
    suspend fun setToken(token: String?)
}