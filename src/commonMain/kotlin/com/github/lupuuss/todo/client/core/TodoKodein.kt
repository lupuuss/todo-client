package com.github.lupuuss.todo.client.core

import com.github.lupuuss.todo.client.core.api.auth.AuthApi
import com.github.lupuuss.todo.client.core.api.auth.KtorAuthApi
import com.github.lupuuss.todo.client.core.api.live.KtorWebSocketLiveApi
import com.github.lupuuss.todo.client.core.api.live.LiveApi
import com.github.lupuuss.todo.client.core.api.me.KtorMyTasksApi
import com.github.lupuuss.todo.client.core.api.me.MyTasksApi
import com.github.lupuuss.todo.client.core.auth.AuthManager
import com.github.lupuuss.todo.client.core.auth.JwtAuth
import com.github.lupuuss.todo.client.core.repository.MyTaskRepository
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.websocket.*
import org.kodein.di.*


class TodoKodeinConfig {
    var preFunction: DI.MainBuilder.() -> Unit = {}
    var postFunction: DI.MainBuilder.() -> Unit = {}

    fun pre(block: DI.MainBuilder.() -> Unit) {
        preFunction = block
    }

    fun post(block: DI.MainBuilder.() -> Unit) {
        postFunction = block
    }
}

object TodoKodein {

    lateinit var di: DI
    private set

    private const val baseUrl = "https://todo-api-rest-ktor.herokuapp.com"
    private const val baseUrlWs = "wss://todo-api-rest-ktor.herokuapp.com"

    fun init(configBlock: TodoKodeinConfig.() -> Unit) {

        val config = TodoKodeinConfig().apply(configBlock)

        di = DI {

            config.preFunction(this)

            bind<HttpClient>(tag = "WebSocket") with singleton {
                HttpClient {
                    install(WebSockets)
                }
            }

            bind<HttpClient>(tag = "Auth") with singleton {
                HttpClient() {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            }

            bind<AuthApi>() with singleton{ KtorAuthApi(baseUrl, instance(tag = "Auth")) }

            bind<AuthManager>() with singleton { AuthManager(instance(), instance(), instance(tag = "Networking")) }

            bind<HttpClient>() with singleton {
                HttpClient {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }

                    val authManager: AuthManager = instance()

                    install(feature = JwtAuth) {
                        tokenProvider = { authManager.token }
                        tokenRefreshCallback = { authManager.refreshToken() }
                    }
                }
            }
            bind<MyTasksApi>() with singleton { KtorMyTasksApi(baseUrl, instance()) }

            bind<LiveApi>() with singleton {
                KtorWebSocketLiveApi(
                    instance(),
                    baseUrlWs,
                    instance(tag = "WebSocket"),
                    instance(tag = "Networking")
                )
            }

            bind<SessionKodein>() with eagerSingleton {
                SessionKodein(di) {

                    myTaskRepositoryHandler = object : InstanceHandler<MyTaskRepository> {

                        override fun provide() =
                            MyTaskRepository(instance(), instance(), instance(tag = "Networking")).apply { init() }

                        override fun clean(instance: MyTaskRepository) = instance.close()

                    }
                }
            }

            bind<MyTaskRepository>() with provider { instance<SessionKodein>().myTaskRepository!! }

            config.postFunction(this)
        }
    }
}