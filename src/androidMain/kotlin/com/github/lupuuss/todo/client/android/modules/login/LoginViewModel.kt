package com.github.lupuuss.todo.client.android.modules.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lupuuss.todo.client.core.auth.AuthManager
import kotlinx.coroutines.launch

class LoginViewModel(private val authManager: AuthManager) : ViewModel() {

    val login: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    fun login() = viewModelScope.launch {

        try {
            authManager.login(login.value!!, password.value!!)
            Log.i(this::class.simpleName, "Auth ok!")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}