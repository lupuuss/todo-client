package com.github.lupuuss.todo.client.android.modules.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.lupuuss.todo.client.android.R
import com.github.lupuuss.todo.client.android.base.BaseActivity
import com.github.lupuuss.todo.client.android.databinding.ActivityLoginBinding
import com.github.lupuuss.todo.client.android.modules.errors.LoginError
import com.github.lupuuss.todo.client.android.modules.errors.PasswordError
import com.github.lupuuss.todo.client.android.modules.home.HomeActivity
import com.github.lupuuss.todo.client.android.provideViewModel
import com.google.android.material.snackbar.Snackbar

class LoginActivity : BaseActivity() {

    private var snackbar: Snackbar? = null
    private lateinit var binds: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binds = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val viewModel = provideViewModel<LoginViewModel>()

        binds.viewModel = viewModel
        binds.lifecycleOwner = this

        viewModel.loginResult.observe(this, this::onLoginResult)
        viewModel.passwordError.observe(this, this::onPasswordError)
        viewModel.loginError.observe(this, this::onLoginError)

    }

    private fun onLoginError(loginError: LoginError?) {
        onLoginResult(null)
        binds.login.error = when (loginError) {
            LoginError.EMPTY -> getString(R.string.error_field_empty)
            null -> ""
        }
    }

    private fun onPasswordError(passwordError: PasswordError?) {
        onLoginResult(null)
        binds.password.error = when (passwordError) {
            PasswordError.EMPTY -> getString(R.string.error_field_empty)
            null -> ""
        }
    }

    private fun onLoginResult(result: Boolean?) {

        when (result) {
            true -> startActivity(Intent(this, HomeActivity::class.java))
            false -> {
                snackbar = Snackbar
                    .make(findViewById(android.R.id.content), R.string.error_auth, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        snackbar?.dismiss()
                    }.also { it.show() }
            }
            null -> {
                snackbar?.dismiss()
            }
        }
    }
}