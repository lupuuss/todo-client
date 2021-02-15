package com.github.lupuuss.todo.client.core.auth

import com.github.lupuuss.todo.api.core.user.Credentials
import com.github.lupuuss.todo.api.core.user.User
import com.github.lupuuss.todo.client.core.api.auth.AuthApi

class AuthFailedException(cause: Throwable) : Exception(cause = cause)

class AuthManager(
    private val authApi: AuthApi,
    private val tokenHolder: TokenHolder
    ) {

    suspend fun login(login: String, password: String) {

        try {
            val token = authApi.login(Credentials(login, password))

            tokenHolder.setToken(token)
        } catch (e: Throwable) {
            throw AuthFailedException(e)
        }
    }

    suspend fun currentUser(): User {
        return authApi.me()
    }

    fun logout() {
        tokenHolder.setToken(null)
    }

    fun isUserLoggedIn(): Boolean {
        return tokenHolder.isAnyTokenAvailable()
    }
}