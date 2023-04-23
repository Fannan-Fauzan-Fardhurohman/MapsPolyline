package com.fanxan.mapspolyline.network

import com.fanxan.mapspolyline.entity.LocationData
import com.fanxan.mapspolyline.network.response.ReserveLocationResponse

object ResponseMapper {
    fun mapReserveLocationResponseToLocation(reserveLocationResponse: ReserveLocationResponse?): LocationData {
        val address = reserveLocationResponse?.data?.address.run {
            LocationData.Address(
                city = this?.city.orEmpty(),
                country = this?.country.orEmpty(),
                district = this?.district.orEmpty()
            )
        }

        val coordinate = reserveLocationResponse?.data?.coordinate.run {
            LocationData.Coordinate(
                latitude = this?.latitude ?: 0.0,
                longitude = this?.longitude ?: 0.0
            )
        }

        val name = reserveLocationResponse?.data?.name.orEmpty()
        return LocationData(
            address = address,
            coordinate = coordinate,
            name = name
        )
    }
}