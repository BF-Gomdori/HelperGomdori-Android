package com.bf.helpergomdori.ui.request

import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestMatchingBinding

class RequestMatchingFragment: BaseFragment<FragmentRequestMatchingBinding>(R.layout.fragment_request_matching) {
    override fun createView(binding: FragmentRequestMatchingBinding) {
        binding.btnWeMet.setOnClickListener {
            navController?.navigate(R.id.action_requestMatchingFragment_to_requestFinishedFragment)
        }
    }

    override fun createFragment() {
    }

}