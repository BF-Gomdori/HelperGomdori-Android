package com.bf.helpergomdori.ui.signIn

import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentSigninAdditionalInfoBinding
import com.bf.helpergomdori.utils.SIGNIN_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInAdditionalInfoFragment : BaseFragment<FragmentSigninAdditionalInfoBinding>(R.layout.fragment_signin_additional_info){

    private val viewModel: SignInViewModel by hiltNavGraphViewModels(R.id.nav_signin_graph)

    override fun createView(binding: FragmentSigninAdditionalInfoBinding) {
    }

    override fun createFragment() {
        setListener()
    }


    private fun setListener(){
        binding.apply {
            btnOk.setOnClickListener {
                viewModel.postUser()
                navController?.navigate(R.id.action_signInAdditionalInfoFragment_to_signInHelpingFragment)
            }

            binding.etPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

            etPhone.setOnEditorActionListener { textView, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    Log.d(SIGNIN_TAG, "etPhone: ${textView.text}")
                    viewModel.setUserPhone(textView.text.toString())
                    true
                }
                false
            }

            etName.setOnEditorActionListener { textView, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(SIGNIN_TAG, "etName: ${textView.text}")
                    viewModel.setUserName(textView.text.toString())
                    true
                }
                false
            }
        }

    }
}