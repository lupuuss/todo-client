package com.github.lupuuss.todo.client.core

import com.github.lupuuss.todo.client.core.api.auth.AuthApi
import com.github.lupuuss.todo.client.core.api.auth.KtorAuthApi
import com.github.lupuuss.todo.client.core.api.me.KtorCurrentUserApi
import com.github.lupuuss.todo.client.core.api.me.CurrentUserApi
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

            bind<HttpClient>() with singleton {
                HttpClient {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }

                    install(feature = JwtAuth) {
                        authClient = instance(tag = "Auth")
                        refreshUrl = "$baseUrl/auth/token"
                        tokenHolder = instance()
                    }
                }
            }
            bind<CurrentUserApi>() with singleton { KtorCurrentUserApi(baseUrl, instance()) }
            bind<MyTaskRepository>() with singleton { MyTaskRepository(instance(), instance(tag = "Networking")) }

            bind<AuthApi>() with singleton{ KtorAuthApi(baseUrl, instance(tag = "Auth")) }
            bind<AuthManager>() with singleton { AuthManager(instance(), instance(), instance(), instance(tag = "Networking")) }
        }
    }
}