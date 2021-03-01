package com.github.lupuuss.todo.client.android

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.lupuuss.todo.client.android.di.DIViewModelFactory
import com.github.lupuuss.todo.client.android.modules.home.HomeViewModel
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
            bind<Storage>() with singleton { SharedPrefsStorage(applicationContext) }
            bind<CoroutineContext>(tag = "Networking") with provider { Dispatchers.IO }

            bind<LoginViewModel>() with provider { LoginViewModel(instance()) }
            bind<HomeViewModel>() with provider { HomeViewModel() }
        }

        di = TodoKodein.di

        viewModelFactory = DIViewModelFactory(di)
    }
}

inline fun <reified T : ViewModel> FragmentActivity.provideViewModel(): T {
    val factory = (this.application as TodoApp).viewModelFactory

    return ViewModelProvider(this, factory).get(T::class.java)
}