package com.example.titas.placefinder.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.titas.placefinder.BR
import com.example.titas.placefinder.R
import com.example.titas.placefinder.databinding.PlacesListRowItemBinding
import com.example.titas.placefinder.repository.model.Place

class PlacesListAdapter( private val placesList: List<Place>): RecyclerView.Adapter<PlacesListAdapter.PlacesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlacesViewHolder {
        val binding = DataBindingUtil.inflate<PlacesListRowItemBinding>(LayoutInflater.from(parent?.context),
                R.layout.places_list_row_item, parent, false)
        return PlacesViewHolder(binding)
    }

    override fun getItemCount() = placesList.size

    override fun onBindViewHolder(holder: PlacesViewHolder?, position: Int) {
        var place = placesList[position]
        holder?.bind(place)
    }


    class PlacesViewHolder(private val binding: PlacesListRowItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place){
            binding.setVariable(BR.place, place)
            binding.executePendingBindings()
        }
    }
}