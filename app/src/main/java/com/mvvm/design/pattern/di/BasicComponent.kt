package com.mvvm.design.pattern.di

import com.mvvm.design.pattern.di.module.CustomAppModule
import com.mvvm.design.pattern.di.module.NetworkModule
import com.mvvm.design.pattern.di.module.ViewModelModule
import com.mvvm.design.pattern.ui.activity.BaseActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CustomAppModule::class, NetworkModule::class, ViewModelModule::class])
interface BasicComponent {
    fun inject(baseActivity: BaseActivity)
}