package com.bf.helpergomdori.ui.signIn

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentSigninGreetingBinding
import com.bf.helpergomdori.utils.SIGNIN_TAG
import com.kakao.sdk.user.UserApiClient

class SignInGreetingFragment :
    BaseFragment<FragmentSigninGreetingBinding>(R.layout.fragment_signin_greeting) {

    private lateinit var navController: NavController

    override fun createView(binding: FragmentSigninGreetingBinding) {
        navController = this@SignInGreetingFragment.findNavController()
        setupClicks()
    }

    override fun createFragment() {

    }

    private fun setupClicks() {
        binding!!.apply {
            btnKakaoLogin.setOnClickListener {
                kakaoLogin()
            }

            btnNaverLogin.setOnClickListener {
                navController.navigate(R.id.signInHelpingFragment)
            }
        }
    }

    private fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.e(SIGNIN_TAG, "카카오 로그인 실패 : $error")
                } else if (token != null) {
                    Log.i(SIGNIN_TAG, "카카오 로그인 성공 ${token.accessToken}")
                    //todo 추후에 accessToken 백엔드에 보낸 후에 jwt 받아옴
                    navController.navigate(R.id.signInHelpingFragment)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
                if (error != null) {
                    Log.e(SIGNIN_TAG, "카카오 로그인 실패", error)
                } else if (token != null) {
                    Log.i(SIGNIN_TAG, "카카오 로그인 성공 ${token.accessToken}")
                    //todo 추후에 accessToken 백엔드에 보낸 후에 jwt 받아옴
                    navController.navigate(R.id.signInHelpingFragment)
                }
            }
        }

    }
}