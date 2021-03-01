package com.github.lupuuss.todo.client.android.modules.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.lupuuss.todo.client.android.R
import com.github.lupuuss.todo.client.android.base.BaseActivity
import com.github.lupuuss.todo.client.android.databinding.ActivityHomeBinding
import com.github.lupuuss.todo.client.android.provideViewModel

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = provideViewModel()
    }
}