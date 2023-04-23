package com.fanxan.mapspolyline.entity

import com.google.gson.annotations.SerializedName

data class LocationData(
    val address: Address,
    val coordinate: Coordinate,
    val name: String
) {
    data class Address(
        val country: String="",
        val city: String = "",
        val district: String=""
    )

    data class Coordinate(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0
    )

}