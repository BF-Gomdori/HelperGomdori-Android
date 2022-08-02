package com.bf.helpergomdori.ui.main

import android.os.Bundle
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val binding by bindingRes<ActivityMainBinding>(R.layout.activity_main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            //todo view 작업
        }
    }
}