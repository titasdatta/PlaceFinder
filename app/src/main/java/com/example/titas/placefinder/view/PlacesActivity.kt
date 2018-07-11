package com.example.titas.placefinder.view

import android.Manifest
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.example.titas.placefinder.R
import com.example.titas.placefinder.common.Constants.Companion.LOCATION_REFRESH_DISTANCE
import com.example.titas.placefinder.common.Constants.Companion.LOCATION_REFRESH_TIME
import com.example.titas.placefinder.common.Constants.Companion.PERMISSION_REQUEST_CODE
import com.example.titas.placefinder.di.PlaceFinderApplication
import com.example.titas.placefinder.repository.model.Place
import com.example.titas.placefinder.repository.model.ResponseWrapper
import com.example.titas.placefinder.viewmodel.PlacesViewModel
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception
import javax.inject.Inject

class PlacesActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        val SEARCH_KEY = "SearchTitle";
    }

    private lateinit var mMap: GoogleMap
    @Inject lateinit var placesViewModelFactory: ViewModelProvider.Factory
    private lateinit var placesViewModel: PlacesViewModel
    private lateinit var placesResponseLiveData: LiveData<ResponseWrapper>
    private lateinit var searchCategory: String
    private var location: Location? = null

    val locationManager: LocationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private var locationListener: LocationListener? = object : LocationListener{
        override fun onLocationChanged(location: Location?) {
            if(location != null){
                fetchNearbyPlacesFor(location)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

        override fun onProviderEnabled(provider: String?) {}

        override fun onProviderDisabled(provider: String?) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as PlaceFinderApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)
        //get the search category
        searchCategory = intent.getStringExtra(SEARCH_KEY);
        setTitle("${searchCategory}s near you")

        placesViewModel = ViewModelProviders.of(this, placesViewModelFactory)[PlacesViewModel::class.java]

        placesResponseLiveData = placesViewModel.getPlacesListObservable()

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

        initialiseMap()

        placesResponseLiveData.observe(this, object : Observer<ResponseWrapper> {
            override fun onChanged(response: ResponseWrapper?) {
                response?.let {
                    if(response.status == ResponseWrapper.Status.OK){
                        drawPlacesOnMapFor(response.placesList)
                    } else {
                        Toast.makeText(this@PlacesActivity, "Places API call failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    private fun initialiseMap(){
        val uiSettings = mMap.uiSettings
        uiSettings.isMyLocationButtonEnabled = false
        uiSettings.setAllGesturesEnabled(true)
        uiSettings.isZoomControlsEnabled = false

        MapsInitializer.initialize(this)

        //TODO: Move map to current location first
        location?.let {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15.0f))
            mMap.isMyLocationEnabled = true
        }
    }

    private fun fetchNearbyPlacesFor(location: Location){
        this.location = location
        val locationString = "${location.latitude},${location.longitude}"
        placesViewModel.searchNearbyPlacesFor(locationString, searchCategory)
        locationManager.removeUpdates(locationListener)
        locationListener = null
    }


    private fun setupLocationManager(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            if(locationManager != null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, locationListener)
            }
            try {
                val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if(location != null){
                    fetchNearbyPlacesFor(location)
                }
            }catch (e: Exception){
                Log.d("PlacesActivity", "Error fetching last location:${e.message}")
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission granted by user
                    setupLocationManager()
                }else{
                    //permission denied by user
                    //TODO: Or maybe show error screen to block the user from using the app
                    //TODO: without giving location permission
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupLocationManager()
    }

    private fun drawPlacesOnMapFor(placesList: ArrayList<Place>){
        mMap.clear()
        placesList.forEach {
            val markerOptions = MarkerOptions()
            val latLng = LatLng(it.geometry.location.lat, it.geometry.location.lng)
            markerOptions.position(latLng)
            markerOptions.title(it.name)
            markerOptions.snippet(it.address)
            mMap.addMarker(markerOptions)
        }
    }

    override fun onStop() {
        super.onStop()
        placesResponseLiveData.removeObservers(this)
    }
}
