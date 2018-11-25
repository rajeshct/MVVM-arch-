package com.mvvm.design.pattern.network

import com.mvvm.design.pattern.model.LoginModel
import com.mvvm.design.pattern.persistance.tables.LoginResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiCalls {
    /**
     * Perform login single.
     *
     * @param loginRequest the login request
     * @return the single
     */
    @POST("api/User/Login")
    fun performLogin(@Body loginRequest: LoginModel): Single<GenericResponse<LoginResponse>>

}