package com.example.titas.placefinder.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.titas.placefinder.R
import com.example.titas.placefinder.adapter.PlacesListAdapter
import com.example.titas.placefinder.repository.model.Place
import com.example.titas.placefinder.repository.model.ResponseWrapper
import kotlinx.android.synthetic.main.places_list_fragment.*

class PlacesListFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.places_list_fragment, container, false)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun init(){
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        places_list.layoutManager = layoutManager

        val placesResponseLiveData = (activity as PlacesActivity).placesViewModel.getPlacesListObservable()
        placesResponseLiveData.observe(this, object : Observer<ResponseWrapper> {
            override fun onChanged(response: ResponseWrapper?) {
                response?.let {
                    if(response.status == ResponseWrapper.Status.OK){
                        updateList(response.placesList)
                    } else {
                        places_list.visibility = View.GONE
                    }
                }
            }

        })
    }

    private fun updateList(placesList: List<Place>){
        val adapter = PlacesListAdapter(placesList)
        places_list.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlacesListFragment()
    }

}
