package com.github.lupuuss.todo.client.core.repository

import com.github.lupuuss.todo.api.core.Page
import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.client.core.api.me.CurrentUserApi
import com.github.lupuuss.todo.client.core.auth.AuthRequiredException

class TaskRepository(private val taskApi: CurrentUserApi) {

    suspend fun getTodoTasks(pageNumber: Int, pageSize: Int): Page<Task> {
        return try {
            taskApi.getMyTasks(pageNumber, pageSize, Task.Status.NOT_STARTED)
        } catch (eAuth: AuthRequiredException) {
            throw eAuth
        } catch (e: Throwable) {
            throw ResourceNotReachedException(e)
        }
    }
}