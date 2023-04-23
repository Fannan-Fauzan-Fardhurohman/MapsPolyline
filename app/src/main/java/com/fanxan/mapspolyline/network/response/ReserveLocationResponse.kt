package com.fanxan.mapspolyline.network.response

import com.google.gson.annotations.SerializedName

data class ReserveLocationResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Address(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("district")
	val district: String? = null
)

data class Data(

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("coordinate")
	val coordinate: Coordinate? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class Coordinate(

	@field:SerializedName("latitude")
	val latitude: Double? = 0.0,

	@field:SerializedName("longitude")
	val longitude: Double? = 0.0
)
