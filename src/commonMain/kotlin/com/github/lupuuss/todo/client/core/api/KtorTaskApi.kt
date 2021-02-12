package com.github.lupuuss.todo.client.core.api

import com.github.lupuuss.todo.api.core.Page
import com.github.lupuuss.todo.api.core.task.Task
import io.ktor.client.*


class KtorTaskApi(baseUrl: String, client: HttpClient) : KtorClientBase(baseUrl, client), TasksApi {

    override suspend fun getTasks(pageNumber: Int, pageSize: Int, status: Task.Status?): Page<Task> {
        TODO()
    }
}