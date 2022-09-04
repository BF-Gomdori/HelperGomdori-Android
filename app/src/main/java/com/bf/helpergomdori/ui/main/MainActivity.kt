package com.bf.helpergomdori.ui.main


import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.databinding.ActivityMainBinding
import com.bf.helpergomdori.model.local.HelpType
import com.bf.helpergomdori.model.local.Ping
import com.bf.helpergomdori.ui.mypage.MypageActivity
import com.bf.helpergomdori.ui.request.RequestActivity
import com.bf.helpergomdori.utils.CAMERA_ZOOM_DENSITY
import com.bf.helpergomdori.utils.DensityUtil
import com.bf.helpergomdori.utils.MAIN_TAG
import com.bf.helpergomdori.utils.MAIN_TO_REQUEST
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), OnMapReadyCallback {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap
    lateinit var fusedLocationClient: FusedLocationProviderClient

    private var gomdoriMarkerList: MutableList<Marker> = mutableListOf()
    private var bfMarkerList: MutableList<Marker> = mutableListOf()


    override fun createActivity() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (!isLocationPermitted()) {
            requestLocationPermission()
        }
        initView()
        setListener()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(MAIN_TAG, "onRestart")
        viewModel.startWebSocket()

    }


    private fun initView() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)

        if (PrefsUtil.getHelpType() == HelpType.BF) {
            binding.btnRequest.visibility = View.GONE
        }
    }

    private fun setListener() {
        binding.apply {
            btnMyLocation.setOnClickListener {
                val latitude = viewModel.currentLocation.x
                val longitude = viewModel.currentLocation.y
                if (latitude != 0.0 && longitude != 0.0) {
                    val cameraPosition = CameraPosition(LatLng(latitude, longitude), CAMERA_ZOOM_DENSITY)
                    naverMap.moveCamera(CameraUpdate.toCameraPosition(cameraPosition))
                }
            }

            btnMypage.setOnClickListener {
                val intent = Intent(this@MainActivity, MypageActivity::class.java)
                startActivity(intent)
            }

            btnRequest.setOnClickListener {
                val intent =Intent(this@MainActivity, RequestActivity::class.java)
                intent.putExtra(MAIN_TO_REQUEST, viewModel.currentLocation)
                startActivity(intent)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            lifecycleScope.launchWhenStarted {
                gomdoriList.collect{
                    Log.d(MAIN_TAG, "gomdoriList: ${it}")
                    if (this@MainActivity::naverMap.isInitialized) putGomdoriMarker(it)
                }
            }

            lifecycleScope.launchWhenStarted {
                bfList.collect{
                    Log.d(MAIN_TAG, "bfList: ${it}")
                    if (this@MainActivity::naverMap.isInitialized) putBfMarker(it)
                }
            }

            lifecycleScope.launchWhenStarted {
                disconnectedGomdoriList.collect{
                    if (this@MainActivity::naverMap.isInitialized) {
                        deleteGomdoriMarker(it)
                    } else {
                        onMapReady(naverMap)
                        deleteGomdoriMarker(it)
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                disconnectedBfList.collect{
                    if (this@MainActivity::naverMap.isInitialized) deleteBfMarker(it)
                }
            }

            lifecycleScope.launchWhenCreated {
                selectedGomdori.collect {
                    if (it != null) {
                        Log.d(MAIN_TAG, "selectedGomdori: ${it}")
                        val profileDialog = MainGomdoriDialog(it, viewModel).apply {
                            isCancelable = true
                        }
                        profileDialog.show(supportFragmentManager, "ProfileDialog")
                        setSelectedGomdori(null)
                    }
                }
            }

            lifecycleScope.launchWhenCreated {
                selectedBf.collect {
                    if (it != null) {
                        Log.d(MAIN_TAG, "selectedBf: ${it}")
                        val profileDialog = MainBfDialog(it).apply {
                            isCancelable = true
                        }
                        profileDialog.show(supportFragmentManager, "ProfileDialog")
                        setSelectedBf(null)
                    }
                }
            }
            
            lifecycleScope.launchWhenCreated { 
                bfCnt.collect {
                    Log.d(MAIN_TAG, "observeBfCnt: ${it}")
                    binding.bfCnt = it
                }
            }

            lifecycleScope.launchWhenCreated {
                gomdoriCnt.collect{
                    Log.d(MAIN_TAG, "observeGomdoriCnt: ${it}")
                    binding.gomdoriCnt = it
                }
            }
        }
    }

    private fun putGomdoriMarker(gomdoriList: List<Ping>) {
        gomdoriList.forEach { ping ->
            val gomdori = Marker().apply {
                position = LatLng(ping.location.x, ping.location.y)
                icon = OverlayImage.fromResource(R.drawable.ic_marker_gomdori)
                DensityUtil.setResouces(resources)
                width = DensityUtil.dp2px(80f).toInt()
                height = DensityUtil.dp2px(80f).toInt()
                map = naverMap
            }
            gomdoriMarkerList.add(gomdori)
            gomdori.setOnClickListener {
                viewModel.getGomdoriPing(ping.jwt, ping.location.x, ping.location.y)
                true
            }
        }
    }

    private fun putBfMarker(bfList: List<Ping>) {
        bfList.forEach { ping ->
            val bf = Marker().apply {
                position = LatLng(ping.location.x, ping.location.y)
                icon = OverlayImage.fromResource(R.drawable.ic_marker_bf)
                DensityUtil.setResouces(resources)
                width = DensityUtil.dp2px(65f).toInt()
                height = DensityUtil.dp2px(65f).toInt()
                map = naverMap
            }
            bfMarkerList.add(bf)
            bf.setOnClickListener {
                Log.d(MAIN_TAG, "click bf : ${ping.jwt}")
                viewModel.getBfPing(ping.jwt, ping.location.x, ping.location.y)
                true
            }
        }
    }

    private fun deleteGomdoriMarker(gomdoriList: List<Ping>){
        gomdoriList.forEach { ping ->
            Log.d(MAIN_TAG, "deleteGomdoriMarker: ${ping}")
            val markersToRemove = gomdoriMarkerList.filter {
                it.position.latitude == ping.location.x && it.position.longitude == ping.location.y
            }
            markersToRemove.forEach {
                it.map = null
            }
        }
    }

    private fun deleteBfMarker(bfList: List<Ping>) {
        bfList.forEach { ping ->
            val markersToRemove = bfMarkerList.filter {
                it.position.latitude == ping.location.x && it.position.longitude == ping.location.y
            }
            markersToRemove.forEach {
                it.map = null
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(nmap: NaverMap) {
        naverMap = nmap
        observeViewModel()

        if (getFusedLocationSource() != null) {
            naverMap.locationSource = getFusedLocationSource() // 내장 위치 추적 기능
            fusedLocationClient.lastLocation.addOnSuccessListener { currentLocation ->
                Log.d(MAIN_TAG, "last location : $currentLocation")
                viewModel.setCurrentLocation(currentLocation.latitude, currentLocation.longitude)
                viewModel.startWebSocket()

                naverMap.apply {
                    mapType = NaverMap.MapType.Basic
                    setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true)
                    val cameraPosition = CameraPosition(
                        LatLng(currentLocation.latitude, currentLocation.longitude),
                        CAMERA_ZOOM_DENSITY
                    )
                    naverMap.moveCamera(CameraUpdate.toCameraPosition(cameraPosition))
                    locationOverlay.run {
                        isVisible = true
                        position = LatLng(currentLocation.latitude, currentLocation.longitude)
                    }
                    locationTrackingMode = LocationTrackingMode.Follow
                    isIndoorEnabled = true
                }
            }
        }

    }




}