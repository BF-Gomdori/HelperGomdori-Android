package com.bf.helpergomdori.ui.request

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestWriteBinding
import com.bf.helpergomdori.utils.REQUEST_TAG
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

class RequestWriteFragment :
    BaseFragment<FragmentRequestWriteBinding>(R.layout.fragment_request_write) {

    private val viewModel: RequestViewModel by activityViewModels()

    override fun createView(binding: FragmentRequestWriteBinding) {

    }

    override fun createFragment() {
        setListener()
        observeViewModel()
    }


    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.requestInfo.collectLatest {
                Log.d(REQUEST_TAG, "observe RequestInfo: ${it}")
                if (it != null) {
                    Glide.with(this@RequestWriteFragment)
                        .load(it.image)
                        .placeholder(R.drawable.img_helper_gomdori)
                        .circleCrop()
                        .into(binding.imgUser)
                    binding.name = it.name
                }
            }
        }


    }

    private fun setListener() {
        binding.apply {
            btnOk.setOnClickListener {
                viewModel.setSpecificRequest(etSpecificRequest.text.toString())
                viewModel.setNeedSituations(getCheckedSituation())
                navController?.navigate(R.id.action_requestWriteFragment_to_requestLocationFragment)
            }
        }
    }

    private fun getCheckedSituation(): List<String> {
        val checkedSituation = mutableListOf<String>()
        binding.apply {
            if (checkboxStairsLift.isChecked) {
                checkedSituation.add(checkboxStairsLift.text.toString())
            }
            if (checkboxPublicTransport.isChecked) {
                checkedSituation.add(checkboxPublicTransport.text.toString())
            }
            if (checkboxEtc.isChecked) {
                checkedSituation.add(checkboxEtc.text.toString())
            }
            if (checkboxStairsLift.isChecked) {
                checkedSituation.add(checkboxStairsLift.text.toString())
            }
            if (checkboxCommunicationProblem.isChecked) {
                checkedSituation.add(checkboxCommunicationProblem.text.toString())
            }
        }
        return checkedSituation.toList()
    }

}