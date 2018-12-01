package com.mvvm.design.pattern.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.os.Handler
import com.mvvm.design.pattern.network.Resource
import com.mvvm.design.pattern.persistance.tables.LoginResponse
import com.mvvm.design.pattern.repository.UserRepository
import com.mvvm.design.pattern.utils.Constants
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository)
    : CustomObservableViewModel() {

    private val result = MediatorLiveData<Resource<LoginResponse>>()

    private var status = Constants.INVALID_CHOICE

    private val loginModel = LoginModel()

    fun getStatus(): Int {
        return status
    }

    fun getLoginModel(): LoginModel {
        return loginModel
    }

    fun loginCallback(): LiveData<Resource<LoginResponse>> {
        return result
    }

    private fun performLogin() {
        if (status == Constants.PERFORM_LOGIN) {
            val originalResource = userRepository.makeApiCall(loginModel)
            result.addSource(originalResource) {
                result.value = it
            }
        }
    }

    fun onLoginClick() {
        when (loginModel.isAbleToLogin()) {

            Constants.INVALID_EMAIL -> {
                this.status = Constants.INVALID_EMAIL
                removeError()
            }

            Constants.INVALID_PASSWORD -> {
                this.status = Constants.INVALID_PASSWORD
                removeError()
            }

            else -> {
                this.status = Constants.PERFORM_LOGIN
                performLogin()
            }
        }
        notifyChange()
    }

    private fun removeError() {
        Handler().postDelayed({
            status = Constants.INVALID_CHOICE
            notifyChange()
        }, 2000L)
    }
}
