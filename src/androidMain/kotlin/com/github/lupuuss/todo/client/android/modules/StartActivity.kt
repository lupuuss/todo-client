package com.github.lupuuss.todo.client.android.modules

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.lupuuss.todo.client.android.R
import com.github.lupuuss.todo.client.android.modules.home.HomeActivity
import com.github.lupuuss.todo.client.android.modules.login.LoginActivity
import com.github.lupuuss.todo.client.core.auth.AuthManager
import kotlinx.coroutines.*
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.instance
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.seconds


class StartActivity : AppCompatActivity(), DIAware, CoroutineScope by MainScope() {

    override val di by di()

    private val authManager: AuthManager by instance()

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
                startActivity(Intent(this@StartActivity, HomeActivity::class.java))
            } else {
                startActivity(Intent(this@StartActivity, LoginActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}