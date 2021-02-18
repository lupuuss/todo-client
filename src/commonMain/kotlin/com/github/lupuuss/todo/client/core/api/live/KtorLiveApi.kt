package com.github.lupuuss.todo.client.core.api.live

import com.github.lupuuss.todo.client.core.auth.AuthManager

class KtorWebSocketLiveApi(private val authManager: AuthManager) : LiveApi {

    override fun addOnChangeListener(listener: LiveApi.Listener) {
        TODO("Not yet implemented")
    }

    override fun removeOnChangeListener(listener: LiveApi.Listener) {
        TODO("Not yet implemented")
    }
}