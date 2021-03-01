package com.github.lupuuss.todo.client.android.modules.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.lupuuss.todo.client.android.R
import com.github.lupuuss.todo.client.android.base.BaseActivity
import com.github.lupuuss.todo.client.android.databinding.ActivityLoginBinding
import com.github.lupuuss.todo.client.android.provideViewModel

class LoginActivity : BaseActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.viewModel = provideViewModel(LoginViewModel::class.java)
    }
}