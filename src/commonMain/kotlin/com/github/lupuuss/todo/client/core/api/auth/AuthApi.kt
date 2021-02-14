package com.github.lupuuss.todo.client.core.api.auth

import com.github.lupuuss.todo.api.core.user.Credentials
import com.github.lupuuss.todo.api.core.user.User

interface AuthApi {

    suspend fun login(credentials: Credentials): String

    suspend fun me(): User
}