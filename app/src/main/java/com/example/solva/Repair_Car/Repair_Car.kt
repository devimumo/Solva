package com.example.solva.Repair_Car

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import com.example.solva.R
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.maps_bottom_sheet.*
import kotlinx.android.synthetic.main.repair__car.*
import android.location.LocationManager as LocationManager1


private  var  REQUEST_LOCATION = 199
private lateinit var locationCallback: LocationCallback
private   var bottomSheetBehavior: BottomSheetBehavior<LinearLayout> ?=null

class Repair_Car : AppCompatActivity(), GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnCameraMoveCanceledListener,
    GoogleMap.OnCameraIdleListener,
    OnMapReadyCallback {
    private  var  location_permission_request_code =2
    private var lat=-34.0
    private var long=151.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  var last_known_location: LatLng=LatLng(lat, long)
    private  var googele_map: GoogleMap? =null

    override fun onCreate(savedInstanceState: Bundle?) {


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.repair__car)

        //check_location_permissions()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

       bottomSheetBehavior = BottomSheetBehavior.from(persistent_bottom_sheet )

        mapFragment.getMapAsync{

            googele_map=it

            googele_map?.setOnCameraMoveListener {
               // Toast.makeText(this,it.toString()+"here we move",Toast.LENGTH_LONG).show()
                bottomSheetBehavior?.setHideable(true);
                bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_HIDDEN);
               // expandCollapseSheet()
            }

          /*  googele_map?.setOnCameraMoveCanceledListener {

                bottomSheetBehavior?.setHideable(false);
                bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_EXPANDED);

                expandCollapseSheet()

            }*/

            googele_map?.setOnCameraIdleListener {

                coordinatorlayout.visibility=View.VISIBLE

               bottomSheetBehavior?.setHideable(false);
                bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            googele_map?.setOnCameraMoveStartedListener {

                coordinatorlayout.visibility=View.GONE
                  bottomSheetBehavior?.setHideable(true);
                  bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_COLLAPSED);

}
}
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////



    private fun expandCollapseSheet() {
        if (bottomSheetBehavior?.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
          //  persistentBtn.text = "Close Bottom Sheet"
        } else {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
         //   persistentBtn.text = "Show Bottom Sheet"
        }
    }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////


       private fun check_location_permissions()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    location_permission_request_code
                )
            }
            else
            {
                googele_map?.isMyLocationEnabled = true

            }
        }
        else
        {

          var location_for_device_enabled_check=  isLocationEnabled(this)

            if (location_for_device_enabled_check==true)
            {
                access_my_current_location()
            }
            else
            {
                get_location_enabled_for_device()

            }

        }
    }



        private fun isLocationEnabled(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager1
            return LocationManagerCompat.isLocationEnabled(locationManager)
        }


//check if loation is enabled for device,if its not then this user gets prompt to enable this
   private fun get_location_enabled_for_device() {

        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)

       val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->

            access_my_current_location()
        }

        task.addOnFailureListener { exception->
            try {
                if (exception is ResolvableApiException) {
                    exception.startResolutionForResult(this@Repair_Car, REQUEST_LOCATION)

                }

            } catch (sendEx: IntentSender.SendIntentException) {

            }
            }     
       
    }



    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == REQUEST_LOCATION) {
            // user is back from location settings - check if location services are now enabled
          //  android.widget.Toast.makeText(this, " user is back from location settings - check if location services are now enabled\n", android.widget.Toast.LENGTH_SHORT).show()

            var location_for_device_enabled_check=  isLocationEnabled(this)

            if (location_for_device_enabled_check==true)
            {

access_my_current_location()
            }
            else
            {
                get_location_enabled_for_device()

            }        }
    }


    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            2 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the

                    googele_map?.isMyLocationEnabled = true
                    check_location_permissions()
                } else { // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    android.widget.Toast.makeText(
                        this,
                        "Map permission is denied",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()

                    //  finish()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        googele_map = googleMap

        // Add a marker in Sydney and move the camera
       // val sydney = LatLng(-34.0, 151.0)
        check_location_permissions()
    }

    @SuppressLint("MissingPermission")
    private fun access_my_current_location() {
        googele_map?.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

                    // Got last known location. In some rare situations this can be null.
try {
    lat= location?.latitude!!
    long=location?.longitude

    last_known_location = LatLng(lat!!, long!!)
    if (last_known_location!=null) {
        googele_map?.addMarker(MarkerOptions().position(last_known_location!!).title("hapa"))
        //  googele_map?.moveCamera(CameraUpdateFactory.newLatLng(last_known_location))
        googele_map?.animateCamera(CameraUpdateFactory.newLatLngZoom(last_known_location, 16f))

    }

}catch (e: NullPointerException)
{
check_location_permissions()
}
                }

        fusedLocationClient.lastLocation.addOnFailureListener {

            android.widget.Toast.makeText(this, "Map 1112", android.widget.Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCameraMoveStarted(p0: Int) {
Toast.makeText(this,"how noooooooow",Toast.LENGTH_LONG).show()
    }

    override fun onCameraMove() {

        TODO("Not yet implemented")
    }

    override fun onCameraMoveCanceled() {

        TODO("Not yet implemented")
    }

    override fun onCameraIdle() {

        TODO("Not yet implemented")
    }


}