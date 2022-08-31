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
import com.bf.helpergomdori.model.Profile
import com.bf.helpergomdori.model.ProfileGomdori
import com.bf.helpergomdori.utils.*
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.net.URLDecoder
import java.net.URLEncoder

class MainProfileDialog(private val profile: Profile) : DialogFragment() {

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
            var placeholder = R.drawable.img_bf_footprint
            if (profile is ProfileGomdori) {
                requestedTerm = profile.requested_term
                placeholder = R.drawable.img_helper_gomdori
            }
            type = profile.type
            name = profile.name
            age = profile.age
            gender = profile.gender
            location = profile.location
            Glide.with(this@MainProfileDialog)
                .load(profile.img)
                .placeholder(placeholder)
                .into(imgProfile)
        }
    }

    private fun connectNaverMapScheme() {
        val slat = 37.494437500000004
        val slng = 126.9599375
        val sname = "숭실대 정보과학관"
        val dlat = 37.496187500000005
        val dlng = 126.9585625
        val dname = "숭실대 도서관"
        val uri = Uri.parse(NAVER_MAP_SCHEME_BASE_URL)
            .buildUpon()
            .appendQueryParameter(SLAT_PARAM, slat.toString())
            .appendQueryParameter(SLNG_PRARM, slng.toString())
            .appendQueryParameter(SNAME_PARAM, sname)
            .appendQueryParameter(DLAT_PARAM, dlat.toString())
            .appendQueryParameter(DLNG_PARRAM, dlng.toString())
            .appendQueryParameter(DNAME_PARAM, dname)
            .appendQueryParameter(APP_NAME_PARAM,resources.getString(R.string.package_name))
            .build()

        Log.d(MAIN_TAG, "connectNaverMapScheme: $uri")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        requireActivity().startActivity(intent)
    }
}