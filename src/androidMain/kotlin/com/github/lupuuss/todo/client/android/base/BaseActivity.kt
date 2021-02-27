package com.github.lupuuss.todo.client.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.lupuuss.todo.client.android.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        applyTheme()
        super.onCreate(savedInstanceState)
    }

    private fun applyTheme() {
        setTheme(R.style.Theme_Todo_Dark)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}