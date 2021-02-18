package com.github.lupuuss.todo.client.core.api.live

import com.github.lupuuss.todo.api.core.live.ItemChange
import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.api.core.user.User
import kotlin.coroutines.CoroutineContext

interface LiveApi {

    interface Listener : CoroutineContext {
        fun onTaskChange(change: ItemChange<Task>)

        fun onUserChange(change: ItemChange<User>)
    }

    fun addOnChangeListener(listener: Listener)

    fun removeOnChangeListener(listener: Listener)
}