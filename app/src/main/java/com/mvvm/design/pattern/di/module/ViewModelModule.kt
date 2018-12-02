package com.mvvm.design.pattern.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mvvm.design.pattern.di.ViewModelFactory
import com.mvvm.design.pattern.di.ViewModelKey
import com.mvvm.design.pattern.model.viewmodel.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun postListViewModel(viewModel: LoginViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    //Add more ViewModels here
}