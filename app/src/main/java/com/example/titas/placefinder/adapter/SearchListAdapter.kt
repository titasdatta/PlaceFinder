package com.example.titas.placefinder.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.titas.placefinder.R
import com.example.titas.placefinder.common.inflate
import com.example.titas.placefinder.repository.model.SearchData
import com.example.titas.placefinder.view.PlacesActivity
import kotlinx.android.synthetic.main.search_item.view.*
import javax.inject.Inject

/**
 * Created by Titas on 7/9/2018.
 */
class SearchListAdapter @Inject constructor(private val searchList: List<SearchData>):
        RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {

    override fun onBindViewHolder(holder: SearchListViewHolder?, position: Int) {
        holder?.bind(searchList[position])
    }

    override fun getItemCount(): Int = searchList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        return SearchListViewHolder(parent.inflate(R.layout.search_item, false))
    }


    class SearchListViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(searchData: SearchData){
            view.search_title.text = searchData.searchTitle
            view.setOnClickListener {
                val intent = Intent(view.context, PlacesActivity::class.java);
                intent.putExtra(PlacesActivity.SEARCH_KEY, searchData.searchTitle)
                view.context.startActivity(intent)
            }
        }
    }
}