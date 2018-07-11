package com.example.titas.placefinder.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.example.titas.placefinder.repository.model.Location
import com.example.titas.placefinder.repository.model.Place
import com.example.titas.placefinder.repository.model.ResponseWrapper
import com.example.titas.placefinder.viewmodel.PlacesViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class PlacesMapFragment: SupportMapFragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var isFavoriteView: Boolean = false


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        retainInstance = true

        isFavoriteView = arguments.getBoolean(PlacesActivity.FAVORITE_VIEW)
        getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    override fun onMapReady(map: GoogleMap) {
        mMap = map
        val uiSettings = map.uiSettings
        uiSettings.isMyLocationButtonEnabled = false
        uiSettings.setAllGesturesEnabled(true)
        uiSettings.isZoomControlsEnabled = false

        MapsInitializer.initialize(activity)
        init()

        (activity as PlacesActivity).setupLocationManager()
    }

    private fun init(){
        if(!isFavoriteView) {
            val placesResponseLiveData = (activity as PlacesActivity).placesViewModel.getPlacesListObservable()
            placesResponseLiveData.observe(this, object : Observer<ResponseWrapper> {
                override fun onChanged(response: ResponseWrapper?) {
                    response?.let {
                        if (response.status == ResponseWrapper.Status.OK) {
                            drawPlacesOnMapFor(response.placesList)
                        } else {
                            Toast.makeText(activity, "Places API call failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })
        } else {
            val favoritesLiveData = (activity as PlacesActivity).placesViewModel.getFavoritesObservable()
            favoritesLiveData.observe(this, object : Observer<List<Place>> {
                override fun onChanged(t: List<Place>?) {
                    if(t != null && t.size > 0){
                        drawPlacesOnMapFor(t as ArrayList<Place>)
                    } else {
                        Toast.makeText(activity, "You don't have any favorites yet", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    fun updateMapForCurrentLocation(location: android.location.Location){
        //TODO: Move map to current location first
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 15.0f))
        mMap.isMyLocationEnabled = true
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

    companion object {
        fun newInstance(isFavoriteView: Boolean): PlacesMapFragment {
            val fragment = PlacesMapFragment()
            val arguments = Bundle()
            arguments.putBoolean(PlacesActivity.FAVORITE_VIEW, isFavoriteView)
            fragment.arguments = arguments
            return fragment
        }
    }
}