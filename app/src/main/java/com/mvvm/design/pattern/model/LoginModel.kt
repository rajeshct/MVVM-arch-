package com.mvvm.design.pattern.model

import android.text.TextUtils
import android.util.Patterns
import com.mvvm.design.pattern.utils.Constants

data class LoginModel(var email: String = "", var password: String = "") {

    private fun isValidEmail(): Boolean {
        return !TextUtils.isEmpty(email)
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(): Boolean {
        return !TextUtils.isEmpty(password) && password.length > 2
    }

    fun isAbleToLogin(): Int {
        return if (!isValidEmail()) {
            Constants.INVALID_EMAIL
        } else if (!isValidPassword()) {
            Constants.INVALID_PASSWORD
        } else {
            Constants.PERFORM_LOGIN
        }
    }

    fun onPasswordChanged(newText: CharSequence) {
        password = newText.toString()
    }

    fun onEmailChanged(newText: CharSequence) {
        email = newText.toString()
    }
}