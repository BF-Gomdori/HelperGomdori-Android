package com.bf.helpergomdori.ui.request

import androidx.activity.viewModels
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.databinding.ActivityRequestBinding
import com.bf.helpergomdori.model.websocket.Location
import com.bf.helpergomdori.utils.MAIN_TO_REQUEST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestActivity: BaseActivity<ActivityRequestBinding>(R.layout.activity_request) {

    private val viewModel: RequestViewModel by viewModels()

    override fun createActivity() {
        val location = intent.getSerializableExtra(MAIN_TO_REQUEST) as Location
        viewModel.setCurrentLocation(location)
        viewModel.postRequestInfo(location)
    }
}