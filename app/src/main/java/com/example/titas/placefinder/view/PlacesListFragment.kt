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
import com.example.titas.placefinder.adapter.FavoriteListener
import com.example.titas.placefinder.adapter.PlacesListAdapter
import com.example.titas.placefinder.repository.model.Place
import com.example.titas.placefinder.repository.model.ResponseWrapper
import kotlinx.android.synthetic.main.places_list_fragment.*

class PlacesListFragment : Fragment(), FavoriteListener {
    private lateinit var rootView: View
    private var isFavoriteView: Boolean = false
    private lateinit var placesList: List<Place>
    private lateinit var adapter: PlacesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.places_list_fragment, container, false)
        isFavoriteView = arguments.getBoolean(PlacesActivity.FAVORITE_VIEW)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init(){
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        places_list.layoutManager = layoutManager

        if(!isFavoriteView) {
            val placesResponseLiveData = (activity as PlacesActivity).placesViewModel.getPlacesListObservable()
            placesResponseLiveData.observe(this, object : Observer<ResponseWrapper> {
                override fun onChanged(response: ResponseWrapper?) {
                    response?.let {
                        if (response.status == ResponseWrapper.Status.OK) {
                            placesList = response.placesList
                            updateList()
                        } else {
                            places_list.visibility = View.GONE
                        }
                    }
                }

            })
        } else {
            val favoritesLiveData = (activity as PlacesActivity).placesViewModel.getFavoritesObservable()
            favoritesLiveData.observe(this, object : Observer<List<Place>> {
                override fun onChanged(t: List<Place>?) {
                    if(t != null && t.size > 0){
                        placesList = t
                        updateList()
                    } else {
                        places_list.visibility = View.GONE
                    }
                }

            })
        }
    }

    private fun updateList(){
        adapter = PlacesListAdapter(placesList, this, isFavoriteView)
        places_list.adapter = adapter
    }

    override fun onFavoriteClicked(place: Place) {
        (activity as PlacesActivity).placesViewModel.favoritePlace(place)
    }

    override fun onUnFavoriteClicked(place: Place, position: Int) {
        (activity as PlacesActivity).placesViewModel.unfavoritePlace(place)
        if(isFavoriteView){
            (placesList as ArrayList).remove(place)
            adapter.notifyItemRemoved(position)
//            adapter.notifyItemRangeChanged(position, adapter.itemCount)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(isFavoriteView: Boolean): PlacesListFragment {
            val fragment = PlacesListFragment()
            val arguments = Bundle()
            arguments.putBoolean(PlacesActivity.FAVORITE_VIEW, isFavoriteView)
            fragment.arguments = arguments
            return fragment
        }
    }

}
