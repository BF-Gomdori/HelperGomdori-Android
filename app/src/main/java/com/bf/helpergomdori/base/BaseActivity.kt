package com.bf.helpergomdori.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B: ViewDataBinding>(private val resId: Int) : AppCompatActivity() {
    private var _binding: B? = null
    val binding get() = _binding!!

    abstract fun createActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView<B>(this, resId)
        setContentView(binding.root)
        createActivity()
    }

    inline fun <reified T : ViewDataBinding> bindingRes(resId: Int) : Lazy<T> {
        return lazy {
            val binding = DataBindingUtil.setContentView<T>(this, resId)
            binding
        }
    }
}