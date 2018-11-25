package com.mvvm.design.pattern.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.mvvm.design.pattern.R
import com.mvvm.design.pattern.databinding.ActivityHomePageBinding
import com.mvvm.design.pattern.di.BasicComponent
import com.mvvm.design.pattern.model.LoginViewModel


class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityHomePageBinding>(
            this
            , R.layout.activity_home_page
        )
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
        binding.homePageModel = loginViewModel
    }

    override fun injectComponent(basicComponent: BasicComponent) {
        basicComponent.inject(this)
    }


}
