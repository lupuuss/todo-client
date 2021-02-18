package com.github.lupuuss.todo.client.core.api.auth

import com.github.lupuuss.todo.api.core.user.Credentials

interface AuthApi {

    suspend fun login(credentials: Credentials): String
}