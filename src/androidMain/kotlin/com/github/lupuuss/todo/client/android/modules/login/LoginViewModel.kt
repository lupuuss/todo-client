package com.github.lupuuss.todo.client.android.modules.login

import androidx.lifecycle.MutableLiveData
import com.github.lupuuss.todo.client.android.base.LoadingViewModel
import com.github.lupuuss.todo.client.android.modules.errors.LoginError
import com.github.lupuuss.todo.client.android.modules.errors.PasswordError
import com.github.lupuuss.todo.client.core.auth.AuthManager

class LoginViewModel(private val authManager: AuthManager) : LoadingViewModel() {

    val login: MutableLiveData<String> = MutableLiveData()
    val loginError: MutableLiveData<LoginError> = MutableLiveData()

    val password: MutableLiveData<String> = MutableLiveData()
    val passwordError: MutableLiveData<PasswordError> = MutableLiveData()

    val loginResult: MutableLiveData<Boolean> = MutableLiveData(null)

    init {
        login.observeForever(this::onLoginChange)
        password.observeForever(this::onPasswordChange)
    }

    private fun onPasswordChange(str: String?) {

        var error: PasswordError? = null

        if (str.isNullOrBlank()) {
            error = PasswordError.EMPTY
        }

        passwordError.value = error
    }

    private fun onLoginChange(str: String?) {

        var error: LoginError? = null

        if (str.isNullOrBlank()) {
            error = LoginError.EMPTY
        }

        loginError.value = error
    }

    override fun canLoad(): Boolean {

        onLoginChange(login.value)
        onPasswordChange(password.value)

        return loginError.value == null && passwordError.value == null
    }

    fun login() = load {
        authManager.login(login.value!!, password.value!!)
        loginResult.value = true
    }

    override fun onException(e: Exception) {
        super.onException(e)
        loginResult.value = false
    }

    override fun onCleared() {
        super.onCleared()

        login.removeObserver(this::onLoginChange)
        password.removeObserver(this::onPasswordChange)
    }
}