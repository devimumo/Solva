package com.example.solva.Vendor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.solva.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
private lateinit var location_chosen: LatLng
private var googlemap: GoogleMap? =null


class AddGarage : AppCompatActivity(), OnMapReadyCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_garage)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        mapFragment.getMapAsync{

            googlemap=it
            googlemap?.setOnMapClickListener {

                location_chosen=it



                googlemap?.clear()

                Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()

                var location_here=LatLng(it.latitude,it.longitude)

                googlemap?.addMarker(MarkerOptions().position(location_here).title("Marker clicked"))
               // googlemap?.moveCamera(CameraUpdateFactory.newLatLng(location_here))

                googlemap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location_here, 18f))


            }
        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        googlemap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        googlemap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googlemap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}