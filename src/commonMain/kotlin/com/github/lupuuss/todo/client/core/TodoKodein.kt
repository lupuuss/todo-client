package com.github.lupuuss.todo.client.core

import com.github.lupuuss.todo.client.core.api.KtorTaskApi
import com.github.lupuuss.todo.client.core.api.TasksApi
import com.github.lupuuss.todo.client.core.auth.JwtAuth
import com.github.lupuuss.todo.client.core.auth.SynchronizedTokenHolder
import com.github.lupuuss.todo.client.core.auth.TokenHolder
import com.github.lupuuss.todo.client.core.repository.TaskRepository
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.kodein.di.*


object TodoKodein {

    val di = DI {
        bind<TokenHolder>() with singleton { SynchronizedTokenHolder("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJ0b2RvLWFwaS1pc3N1ZXIiLCJpZCI6IjYwMTgzMWFjYjExNTRiMjUxNDA2MDg2YSIsImV4cCI6MTYxMzI1NTAxMX0.23ArAgQdFrZs7WMOKFYR1Ic9UqF92rYlVyjCCVhzL1w-PwI778ba4MUmWzUBRNV-6gW2-2pr4mk8mofgu4WAzg") }
        bind<HttpClient>() with provider {
            HttpClient() {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }

                install(feature = JwtAuth) {
                    authClient = HttpClient()
                    refreshUrl = "http://localhost:9090/auth/token"
                    tokenHolder = instance()
                }
            }
        }
        bind<TasksApi>() with singleton { KtorTaskApi("http://localhost:9090", instance()) }
        bind<TaskRepository>() with singleton { TaskRepository(instance()) }
    }
}