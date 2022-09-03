package com.bf.helpergomdori.ui.request

import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestMatchingBinding
import com.bf.helpergomdori.ui.main.MainActivity

class RequestMatchingFragment: BaseFragment<FragmentRequestMatchingBinding>(R.layout.fragment_request_matching) {

    private val viewModel: RequestViewModel by activityViewModels()

    override fun createView(binding: FragmentRequestMatchingBinding) {

    }

    override fun createFragment() {
        binding.btnWeMet.setOnClickListener {
            navController?.navigate(R.id.action_requestMatchingFragment_to_requestFinishedFragment)
        }
    }

}