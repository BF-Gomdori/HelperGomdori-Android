package com.bf.helpergomdori.ui.signIn

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentSigninSelectHelpingBinding
import com.bf.helpergomdori.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInSelectHelpingFragment :
    BaseFragment<FragmentSigninSelectHelpingBinding>(R.layout.fragment_signin_select_helping) {

    private val viewModel: SignInViewModel by hiltNavGraphViewModels(R.id.nav_signin_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()
        setCurrentNavController(this.findNavControllerSafely())
    }

    override fun createView(binding: FragmentSigninSelectHelpingBinding) {
        setListener()
    }

    override fun createFragment() {

    }

    private fun setListener() {
        binding.apply {
            btnGivingHelp.setOnClickListener {
                viewModel.postHelper()
                val intent = Intent(requireActivity(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                requireActivity().finish()
            }

            btnGettingHelp.setOnClickListener {
                val navController = it.findNavController()
                navController.navigate(R.id.action_signInHelpingFragment_to_signInHelpeeFragment)
            }
        }

    }

}