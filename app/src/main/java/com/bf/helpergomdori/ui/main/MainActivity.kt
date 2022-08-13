package com.bf.helpergomdori.ui.main


import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.databinding.ActivityMainBinding
import com.naver.maps.map.MapFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main){

    private lateinit var mapFragment: MapFragment

    override fun createActivity() {
        binding.apply {

        }

        // 지도 객체 생성
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment

    }

}