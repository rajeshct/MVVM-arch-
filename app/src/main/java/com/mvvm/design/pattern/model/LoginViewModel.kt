package com.mvvm.design.pattern.model

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import android.os.Handler
import com.mvvm.design.pattern.repository.UserRepository
import com.mvvm.design.pattern.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val status = ObservableInt(Constants.INVALID_CHOICE)

    private val loginModel = LoginModel()

    fun getStatus(): ObservableInt {
        return status
    }

    fun getLoginModel(): LoginModel {
        return loginModel
    }

    fun onLoginClick() {
        when (loginModel.isAbleToLogin()) {

            Constants.INVALID_EMAIL -> {
                status.set(Constants.INVALID_EMAIL)
            }

            Constants.INVALID_PASSWORD -> {
                status.set(Constants.INVALID_PASSWORD)
            }

            else -> {
                status.set(Constants.PERFORM_LOGIN)
                userRepository.saveToLocalDb(loginModel)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }
        Handler().postDelayed({ status.set(Constants.INVALID_CHOICE) }, 2000L)
    }
}
