package com.bf.helpergomdori.ui.request

import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestIngBinding

class RequestIngFragment: BaseFragment<FragmentRequestIngBinding>(R.layout.fragment_request_ing) {
    override fun createView(binding: FragmentRequestIngBinding) {
        binding.btnRequestIng.setOnClickListener {
            navController?.navigate(R.id.action_requestIngFragment_to_requestMatchingFragment)
        }
    }

    override fun createFragment() {
    }

}