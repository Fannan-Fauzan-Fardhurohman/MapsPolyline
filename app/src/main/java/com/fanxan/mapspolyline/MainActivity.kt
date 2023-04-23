package com.fanxan.mapspolyline

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import com.fanxan.mapspolyline.databinding.ActivityMainBinding
import com.fanxan.mapspolyline.manager.LocationManager
import com.fanxan.mapspolyline.utils.BaseActivitybinding
import com.fanxan.mapspolyline.utils.intentTo
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivitybinding<ActivityMainBinding>() {
    private val locationManager: LocationManager by lazy {
        LocationManager(this)
    }

    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    @AfterPermissionGranted(value = RC_LOCATION)
    override fun oncreateBinding(savedInstanceState: Bundle?) {
//        code
        getPermission {
            with(binding) {
                btnMaps.setOnClickListener {
                    intentTo(MapsActivity::class.java)
                }
                btnUser.setOnClickListener {
                    intentTo(UserActivity::class.java)
                }
                btnPicker.setOnClickListener {
                    intentTo(MapsPickerActivity::class.java)
                }
            }
        }

    }

    private fun getPermission(onResult: () -> Unit) {
        val findLocation = Manifest.permission.ACCESS_FINE_LOCATION
        val coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
        if (EasyPermissions.hasPermissions(this, findLocation, coarseLocation)) {
            Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show()
            onResult.invoke()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Granted For Location",
                RC_LOCATION,
                findLocation,
                coarseLocation
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    companion object {
        private const val RC_LOCATION = 16
    }

}