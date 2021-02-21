package com.github.lupuuss.todo.client.core.api.me

import com.github.lupuuss.todo.api.core.Page
import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.api.core.user.User

interface MyTasksApi {

    suspend fun getMyTasks(pageNumber: Int, pageSize: Int, status: Task.Status? = null): Page<Task>
}