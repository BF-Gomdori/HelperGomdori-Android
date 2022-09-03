package com.bf.helpergomdori.ui.request

import android.content.Intent
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestIngBinding
import com.bf.helpergomdori.ui.main.MainActivity

class RequestIngFragment: BaseFragment<FragmentRequestIngBinding>(R.layout.fragment_request_ing) {
    override fun createView(binding: FragmentRequestIngBinding) {

    }

    override fun createFragment() {
        binding.btnRequestIng.setOnClickListener {
            //navController?.navigate(R.id.action_requestIngFragment_to_requestMatchingFragment)
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }

}