package com.bf.helpergomdori.ui.request

import androidx.fragment.app.activityViewModels
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestMatchingBinding
import com.bf.helpergomdori.utils.CAMERA_ZOOM_DENSITY_MORE
import com.bf.helpergomdori.utils.DensityUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class RequestMatchingFragment :
    BaseFragment<FragmentRequestMatchingBinding>(R.layout.fragment_request_matching),
    OnMapReadyCallback {

    private val viewModel: RequestViewModel by activityViewModels()
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap

    override fun createView(binding: FragmentRequestMatchingBinding) {

    }

    override fun createFragment() {
        initView()
        setListener()
    }

    private fun initView() {
        mapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                requireActivity().supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun setListener(){
        binding.btnWeMet.setOnClickListener {
            navController?.navigate(R.id.action_requestMatchingFragment_to_requestFinishedFragment)
        }
    }

    override fun onMapReady(nmap: NaverMap) {
        naverMap = nmap
        naverMap.apply {
            mapType = NaverMap.MapType.Basic
            setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true)
            val location = viewModel.currentLocation!!
            val cameraPosition = CameraPosition(
                LatLng(location.x, location.y),
                CAMERA_ZOOM_DENSITY_MORE
            )
            naverMap.moveCamera(CameraUpdate.toCameraPosition(cameraPosition))
            putGomdoriMarker()
            putBfMarker()
        }
    }

    private fun putGomdoriMarker() {
        Marker().apply {
            val location = viewModel.currentLocation!!
            position = LatLng(location.x, location.y)
            icon = OverlayImage.fromResource(R.drawable.ic_marker_gomdori)
            DensityUtil.setResouces(resources)
            width = DensityUtil.dp2px(65f).toInt()
            height = DensityUtil.dp2px(65f).toInt()
            map = naverMap
        }
    }

    private fun putBfMarker(){
        Marker().apply {
            val location = viewModel.bfLocation.value!!
            position = LatLng(location.x, location.y)
            icon = OverlayImage.fromResource(R.drawable.ic_marker_bf)
            DensityUtil.setResouces(resources)
            width = DensityUtil.dp2px(65f).toInt()
            height = DensityUtil.dp2px(65f).toInt()
            map = naverMap
        }
    }

}