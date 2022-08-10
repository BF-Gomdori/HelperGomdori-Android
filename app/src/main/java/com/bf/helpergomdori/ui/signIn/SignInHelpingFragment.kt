package com.bf.helpergomdori.ui.signIn

import androidx.navigation.findNavController
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentSigninHelpingBinding

class SignInHelpingFragment: BaseFragment<FragmentSigninHelpingBinding>(R.layout.fragment_signin_helping) {
    override fun createView(binding: FragmentSigninHelpingBinding) {
        binding.apply {
            btnGivingHelp.setOnClickListener {
                val navController = it.findNavController()
                navController.navigate(R.id.signInHelpeeFragment)
            }
        }
    }

    override fun createFragment() {

    }
}