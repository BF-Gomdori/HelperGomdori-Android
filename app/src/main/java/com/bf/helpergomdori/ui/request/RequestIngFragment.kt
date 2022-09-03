package com.bf.helpergomdori.ui.request

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestIngBinding
import com.bf.helpergomdori.ui.main.MainActivity
import kotlinx.coroutines.flow.collect

class RequestIngFragment: BaseFragment<FragmentRequestIngBinding>(R.layout.fragment_request_ing) {

    private val viewModel: RequestViewModel by activityViewModels()

    override fun createView(binding: FragmentRequestIngBinding) {

    }

    override fun createFragment() {
        observeViewModel()
        setListener()
    }

    private fun observeViewModel(){
        lifecycleScope.launchWhenStarted {
            viewModel.bfLocation.collect{
                if (it != null) {
                    navController?.navigate(R.id.action_requestIngFragment_to_requestMatchingFragment)
                }
            }
        }
    }

    private fun setListener() {
        binding.btnRequestIng.setOnClickListener {
            //navController?.navigate(R.id.action_requestIngFragment_to_requestMatchingFragment)
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }

}