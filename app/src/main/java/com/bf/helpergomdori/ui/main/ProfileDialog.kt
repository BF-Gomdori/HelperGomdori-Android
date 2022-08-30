package com.bf.helpergomdori.ui.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bf.helpergomdori.R
import com.bf.helpergomdori.databinding.DialogProfileBinding
import com.bf.helpergomdori.model.Profile
import com.bf.helpergomdori.model.ProfileGomdori
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

}