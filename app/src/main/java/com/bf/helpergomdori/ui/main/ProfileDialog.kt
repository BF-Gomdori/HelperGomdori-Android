package com.bf.helpergomdori.ui.main

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bf.helpergomdori.R
import com.bf.helpergomdori.databinding.DialogProfileBinding
import com.bf.helpergomdori.model.Profile
import com.bf.helpergomdori.model.ProfileGomdori
import com.bf.helpergomdori.utils.*
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.net.URLEncoder

class ProfileDialog(val profile: Profile) : BottomSheetDialogFragment() {

    private var _binding: DialogProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.dialog_profile)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogProfileBinding.inflate(inflater, container, false)
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
            Glide.with(this@ProfileDialog)
                .load(profile.img)
                .placeholder(placeholder)
                .into(imgProfile)
        }
    }

    private fun connectNaverMapScheme() {
        val slat = 37.494437500000004
        val slng = 126.9599375
        val sname = URLEncoder.encode("숭실대 정보과학관", "UTF-8")
        val dlat = 37.496187500000005
        val dlng = 126.9585625
        val dname = URLEncoder.encode("숭실대 도서관", "EUC-KR")
        val parameter = "slat=$slat&slng=$slng&dlat=$dlat&dlng=$dlng"
        resources.getString(R.string.package_name)
        //val uri = NAVER_MAP_SCHEME_URL + parameter + "&appname=${resources.getString(R.string.package_name)}"
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

        val urii = "nmap://route/walk?slat=37.4640070&slng=126.9522394&sname=%EC%84%9C%EC%9A%B8%EB%8C%80%ED%95%99%EA%B5%90&dlat=37.4764356&dlng=126.9618302&dname=%EB%8F%99%EC%9B%90%EB%82%99%EC%84%B1%EB%8C%80%EC%95%84%ED%8C%8C%ED%8A%B8&appname=com.example.myapp"
        Log.d(MAIN_TAG, "connectNaverMapScheme: $uri")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        requireActivity().startActivity(intent)
    }
}