package com.github.lupuuss.todo.client.core.auth

import com.github.lupuuss.todo.api.core.user.Credentials
import com.github.lupuuss.todo.api.core.user.User
import com.github.lupuuss.todo.client.core.api.auth.AuthApi
import com.github.lupuuss.todo.client.core.storage.Storage
import io.ktor.client.features.*
import io.ktor.http.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AuthManager(
    private val authApi: AuthApi,
    private val storage: Storage,
    context: CoroutineContext
    ): CoroutineScope by CoroutineScope(context) {

    fun interface OnAuthStatusChangedListener {
        fun onAuthChanged(user: User?)
    }

    private val tokenKey = "DefinitelyNotJwtToken"

    private val listeners = mutableListOf<OnAuthStatusChangedListener>()

    private var currentUser: User? = null

    var token: String? = storage[tokenKey]
    private set

    private fun setToken(value: String?) {
        token = value
        storage[tokenKey] = value
    }

    suspend fun login(login: String, password: String) = withContext(coroutineContext) {

        try {
            val token = authApi.login(Credentials(login, password))

            setToken(token)

            currentUser = fetchCurrentUser()

            listeners.forEach { it.onAuthChanged(currentUser) }

        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            setToken(null)
            throw AuthFailedException(e)
        }
    }

    suspend fun currentUser(): User {
        return withContext(coroutineContext) {
            currentUser ?: fetchCurrentUser().also {
                currentUser = it
            }
        }
    }

    suspend fun refreshToken() = withContext(coroutineContext) {

        try {

            val newToken = authApi.refreshToken(token ?: throw AuthRequiredException())

            setToken(newToken)

        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            throw RefreshFailedException(e)
        }

    }

    private suspend fun fetchCurrentUser(retry: Boolean = false): User {

        try {

            return authApi.me(token ?: throw UnauthorizedException("User is not logged in!"))

        } catch (e: CancellationException) {
            throw e
        } catch (e: ClientRequestException) {

            if (!retry && e.response.status == HttpStatusCode.Unauthorized) {

                refreshToken()

                return fetchCurrentUser(retry = true)
            }

            throw CurrentUserObjectException(e)

        } catch (e: Throwable) {

            throw CurrentUserObjectException(e)

        }
    }

    fun logout() {
        setToken(null)
        currentUser = null
        listeners.forEach { it.onAuthChanged(currentUser) }
    }

    fun isUserLoggedIn(): Boolean {
        return token != null
    }

    fun addOnAuthChangedListener(listener: OnAuthStatusChangedListener) {
        listeners.add(listener)
    }

    fun removeOnAuthChangedListener(listener: OnAuthStatusChangedListener) {
        listeners.remove(listener)
    }
}

class AuthRequiredException : Exception("User must login with credentials!")

class RefreshFailedException(cause: Throwable) : Exception(cause)

class AuthFailedException(cause: Throwable) : Exception(cause = cause)

class CurrentUserObjectException(cause: Throwable) : Exception(cause = cause)

class UnauthorizedException(msg: String): Exception(msg)