package com.mvvm.design.pattern.persistance.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.mvvm.design.pattern.persistance.tables.LoginResponse

@Dao
interface LoginUserDao {
    @Query("select * from LoginResponse")
    fun getLoginResponse(): LiveData<LoginResponse>

    @Insert
    fun insertLoginDetails(loginResponse: LoginResponse): Int


}