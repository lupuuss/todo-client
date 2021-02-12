package com.github.lupuuss.todo.client.core.repository

import com.github.lupuuss.todo.api.core.Page
import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.client.core.api.TasksApi

class TaskRepository(private val taskApi: TasksApi) {

    suspend fun getTodoTasks(pageNumber: Int, pageSize: Int): Page<Task> {
        return taskApi.getTasks(pageNumber, pageSize, Task.Status.NOT_STARTED)
    }
}