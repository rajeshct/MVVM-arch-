package com.mvvm.design.pattern.repository

import android.arch.lifecycle.LiveData
import com.mvvm.design.pattern.model.LoginModel
import com.mvvm.design.pattern.network.*
import com.mvvm.design.pattern.persistance.AppLocalDatabase
import com.mvvm.design.pattern.persistance.tables.LoginResponse
import retrofit2.Retrofit
import javax.inject.Inject

class UserRepository @Inject constructor(private val retrofit: Retrofit
                                         , private val appExecutors: AppExecutors
                                         , private val appLocalDatabase: AppLocalDatabase) {


    fun makeApiCall(loginModel: LoginModel): LiveData<Resource<LoginResponse>> {
        return object : NetworkBoundResource<LoginResponse, GenericResponse<LoginResponse>>(appExecutors) {
            override fun isFetchFromDb(): Boolean {
                return false
            }

            override fun saveCallResult(item: GenericResponse<LoginResponse>) {
                item.data?.let {
                    appLocalDatabase.getLoginUserDao().insertLoginDetails(it)
                }
            }

            override fun getData(item: GenericResponse<LoginResponse>): LoginResponse {
                val loginResponse = item.data
                return if (loginResponse == null) {
                    LoginResponse()
                } else {
                    return loginResponse
                }
            }

            override fun createCall(): LiveData<ApiResponse<GenericResponse<LoginResponse>>> {
                return retrofit.create(ApiCalls::class.java).performLogin(loginModel)
            }

            override fun isSaveInDb(): Boolean {
                return true
            }

            override fun isFetchFromNetwork(data: LoginResponse?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<LoginResponse> {
                return appLocalDatabase.getLoginUserDao().getLoginResponse()
            }

        }.asLiveData()
    }

}
