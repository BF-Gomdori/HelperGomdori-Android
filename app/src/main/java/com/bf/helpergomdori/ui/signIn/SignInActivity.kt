package com.bf.helpergomdori.ui.signIn

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bf.helpergomdori.R
import com.bf.helpergomdori.UserInfo
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.databinding.ActivitySigninBinding
import com.bf.helpergomdori.ui.main.MainActivity
import com.bf.helpergomdori.utils.SIGNIN_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySigninBinding>(R.layout.activity_signin) {
    private val viewModel: SignInViewModel by viewModels()

    override fun createActivity() {
        //viewModel.getUserInfo()
        observeViewModel()
    }

    private fun observeViewModel() {
        // todo splash에서 검사
//        lifecycleScope.launchWhenCreated {
//            viewModel.currentUserInfo.collect {
//                Log.d(SIGNIN_TAG, "currentUserInfo: ${it?.name} ${it?.jwt}")
//                if (it != null && it.jwt != "") {
//                    val intent = Intent(this@SignInActivity, MainActivity::class.java).apply {
//                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
//                    }
//                    startActivity(intent)
//                    this@SignInActivity.finish()
//                }
//            }
//        }

    }
}