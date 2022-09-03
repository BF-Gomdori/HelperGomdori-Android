package com.bf.helpergomdori.ui.request

import androidx.fragment.app.activityViewModels
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseFragment
import com.bf.helpergomdori.databinding.FragmentRequestLocationBinding
import com.bf.helpergomdori.utils.CAMERA_ZOOM_DENSITY_MORE
import com.bf.helpergomdori.utils.DensityUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class RequestLocationFragment :
    BaseFragment<FragmentRequestLocationBinding>(R.layout.fragment_request_location),
    OnMapReadyCallback 
{
    private val viewModel: RequestViewModel by activityViewModels()

    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap
    
    override fun createView(binding: FragmentRequestLocationBinding) {
    }

    override fun createFragment() {
        initView()
        setListener()
    }

    private fun setListener() {
        binding.apply {
            btnRequest.setOnClickListener {
                viewModel.setDetailAddress(etAddress.text.toString())
                navController?.navigate(R.id.action_requestLocationFragment_to_requestIngFragment)
            }
        }
    }

    private fun initView() {
        val fm = requireActivity().supportFragmentManager
        mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment? ?: MapFragment.newInstance().also{
            fm.beginTransaction().add(R.id.map_fragment, it).commit()
        }
        mapFragment.getMapAsync(this)

        binding.address = viewModel.requestInfo.value?.location
    }
    
    override fun onMapReady(nMap: NaverMap) {
        naverMap = nMap

        naverMap.apply {
            mapType = NaverMap.MapType.Basic
            setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true)
            val location = viewModel.currentLocation!!
            val cameraPosition = CameraPosition(
                LatLng(location.x, location.y),
                CAMERA_ZOOM_DENSITY_MORE
            )
            naverMap.moveCamera(CameraUpdate.toCameraPosition(cameraPosition))

            Marker().apply{
                position = LatLng(location.x, location.y)
                icon = OverlayImage.fromResource(R.drawable.ic_marker_gomdori)
                DensityUtil.setResouces(resources)
                width = DensityUtil.dp2px(65f).toInt()
                height = DensityUtil.dp2px(65f).toInt()
                map = naverMap
            }
        }
    }
}