package com.bf.helpergomdori.ui.signIn

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.databinding.ActivitySigninBinding
import com.bf.helpergomdori.model.local.HelpType
import com.bf.helpergomdori.service.BfService
import com.bf.helpergomdori.ui.main.MainActivity
import com.bf.helpergomdori.utils.PUSH_TAG
import com.bf.helpergomdori.utils.SIGNIN_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySigninBinding>(R.layout.activity_signin) {
    private val viewModel: SignInViewModel by viewModels()

    private val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            Log.d(PUSH_TAG, "onServiceConnected")
            val binder = service as BfService.LocalBinder
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(PUSH_TAG, "onServiceDisconnected")
        }
    }

    override fun createActivity() {

        if (!isLocationPermitted()) {
            requestLocationPermission()
        }

        if (viewModel.isExistUser()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        applicationContext.bindService(
            Intent(this, BfService::class.java),
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

}