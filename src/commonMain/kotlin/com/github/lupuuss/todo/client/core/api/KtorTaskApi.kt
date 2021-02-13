package com.github.lupuuss.todo.client.core.api

import com.github.lupuuss.todo.api.core.Page
import com.github.lupuuss.todo.api.core.task.Task
import io.ktor.client.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


@Serializable
internal class PageWrapper(val page: Page<Task>)

class KtorTaskApi(baseUrl: String, client: HttpClient) : KtorClientBase(baseUrl, client), TasksApi {

    override suspend fun getTasks(pageNumber: Int, pageSize: Int, status: Task.Status?): Page<Task> {

        // as kotlinx.serialization doesn't fully support generics for now,
        // deserialization of Page<T> requires simple wrapper to work

        val json = get<String>("/me/task", mapOf(
            "pageNumber" to pageNumber,
            "pageSize" to pageSize,
            "status" to status
        ))

        val tmp = "{ \"page\": $json }"

        return Json.decodeFromString<PageWrapper>(tmp).page
    }
}