package com.github.lupuuss.todo.client.android

import android.app.Application
import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.storage.SharedPrefsStorage
import com.github.lupuuss.todo.client.core.storage.Storage
import kotlinx.coroutines.Dispatchers
import org.kodein.di.*
import kotlin.coroutines.CoroutineContext

class TodoApp : Application(), DIAware {

    override lateinit var di: DI

    override fun onCreate() {
        super.onCreate()

        TodoKodein.init {
            bind<Storage>() with singleton { SharedPrefsStorage(applicationContext) }
            bind<CoroutineContext>(tag = "Networking") with provider { Dispatchers.IO }
        }

        di = TodoKodein.di
    }

}