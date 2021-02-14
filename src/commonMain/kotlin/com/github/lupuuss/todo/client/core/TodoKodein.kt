package com.github.lupuuss.todo.client.core

import com.github.lupuuss.todo.client.core.api.KtorTaskApi
import com.github.lupuuss.todo.client.core.api.TasksApi
import com.github.lupuuss.todo.client.core.auth.JwtAuth
import com.github.lupuuss.todo.client.core.auth.TokenHolder
import com.github.lupuuss.todo.client.core.repository.TaskRepository
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.kodein.di.*


object TodoKodein {

    lateinit var di: DI
    private set

    private val baseUrl = "http://localhost:9090"

    fun init(platformSpecific: DI.MainBuilder.() -> Unit) {

        di = DI {

            platformSpecific()

            bind<HttpClient>() with singleton {
                HttpClient {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }

                    install(feature = JwtAuth) {
                        authClient = HttpClient()
                        refreshUrl = "$baseUrl/auth/token"
                        tokenHolder = instance()
                    }
                }
            }
            bind<TasksApi>() with singleton { KtorTaskApi(baseUrl, instance()) }
            bind<TaskRepository>() with singleton { TaskRepository(instance()) }
        }
    }
}