package com.bf.helpergomdori.ui.signIn

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.databinding.ActivitySigninBinding
import com.bf.helpergomdori.model.local.HelpType
import com.bf.helpergomdori.ui.main.MainActivity
import com.bf.helpergomdori.utils.SIGNIN_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySigninBinding>(R.layout.activity_signin) {
    private val viewModel: SignInViewModel by viewModels()

    override fun createActivity() {

        if (!isLocationPermitted()) {
            requestLocationPermission()
        }

        if (viewModel.isExistUser()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}