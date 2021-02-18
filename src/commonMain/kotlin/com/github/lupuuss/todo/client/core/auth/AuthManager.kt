package com.github.lupuuss.todo.client.core.auth

import com.github.lupuuss.todo.api.core.user.Credentials
import com.github.lupuuss.todo.api.core.user.User
import com.github.lupuuss.todo.client.core.api.auth.AuthApi
import com.github.lupuuss.todo.client.core.api.me.CurrentUserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AuthFailedException(cause: Throwable) : Exception(cause = cause)

class CurrentUserObjectException(cause: Throwable) : Exception(cause = cause)

class AuthManager(
    private val authApi: AuthApi,
    private val tokenHolder: TokenHolder,
    private val userApi: CurrentUserApi,
    context: CoroutineContext
    ): CoroutineScope by CoroutineScope(context) {

    fun interface OnAuthStatusChangedListener {
        fun onAuthChanged(user: User?)
    }

    private val listeners = mutableListOf<OnAuthStatusChangedListener>()

    private var currentUser: User? = null

    suspend fun login(login: String, password: String) = withContext(coroutineContext) {

        try {
            val token = authApi.login(Credentials(login, password))

            tokenHolder.setToken(token)

            currentUser = userApi.me()

            listeners.forEach { it.onAuthChanged(currentUser) }

        } catch (e: Throwable) {

            tokenHolder.setToken(null)
            throw AuthFailedException(e)
        }
    }

    suspend fun currentUser(): User {
        return withContext(coroutineContext) {
            currentUser ?: try {

                userApi.me().also { currentUser = it }

            } catch (e: Throwable) {
                throw CurrentUserObjectException(e)
            }
        }
    }

    fun logout() {
        currentUser = null
        tokenHolder.setToken(null)
        listeners.forEach { it.onAuthChanged(currentUser) }
    }

    fun isUserLoggedIn(): Boolean {
        return tokenHolder.isAnyTokenAvailable()
    }

    fun addOnAuthChangedListener(listener: OnAuthStatusChangedListener) {
        listeners.add(listener)
    }

    fun removeOnAuthChangedListener(listener: OnAuthStatusChangedListener) {
        listeners.remove(listener)
    }
}
