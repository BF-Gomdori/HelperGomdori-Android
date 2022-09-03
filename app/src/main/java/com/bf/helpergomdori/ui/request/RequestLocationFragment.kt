package com.bf.helpergomdori.ui.request

import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestLocationBinding

class RequestLocationFragment :
    BaseFragment<FragmentRequestLocationBinding>(R.layout.fragment_request_location) {
    override fun createView(binding: FragmentRequestLocationBinding) {
        setListener()
    }

    override fun createFragment() {
    }

    private fun setListener() {
        binding.btnRequest.setOnClickListener {
            navController?.navigate(R.id.action_requestLocationFragment_to_requestIngFragment)
        }
    }
}