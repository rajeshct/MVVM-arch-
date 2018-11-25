package com.mvvm.design.pattern.repository

import com.mvvm.design.pattern.model.LoginModel
import com.mvvm.design.pattern.persistance.AppLocalDatabase
import com.mvvm.design.pattern.persistance.tables.LoginResponse
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(private val appLocalDatabase: AppLocalDatabase) {


    fun saveToLocalDb(loginModel: LoginModel): Single<Int> {
        val loginResponse = LoginResponse(userEmail = loginModel.email)
        return Single.fromCallable {
            appLocalDatabase.getLoginUserDao()
                .insertLoginDetails(loginResponse)
        }
    }

}
