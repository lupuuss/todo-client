package com.github.lupuuss.todo.client.core

import com.github.lupuuss.todo.api.core.user.User
import com.github.lupuuss.todo.client.core.auth.AuthManager
import com.github.lupuuss.todo.client.core.repository.MyTaskRepository
import org.kodein.di.DI
import org.kodein.di.instance

interface InstanceHandler<T> {
    fun provide(): T
    fun clean(instance: T)
}

class SessionKodeinConfigure() {
    lateinit var myTaskRepositoryHandler: InstanceHandler<MyTaskRepository>
}

class SessionKodein(di: DI, configureBlock: SessionKodeinConfigure.() -> Unit) : AuthManager.OnAuthStatusChangedListener {

    private val configure = SessionKodeinConfigure().apply(configureBlock)

    private val authManager: AuthManager by di.instance()

    var myTaskRepository: MyTaskRepository? = null
    private set

    init {
        authManager.addOnAuthChangedListener(this)

        if (authManager.isUserLoggedIn()) {
            myTaskRepository = configure.myTaskRepositoryHandler.provide()
        }
    }

    override fun onAuthChanged(user: User?) {
        if (user == null) {
            myTaskRepository?.let {
                configure.myTaskRepositoryHandler.clean(it)
            }
            myTaskRepository = null
            return
        }

        myTaskRepository = configure.myTaskRepositoryHandler.provide()
    }
}