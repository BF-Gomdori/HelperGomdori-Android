package com.bf.helpergomdori.ui.signIn

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentSigninAdditionalInfoBinding

class SignInAdditionalInfoFragment : BaseFragment<FragmentSigninAdditionalInfoBinding>(R.layout.fragment_signin_additional_info){

    private lateinit var navController: NavController

    override fun createView(binding: FragmentSigninAdditionalInfoBinding) {
        navController = this.findNavController()
        setupClicks()
    }

    override fun createFragment() {
    }

    private fun setupClicks(){
        binding.apply {
            btnOk.setOnClickListener {
                navController.navigate(R.id.signInHelpingFragment)
            }
        }
    }
}