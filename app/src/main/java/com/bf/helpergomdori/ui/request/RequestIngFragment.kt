package com.bf.helpergomdori.ui.request

import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestIngBinding
import com.bf.helpergomdori.ui.main.MainActivity
import com.bf.helpergomdori.utils.REQUEST_TAG
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
                Log.d(REQUEST_TAG, "observe bfLocation: ${it}")
                if (it != null) {
                    if (navController == null){
                        this@RequestIngFragment.findNavControllerSafely()?.navigate(R.id.action_requestIngFragment_to_requestMatchingFragment)
                    }else {
                        navController?.navigate(R.id.action_requestIngFragment_to_requestMatchingFragment)
                    }
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