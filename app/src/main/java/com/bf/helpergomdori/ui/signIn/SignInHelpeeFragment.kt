package com.bf.helpergomdori.ui.signIn

import android.content.Intent
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentSigninHelpeeBinding
import com.bf.helpergomdori.ui.main.MainActivity

class SignInHelpeeFragment: BaseFragment<FragmentSigninHelpeeBinding>(R.layout.fragment_signin_helpee) {
    override fun createView(binding: FragmentSigninHelpeeBinding) {
        setUpClicks()
    }

    override fun createFragment() {

    }

    private fun setUpClicks() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            requireActivity().finish()
        }
    }
}