package com.github.lupuuss.todo.client.android.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class LoadingViewModel : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    open fun canLoad(): Boolean {
        return true
    }

    fun load(loading: suspend () -> Unit) {
        if (!canLoad()) return

        viewModelScope.launch {
            isLoading.value = true

            try {
                loading()
            } catch (e: Exception) {
                onException(e)
            }

            isLoading.value = false
        }
    }

    open fun onException(e: Exception) {
        e.printStackTrace()
    }
}