package com.fanxan.mapspolyline.network

import com.fanxan.mapspolyline.network.response.ReserveLocationResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET(EndPoint.RESERVE)
    suspend fun reserveCoordinate(
        @Query("coordinate") coordinate: String
    ): Response<ReserveLocationResponse>

    companion object {
        private const val BASE_URL = "https://ee18-125-164-18-252.ngrok-free.app"

        fun create(): WebServices {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebServices::class.java)
        }
    }

    object EndPoint {
        const val RESERVE = "/api/location/reserve"
    }

}