package com.bf.helpergomdori.ui.signIn

import androidx.navigation.findNavController
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentSigninSelectHelpingBinding

class SignInSelectHelpingFragment: BaseFragment<FragmentSigninSelectHelpingBinding>(R.layout.fragment_signin_select_helping) {
    override fun createView(binding: FragmentSigninSelectHelpingBinding) {
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