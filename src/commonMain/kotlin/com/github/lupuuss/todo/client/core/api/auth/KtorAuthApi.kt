package com.github.lupuuss.todo.client.core.api.auth

import com.github.lupuuss.todo.api.core.user.Credentials
import com.github.lupuuss.todo.api.core.user.User
import com.github.lupuuss.todo.client.core.api.KtorClientBase
import io.ktor.client.*

class KtorAuthApi(baseUrl: String, authClient: HttpClient) : KtorClientBase(baseUrl, authClient), AuthApi {
    override suspend fun login(credentials: Credentials): String {

        return postJson("/auth/login", credentials)
    }
}