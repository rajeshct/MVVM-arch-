package com.mvvm.design.pattern.network

interface IErrorSuccessCallback<T> {
    fun onError(message: String)
    fun noData()
    fun data(data: T)
}