package com.github.lupuuss.todo.client.core.repository

import com.github.lupuuss.todo.api.core.Page
import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.client.core.api.task.TasksApi
import com.github.lupuuss.todo.client.core.auth.AuthRequiredException

class TaskRepository(private val taskApi: TasksApi) {

    suspend fun getTodoTasks(pageNumber: Int, pageSize: Int): Page<Task> {
        return try {
            taskApi.getTasks(pageNumber, pageSize, Task.Status.NOT_STARTED)
        } catch (eAuth: AuthRequiredException) {
            throw eAuth
        } catch (e: Throwable) {
            throw ResourceNotReachedException(e)
        }
    }
}