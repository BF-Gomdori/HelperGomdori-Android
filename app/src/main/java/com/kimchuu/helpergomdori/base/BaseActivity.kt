package com.kimchuu.helpergomdori.base

import android.os.Bundle
import android.os.PersistableBundle
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