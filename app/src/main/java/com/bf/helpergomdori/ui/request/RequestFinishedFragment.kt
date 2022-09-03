package com.bf.helpergomdori.ui.request

import android.content.Intent
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestFinishedBinding
import com.bf.helpergomdori.ui.main.MainActivity

class RequestFinishedFragment: BaseFragment<FragmentRequestFinishedBinding>(R.layout.fragment_request_finished) {
    override fun createView(binding: FragmentRequestFinishedBinding) {
        setListener()
    }

    override fun createFragment() {
    }

    private fun setListener(){
        binding.apply {
            btnGoToHome.setOnClickListener {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        }
    }
}