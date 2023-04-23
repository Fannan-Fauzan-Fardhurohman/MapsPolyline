package com.fanxan.mapspolyline

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.fanxan.mapspolyline.databinding.ActivityMapsBinding
import com.fanxan.mapspolyline.manager.LocationManager
import com.fanxan.mapspolyline.utils.moveSmootly
import com.fanxan.mapspolyline.utils.toLatLng
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val RC_LOCATION = 16
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding



    private val locationManager: LocationManager by lazy {
        LocationManager(this)
    }

    private var marker: Marker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true

        val routes = Sources.getResultRoutes()
        val coordinates = routes.data?.route.orEmpty()
            .map {
                LatLng(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
            }

        val point1 = coordinates.firstOrNull()
        point1?.let {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point1, 14f))
        }


        val polyline = PolylineOptions().addAll(coordinates)
            .width(18f)
            .color(Color.RED)
        mMap.addPolyline(polyline)

        getLocationWithPermission()

        binding.tvResultCoordinate.setOnClickListener {
//            Intent(this, UserActivity::class.java).also {
//                startActivity(it)
//            }

            val lastLocation = locationManager.getLastLocation {

                Toast.makeText(this, it.toLatLng().toString(), Toast.LENGTH_SHORT).show()
            }

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

    @AfterPermissionGranted(value = RC_LOCATION)
    private fun getLocationWithPermission() {
        val findLocation = Manifest.permission.ACCESS_FINE_LOCATION
        val coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
        if (EasyPermissions.hasPermissions(this, findLocation, coarseLocation)) {
//            Get Lcoation
            getLocation()
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

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        MainScope().launch {
            locationManager.getLocationFlow().collect(onLocationResult())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onLocationResult() = FlowCollector<Location> { location ->
        binding.tvResultCoordinate.text = "${location.latitude},${location.longitude}"
        println("---------LOCATION UPDATE---------->${location.latitude},${location.longitude}")

        val newLatLng = LatLng(location.latitude, location.longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(newLatLng))

        if (marker == null) {
            val markerOption = MarkerOptions().position(newLatLng)
            marker = mMap.addMarker(markerOption)
        }
        marker?.moveSmootly(newLatLng)


    }

//    val polylineShape =
//        "BGzs4hN-xtgtG0IiGof0UgU3csOzUkI3NwWvlBwHvM4DvCAsEvHkN7V4mB3IkNjN0U_TgevHoL7L8QnLoQjX4hBrEoGrEoB_J4NzUsdnBoBnawlBjDgFnB8B3D0FjIoL_JgPjN4SvC4DjI0KjDgFrEoG3IwMvCkDzKoQvCkD_J4N3NsTzKoQjNwR_J4NzKgPnLoQjNkSrJsO3IwMzPoV3X8f_JkNjN4S_EoGvHsJ_EkI_JkNvM8QjS0Z7L0PrJgP3IsO3IkNvHwH7GsE7VkIzF8B7LwC3NwCnGoBjDU3DUzK8B7agFrYsEnL8BnnC0U3mBoL7QsEzKkD3NsEnG8BvMkDrOwCrJU_OoBzKoBvb8B_OoB3XoB_JoB3IoB3IwC7GwCnLgFvHgFjIoG3NsOrToQrJ4IjD4Dnf4c3I4InBoBnBoBnLoLjNkN3NkNnGoG3D4DzF0F_EsEjNwMrEsEnL8L7LsO7G4IjI8LvH8L_JoQjNsY_E0KvCgF3IwRzFoLvMgZjD0FnL0UvH0P_EoL7B8GAwMwC0PwCoQwCkIgFgP8Vo2C8GkckIof0FgZkDgP8BsJAwH7BkInGsOjDoGrJsTvCgF3DkI_EgK7LgZ3Xw0B3IwRvR0jB3SwlB3SwlBnfk_BzF0K3I8QjD8G3IsTnGsOrJ8VjIoQ3I0U3XkwBrEsJrEgKzFgPjDsJnG8VjDgK_J8f7BwH_Jof3X8nCvC0KjDsOT0FnB4S7B0mCnBgUnBwMTwvBoBkNsEgUwCoQoBkNT8LT0KjDkN7BoG7BgF7BsE7ag3B_EoLrEgPnBgKUkNwM08B0FokBkD8VgF84BwCgPUoQ7BoLzFkS3NgoBrTk1B7QssB_EsTA4I7GgUrJwWzK4cjD4I_E0F7Lkc_YkkC_JoVrO8a_J4SrOoarJoQjDgF7LkSrT0evMgUvR0eT0FrEwH7GwMjc4rB_EwH3N8VvqBgmCvC4D3D8B7LwR3I0P_TwgBrEwHjNwW3NkXrEkI7B4DTgFnGgKjI4NvCgFvRsdvH8LzFkIvCsE3D0FjI4NrEkIjX8kB7Qwb7LkS7GgK3N0UjDkDvCwC_iBkXrJwC3SsJrEwC_EkD_YsOvHgFjD8B7aoQnaoQzKoGnawRjSgKjNwHvHsE_OkIjI0FvM4IzF4DjN4IvHsErJ0FzUkNvM8GzZ4NjhB0UzFoG7Q0KjI0F_EsEzPsJ_iBwWzFkDvWoL_Y0PrO4IzoBsYrEwCrJ0F3NkIrYoQ_J0FjNkIjD8BvCoBrEwCvHwCrEkDvC8BrEwCzZ0P_lC4rB_JgFnpB8ajrBoa7QsJjIsE_O4I7GsE3IgFrEwC_YgPrEoB_TwM7zB0e3N4IrJ0FjIgF7QgKvM8GrEwCvR0KrOsJjD8B_iBwWzyBofjIsEvW4NjS0K3DgFrd8QvHsEzF4DzUkNjhBgU7kBwWnuBkczK8G3hBsTrdkSnG4DvMwH7V4NnG4DvR0KjS0K3X4NzFkDvRoLnG4DrJ0FnG4D_qCssB3hBsTrJ0F3SoL3NkIrJ0FvM4InVkNnVwMjN4I_JoGvHsEvR0KnuBwb_EkDzF4DrE8BjDwCnGwHvH0P_JsOjIkSvb0mCvR8uBvC8GvCoGnVo4BjDsJrJwb_EwMnB4D7Vg3BnBkD3DkInGsOzF4N3DwHnBkDjIoQjD8GzKgZjN03BzFsdnB0F_EoVjDgU7GwbvM46BnBsEvCgKrE8VnG4cnB0FT4DrEgPjDwM7B4I_EgU_EsTrE8LzFwWrJwWnG4S3DgPvCoLvCsJjDgK7LkhBvHkX_EsO7BgFrE0KjDsJjDgKjD0FjDkN7B8GnB8GrEsiB7B4mBT0K7BgZvCkXT8G7BoQ3DwgBvHozBjD8ajDgjBAkSUwgBAsTT0jB8B0jBAoGkDozBgFkuCA8GAgPA8BA8BAkDUsTA0K8B89BUoaoBwWAkDA4coB0ekDkXoBsYA4SA8LU8LoBkpCA4XUsJoBoL8B4NoBoL8BwM4IozB8BkNgFkXnV7B31BrErJTzPnBv0B7Bn9B3D3ITjc3Dnf3DjcjD3hB7B7LTjhBjDAgFvCoQ7BoV7B4SqR-B"
}


inline fun <T> List<T>.sumOfDistance(selector: (T?, T?) -> Float): Float {
    var sum = 0f

    this.forEachIndexed { index, t ->
        sum += when {
            index > 0 -> {
                val prev = this[index - 1]
                selector(t, prev)
            }
            index == size - 1 -> {
                val next = this[size - 1]
                selector(next, t)
            }
            else -> {
                selector(null, null)
            }
        }
    }
    return sum
}
