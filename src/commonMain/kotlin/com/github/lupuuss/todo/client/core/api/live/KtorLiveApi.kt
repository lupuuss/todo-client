package com.github.lupuuss.todo.client.core.api.live

import com.github.lupuuss.todo.api.core.live.Commands
import com.github.lupuuss.todo.api.core.live.ItemChange
import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.api.core.user.User
import com.github.lupuuss.todo.client.core.auth.AuthManager
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.json.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.receiveOrNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

@Serializable
internal class ItemChangeTask(val change: ItemChange<Task>)

@Serializable
internal class ItemChangeUser(val change: ItemChange<User>)

@ExperimentalCoroutinesApi
class KtorWebSocketLiveApi(
    private val authManager: AuthManager,
    private val baseUrl: String,
    private val client: HttpClient,
    context: CoroutineContext
) : LiveApi, CoroutineScope by CoroutineScope(context) {

    private val listeners = mutableListOf<LiveApi.Listener>()

    private var socketJob: Job? = null

    private var startLock = Mutex()

    private var firstAuth = true

    override fun addOnChangeListener(listener: LiveApi.Listener) {
        listeners.add(listener)


        launch {

            val shouldStart = startLock.tryLock()

            if (!shouldStart) return@launch

            socketJob = this@KtorWebSocketLiveApi.async {
                handleSocket()
                startLock.unlock()
                startLock = Mutex()
            }
        }
    }

    private suspend fun handleSocket() {

        client.ws("$baseUrl/live") {

            while(true) {

                val command = incoming.receiveOrNull() as? Frame.Text ?: break

                when (command.readText()) {
                    Commands.authExpected -> handleAuth()
                    Commands.userIncoming -> handleUserChange()
                    Commands.taskIncoming -> handleTaskChange()
                }
            }
        }

    }

    private suspend fun DefaultClientWebSocketSession.handleAuth() {
        if (firstAuth) {
            outgoing.send(Frame.Text(authManager.token!!))
            return
        }

        authManager.refreshToken()
        outgoing.send(Frame.Text(authManager.token!!))
    }

    private suspend fun DefaultClientWebSocketSession.handleTaskChange() {
        val taskChange = (incoming.receive() as? Frame.Text)?.readText() ?: return
        val item = Json.decodeFromString<ItemChangeTask>("{\"change\":$taskChange}")

        launch {

            listeners.forEach { listener ->
                withContext(listener.coroutineContext) {
                    listener.onTaskChange(item.change)
                }
            }
        }

    }

    private suspend fun DefaultClientWebSocketSession.handleUserChange() {
        val taskChange = (incoming.receive() as? Frame.Text)?.readText() ?: return

        val item = Json.decodeFromString<ItemChangeUser>("{ \"change\": $taskChange }")

        launch {

            listeners.forEach { listener ->
                withContext(listener.coroutineContext) {
                    listener.onUserChange(item.change)
                }
            }
        }
    }

    override fun removeOnChangeListener(listener: LiveApi.Listener) {
        listeners.remove(listener)

        if (listeners.isEmpty()) {
            socketJob?.cancel()
            socketJob = null
        }
    }
}