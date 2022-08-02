package com.bf.helpergomdori.base

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity : AppCompatActivity() {

    inline fun <reified T : ViewDataBinding> bindingRes(resId: Int) : Lazy<T> {
        return lazy {
            val binding = DataBindingUtil.setContentView<T>(this, resId)
            binding
        }
    }
}