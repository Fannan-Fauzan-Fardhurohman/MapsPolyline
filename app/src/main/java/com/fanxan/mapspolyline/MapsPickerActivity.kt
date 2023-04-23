package com.fanxan.mapspolyline

import android.os.Bundle
import com.fanxan.mapspolyline.databinding.ActivityPickerMapsBinding
import com.fanxan.mapspolyline.entity.LocationData
import com.fanxan.mapspolyline.manager.LocationManager
import com.fanxan.mapspolyline.network.ResponseMapper
import com.fanxan.mapspolyline.network.WebServices
import com.fanxan.mapspolyline.network.response.ReserveLocationResponse
import com.fanxan.mapspolyline.utils.BaseActivitybinding
import com.fanxan.mapspolyline.utils.throttleLatest
import com.fanxan.mapspolyline.utils.toLatLng
import com.fanxan.mapspolyline.utils.toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import java.time.Duration
import java.util.concurrent.TimeUnit

class MapsPickerActivity : BaseActivitybinding<ActivityPickerMapsBinding>() {
    private var isPanelShow = false

    private val locationManager by lazy {
        LocationManager(this)
    }

    private val webServices: WebServices by lazy {
        WebServices.create()
    }

    override fun inflateBinding(): ActivityPickerMapsBinding {
        return ActivityPickerMapsBinding.inflate(layoutInflater)
    }

    override fun oncreateBinding(savedInstanceState: Bundle?) {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(onMapReady())
        hidePanel()
    }

    private fun onMapReady(): OnMapReadyCallback = OnMapReadyCallback { map ->
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isZoomControlsEnabled = true
        locationManager.getLastLocation { location ->
            val latLng = location.toLatLng()
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, 12f)
            )
        }

        map.setOnCameraMoveListener {
            println("Move......")
            hidePanel()
        }


        map.setOnCameraIdleListener {
            println("idle..... -> ${map.cameraPosition.target}")
            runBlocking {
                val coordinate = map.cameraPosition.target
                println("Loading......")
                map.uiSettings.isScrollGesturesEnabled = false
                getReverseLocationFlow(coordinate)
                    .debounce(1000L).collect {
                        println("result -> ${it.name}")
                        onLocationResult(it)
                        map.uiSettings.isScrollGesturesEnabled = true
                    }
            }
        }

    }

    private fun onLocationResult(locationData: LocationData) {
        showPanel()
        with(binding) {
            tvLocationResult.text = "${locationData.name}\n${locationData.address.country}"
        }
    }

    private fun hidePanel() {
        isPanelShow = false
        with(binding) {
            panelLocation.animate()
                .translationY(400f)
                .start()
        }
    }

    private fun showPanel() {
        isPanelShow = true
        with(binding) {
            panelLocation.animate()
                .translationY(0f)
                .start()
        }
    }

    private fun reverseLocation(
        latLng: LatLng,
        onLoading: () -> Unit,
        onResultLocation: (LocationData) -> Unit
    ) {
        runBlocking {
            val coordinateString = "${latLng.latitude},${latLng.longitude}"
            onLoading.invoke()
//            val reserveLocation: (Response<ReserveLocationResponse>) -> Unit =
//                throttleLatest<Response<ReserveLocationResponse>> {
//                    coroutineScope = this,
//                    destinationFunction = webServices::reserveCoordinate
//                }

            val getter = webServices.reserveCoordinate(coordinateString)
            val response = webServices.reserveCoordinate(coordinateString)
            if (response.isSuccessful) {
                val data = ResponseMapper.mapReserveLocationResponseToLocation(response.body())
                onResultLocation.invoke(data)
            } else {
                toast("error ; ${response.message()}")
            }
        }
    }


    private fun getReverseLocationFlow(
        latLng: LatLng
    ): Flow<LocationData> {
        return flow {
            val coordinateString = "${latLng.latitude},${latLng.longitude}"
            val response = webServices.reserveCoordinate(coordinateString)
            if (response.isSuccessful) {
                val data = ResponseMapper.mapReserveLocationResponseToLocation(response.body())
                emit(data)
            } else {
                toast("error ; ${response.message()}")
            }
        }
    }

    private fun togglePanel() {
        if (isPanelShow) {
            hidePanel()
        } else {
            showPanel()
        }
    }
}