package com.bf.helpergomdori.ui.signIn

import android.content.Intent
import android.util.Log
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentSigninHelpeeBinding
import com.bf.helpergomdori.ui.main.MainActivity
import com.bf.helpergomdori.utils.SIGNIN_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInHelpeeFragment: BaseFragment<FragmentSigninHelpeeBinding>(R.layout.fragment_signin_helpee) {

    private val viewModel: SignInViewModel by hiltNavGraphViewModels(R.id.nav_signin_graph)

    override fun createView(binding: FragmentSigninHelpeeBinding) {
        setListener()
    }

    override fun createFragment() {

    }

    private fun setListener() {
        binding.apply {
            btnOk.setOnClickListener {
                val checkedDisableTypeList = getCheckedType()
                viewModel.postHelpee(checkedDisableTypeList)
                val intent = Intent(requireActivity(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                requireActivity().finish()
            }


        }

    }

    private fun getCheckedType(): MutableList<String> {
        val checkedType = mutableListOf<String>()

        binding.apply {
            if (checkboxLanguage.isChecked) {
                checkedType.add(checkboxLanguage.text.toString())
            }
            if (checkboxBody.isChecked) {
                checkedType.add(checkboxBody.text.toString())
            }
            if (checkboxEye.isChecked) {
                checkedType.add(checkboxEye.text.toString())
            }
            if (checkboxEar.isChecked) {
                checkedType.add(checkboxEar.text.toString())
            }
            if (checkboxEtc.isChecked) {
                checkedType.add(checkboxEtc.text.toString())
            }
            if (checkboxDevelopmental.isChecked) {
                checkedType.add(checkboxDevelopmental.text.toString())
            }
            if (checkboxMental.isChecked) {
                checkedType.add(checkboxMental.text.toString())
            }
        }
        Log.d(SIGNIN_TAG, "getCheckedType: ${checkedType}")

        return checkedType
    }

}