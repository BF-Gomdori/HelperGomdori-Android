package com.bf.helpergomdori.ui.request

import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestWriteBinding

class RequestWriteFragment: BaseFragment<FragmentRequestWriteBinding>(R.layout.fragment_request_write) {
    override fun createView(binding: FragmentRequestWriteBinding) {
        binding.btnOk.setOnClickListener {
            navController?.navigate(R.id.action_requestWriteFragment_to_requestLocationFragment)
        }
    }

    override fun createFragment() {
    }
}