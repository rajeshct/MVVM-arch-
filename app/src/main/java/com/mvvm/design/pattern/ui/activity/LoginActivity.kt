package com.mvvm.design.pattern.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.widget.FrameLayout
import com.mvvm.design.pattern.R
import com.mvvm.design.pattern.databinding.ActivityLoginBinding
import com.mvvm.design.pattern.di.BasicComponent
import com.mvvm.design.pattern.model.LoginViewModel
import com.mvvm.design.pattern.network.Status


class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
        binding.setLifecycleOwner(this)
        binding.loginModel = loginViewModel
        registerCallback()
    }

    private fun registerCallback() {
        loginViewModel.loginCallback().observe(this, Observer {
            if (it != null) {
                binding.resource = it
                if (it.status == Status.ERROR) {
                    val snackBar = Snackbar.make(binding.topLayout, getString(R.string.error_no_internet), Snackbar.LENGTH_SHORT)
                    val view = snackBar.view
                    val params = view.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.TOP
                    view.layoutParams = params
                    snackBar.show()
                }
            }
        })
    }

    override fun injectComponent(basicComponent: BasicComponent) {
        basicComponent.inject(this)
    }


}
