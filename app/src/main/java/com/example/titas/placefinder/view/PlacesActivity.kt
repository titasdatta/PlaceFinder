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
import android.view.Menu
import android.view.MenuItem
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

class PlacesActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_KEY = "SearchTitle";
    }

    @Inject lateinit var placesViewModelFactory: ViewModelProvider.Factory
    lateinit var placesViewModel: PlacesViewModel
    private lateinit var placesResponseLiveData: LiveData<ResponseWrapper>
    private lateinit var searchCategory: String
    private lateinit var mapFragment: PlacesMapFragment
    private var location: Location? = null

    private val locationManager: LocationManager by lazy {
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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = PlacesMapFragment.newInstance()

        supportFragmentManager.beginTransaction().add(R.id.container, mapFragment).commit()
        supportFragmentManager.executePendingTransactions()

        placesViewModel = ViewModelProviders.of(this, placesViewModelFactory)[PlacesViewModel::class.java]
//        mapFragment.getMapAsync(this)
    }

    private fun fetchNearbyPlacesFor(location: Location){
        this.location = location
        val locationString = "${location.latitude},${location.longitude}"
        placesViewModel.searchNearbyPlacesFor(locationString, searchCategory)
        if(locationListener != null) {
            locationManager.removeUpdates(locationListener)
        }
    }


    fun setupLocationManager(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            if(locationManager != null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, locationListener)
            }
            try {
                val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if(location != null){
                    mapFragment.updateMapForCurrentLocation(location = location)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_places, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.toggle){
            toggleFragment(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggleFragment(item: MenuItem){
        if(item.title.toString().equals("list", ignoreCase = true)){
            item.title = "map"
            item.icon = resources.getDrawable(R.drawable.ic_map_placeholder)
            supportFragmentManager.beginTransaction().replace(R.id.container, PlacesListFragment.newInstance())
                    .commit()
        }else {
            item.title = "list"
            item.icon = resources.getDrawable(R.drawable.ic_list)
            supportFragmentManager.beginTransaction().replace(R.id.container, mapFragment)
                    .commit()
        }
        supportFragmentManager.executePendingTransactions()
    }
}
