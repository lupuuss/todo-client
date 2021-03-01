package com.github.lupuuss.todo.client.android

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.lupuuss.todo.client.android.di.DIViewModelFactory
import com.github.lupuuss.todo.client.android.modules.login.LoginViewModel
import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.storage.SharedPrefsStorage
import com.github.lupuuss.todo.client.core.storage.Storage
import kotlinx.coroutines.Dispatchers
import org.kodein.di.*
import kotlin.coroutines.CoroutineContext

class TodoApp : Application(), DIAware {

    override lateinit var di: DI

    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()

        TodoKodein.init {
            pre {
                bind<Storage>() with singleton { SharedPrefsStorage(applicationContext) }
                bind<CoroutineContext>(tag = "Networking") with provider { Dispatchers.IO }
            }

            post {
                bind<LoginViewModel>() with provider { LoginViewModel(instance()) }
            }
        }

        di = TodoKodein.di

        viewModelFactory = DIViewModelFactory(di)
    }
}

fun <T : ViewModel> FragmentActivity.provideViewModel(cls: Class<T>): T {
    val factory = (this.application as TodoApp).viewModelFactory

    return ViewModelProvider(this, factory).get(cls)
}