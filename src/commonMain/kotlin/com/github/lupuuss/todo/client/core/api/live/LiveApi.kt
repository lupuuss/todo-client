package com.github.lupuuss.todo.client.core.api.live

import com.github.lupuuss.todo.api.core.live.ItemChange
import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.api.core.user.User
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface LiveApi {

    interface Listener : CoroutineScope {
        fun onTaskChange(change: ItemChange<Task>)

        fun onUserChange(change: ItemChange<User>)
    }

    fun addOnChangeListener(listener: Listener)

    fun removeOnChangeListener(listener: Listener)
}