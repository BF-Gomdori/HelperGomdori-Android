package com.bf.helpergomdori.ui.signIn

import androidx.navigation.findNavController
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentSigninGreetingBinding

class SignInGreetingFragment : BaseFragment<FragmentSigninGreetingBinding>(R.layout.fragment_signin_greeting) {
    override fun createView(binding: FragmentSigninGreetingBinding) {
        setupClicks()
    }

    override fun createFragment() {

    }

    private fun setupClicks() {
        binding!!.apply {
            btnNaverLogin.setOnClickListener {
                val navController = it.findNavController()
                navController.navigate(R.id.signInHelpingFragment)
            }
        }
    }
}