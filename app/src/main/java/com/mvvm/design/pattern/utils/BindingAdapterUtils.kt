package com.mvvm.design.pattern.utils

import android.databinding.BindingAdapter
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.widget.ImageView

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) {

}

@BindingAdapter("showError")
fun TextInputLayout.showError(showError: String) {
    requestFocus()
    error = if (TextUtils.isEmpty(showError)) null else showError
}
