package com.mvvm.design.pattern.persistance

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.mvvm.design.pattern.persistance.dao.LoginUserDao
import com.mvvm.design.pattern.persistance.tables.LoginResponse

@Database(entities = [LoginResponse::class], version = 1)
abstract class AppLocalDatabase : RoomDatabase() {
    abstract fun getLoginUserDao(): LoginUserDao
}