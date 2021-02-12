package com.github.lupuuss.todo.client.core

import com.github.lupuuss.todo.client.core.api.KtorTaskApi
import com.github.lupuuss.todo.client.core.api.TasksApi
import com.github.lupuuss.todo.client.core.repository.TaskRepository
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.kodein.di.*


object TodoKodein {

    val di = DI {
        bind<HttpClient>() with provider { HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        } }
        bind<TasksApi>() with singleton { KtorTaskApi("http://localhost:9090", instance()) }
        bind<TaskRepository>() with singleton { TaskRepository(instance()) }
    }
}