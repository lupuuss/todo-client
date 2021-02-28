package com.github.lupuuss.todo.client.android.modules

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.lupuuss.todo.client.android.R
import com.github.lupuuss.todo.client.android.modules.login.LoginActivity
import com.github.lupuuss.todo.client.core.TodoKodein
import com.github.lupuuss.todo.client.core.auth.AuthManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.instance
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.seconds


class StartActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val authManager: AuthManager by TodoKodein.di.instance()

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        launch {

            val duration = measureTime {

                if (!authManager.isUserLoggedIn()) return@measureTime

                try {
                    authManager.refreshToken()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            delay(maxOf(0.seconds, 1.5.seconds - duration))

            if (authManager.isUserLoggedIn()) {
                // todo
            } else {
                startActivity(Intent(this@StartActivity, LoginActivity::class.java))
            }
        }
    }
}