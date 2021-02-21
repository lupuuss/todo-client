package com.github.lupuuss.todo.client.core

import com.github.lupuuss.todo.client.core.api.auth.AuthApi
import com.github.lupuuss.todo.client.core.api.auth.KtorAuthApi
import com.github.lupuuss.todo.client.core.api.me.KtorMyTasksApi
import com.github.lupuuss.todo.client.core.api.me.MyTasksApi
import com.github.lupuuss.todo.client.core.auth.AuthManager
import com.github.lupuuss.todo.client.core.auth.JwtAuth
import com.github.lupuuss.todo.client.core.repository.MyTaskRepository
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.kodein.di.*


object TodoKodein {

    lateinit var di: DI
    private set

    private const val baseUrl = "http://localhost:9090"

    fun init(platformSpecific: DI.MainBuilder.() -> Unit) {

        di = DI {

            platformSpecific()

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
            bind<MyTaskRepository>() with singleton { MyTaskRepository(instance(), instance(tag = "Networking")) }
        }
    }
}