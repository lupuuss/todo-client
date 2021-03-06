package com.github.lupuuss.todo.client.android.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.github.lupuuss.todo.client.android.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        applyTheme()
        super.onCreate(savedInstanceState)
    }

    private fun applyTheme() {
        setTheme(R.style.Theme_Todo_Dark)
    }

    fun hideSoftKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        val view = currentFocus ?: View(this)

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}