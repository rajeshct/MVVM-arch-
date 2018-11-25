package com.mvvm.design.pattern.ui.activity

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mvvm.design.pattern.CustomApplication
import com.mvvm.design.pattern.di.BasicComponent
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent((application as CustomApplication).getBasicComponent())
    }

    abstract fun injectComponent(basicComponent: BasicComponent)

}