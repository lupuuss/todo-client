package com.github.lupuuss.todo.client.core.api.auth

import com.github.lupuuss.todo.api.core.user.Credentials
import com.github.lupuuss.todo.api.core.user.User
import com.github.lupuuss.todo.client.core.api.KtorClientBase
import io.ktor.client.*
import io.ktor.client.request.*

class KtorAuthApi(baseUrl: String, authClient: HttpClient) : KtorClientBase(baseUrl, authClient), AuthApi {
    override suspend fun login(credentials: Credentials): String {

        return postJson("/auth/login", credentials)
    }

    override suspend fun refreshToken(token: String): String {
        return post("/auth/token") {
            header("Authorization", "Bearer $token")
        }
    }

    override suspend fun me(token: String): User {
        return get("/me") {
            header("Authorization", "Bearer $token")
        }
    }
}