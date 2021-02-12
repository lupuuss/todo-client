package com.github.lupuuss.todo.client.core.api

import com.github.lupuuss.todo.api.core.Page
import com.github.lupuuss.todo.api.core.task.Task

interface TasksApi {

    suspend fun getTasks(pageNumber: Int, pageSize: Int, status: Task.Status? = null): Page<Task>
}