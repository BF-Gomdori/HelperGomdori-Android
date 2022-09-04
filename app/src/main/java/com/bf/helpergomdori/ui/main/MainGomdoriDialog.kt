package com.bf.helpergomdori.ui.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bf.helpergomdori.R
import com.bf.helpergomdori.databinding.DialogMainProfileBinding
import com.bf.helpergomdori.model.local.BfDetailPing
import com.bf.helpergomdori.model.local.Gender
import com.bf.helpergomdori.model.local.GomdoriDetailPing
import com.bf.helpergomdori.model.remote.response.DetailPingResponse
import com.bf.helpergomdori.utils.*
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.system.exitProcess

class MainGomdoriDialog(
    private val ping: GomdoriDetailPing, private val viewModel: MainViewModel
) : DialogFragment() {

    private var _binding: DialogMainProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.dialog_profile)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogMainProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        binding.btnResponse.setOnClickListener {
            dismiss()
            viewModel.sendAcceptedMessage(ping.helpeeDetailPing.helpRequest)
            connectNaverMapScheme()
        }

        binding.btnX.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.apply {
            val detailPing = ping.helpeeDetailPing
            val mGender = when (detailPing.gender) {
                "남" -> Gender.MALE
                "여" -> Gender.FEMALE
                else -> Gender.NONE
            }
            val placeholder = R.drawable.img_helper_gomdori
            requestedTerm = ping.helpeeDetailPing.helpRequest.requestDetail
            type = ping.helpeeDetailPing.type
            name = detailPing.name
            age = detailPing.age
            gender = mGender
            location = detailPing.location
            Glide.with(this@MainGomdoriDialog)
                .load(detailPing.photoLink)
                .placeholder(placeholder)
                .circleCrop()
                .into(imgProfile)
        }
    }

    private fun connectNaverMapScheme() {
        val uri = Uri.parse(NAVER_MAP_SCHEME_BASE_URL)
            .buildUpon()
            .appendQueryParameter(DLAT_PARAM, ping.latitude.toString())
            .appendQueryParameter(DLNG_PARRAM, ping.longitude.toString())
            .appendQueryParameter(DNAME_PARAM, ping.helpeeDetailPing.location)
            .appendQueryParameter(APP_NAME_PARAM, resources.getString(R.string.package_name))
            .build()

        Log.d(MAIN_TAG, "connectNaverMapScheme: $uri")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        requireActivity().startActivity(intent)
        exitProcess(0)
    }
}