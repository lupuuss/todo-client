package com.github.lupuuss.todo.client.core.storage

import android.content.Context

class SharedPrefsStorage(context: Context) : Storage {

    private val sharedPreferences = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)

    override fun init() = Unit

    override fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun set(key: String, value: String?) {

        if (value == null) {
            sharedPreferences
                .edit()
                .remove(key)
                .apply()

            return
        }

        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    override fun close() = Unit
}