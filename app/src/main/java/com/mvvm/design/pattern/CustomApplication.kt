package com.mvvm.design.pattern

import android.app.Application
import com.mvvm.design.pattern.di.BasicComponent
import com.mvvm.design.pattern.di.DaggerBasicComponent
import com.mvvm.design.pattern.di.module.CustomAppModule
import com.mvvm.design.pattern.di.module.NetworkModule

class CustomApplication : Application() {
    private lateinit var baseComponents: BasicComponent

    override fun onCreate() {
        super.onCreate()
        baseComponents = DaggerBasicComponent.builder()
            .customAppModule(CustomAppModule(this))
            .networkModule(NetworkModule(BuildConfig.BASE_URL))
            .build()
    }

    fun getBasicComponent(): BasicComponent {
        return baseComponents
    }


}