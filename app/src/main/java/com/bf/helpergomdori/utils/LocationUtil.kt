package com.bf.helpergomdori.utils

import com.google.android.gms.location.FusedLocationProviderClient

class LocationUtil {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    fun setFusedLocationProviderClient(locationProviderClient: FusedLocationProviderClient) {
        fusedLocationProviderClient = locationProviderClient
    }

}