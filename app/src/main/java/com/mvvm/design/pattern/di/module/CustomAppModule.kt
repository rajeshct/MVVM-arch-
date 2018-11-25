package com.mvvm.design.pattern.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.mvvm.design.pattern.persistance.AppLocalDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CustomAppModule(private val customApplication: Application) {

    @Provides
    @Singleton
    fun getApplication(): Application {
        return customApplication
    }

    @Provides
    @Singleton
    fun getDatabaseInstance(): AppLocalDatabase {
        return Room.databaseBuilder(
            customApplication, AppLocalDatabase::class.java
            , "database-mvvmapp"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}