package com.mvvm.design.pattern.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(

    @SerializedName("status")
    @Expose
    var status: Boolean = false,

    @SerializedName("data")
    @Expose
    var data: T?,

    @SerializedName("code")
    @Expose
    var code: String? = null,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    @SerializedName("more")
    @Expose
    var more: Boolean = false,

    @SerializedName("error")
    @Expose
    var error: Boolean = false
)