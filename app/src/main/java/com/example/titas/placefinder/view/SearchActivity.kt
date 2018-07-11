package com.example.titas.placefinder.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.titas.placefinder.R
import com.example.titas.placefinder.adapter.SearchListAdapter
import com.example.titas.placefinder.di.PlaceFinderApplication
import com.example.titas.placefinder.repository.model.SearchData
import com.example.titas.placefinder.viewmodel.SearchViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    @Inject lateinit var searchViewModelFactory: ViewModelProvider.Factory
    private lateinit var searchViewModel: SearchViewModel
    private var searchListAdapter: SearchListAdapter? = null
    private lateinit var searchListLiveData: LiveData<List<SearchData>>

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as PlaceFinderApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title = "Place Finder"

        searchViewModel = ViewModelProviders.of(this, searchViewModelFactory)[SearchViewModel::class.java]

        searchListLiveData = searchViewModel.getSearchListObservable()
        searchListLiveData.observe(this, object : Observer<List<SearchData>>{
            override fun onChanged(t: List<SearchData>?) {
                if(t != null && t.isNotEmpty()){
                    updateSearchList(t)
                }
            }

        })

        //initialising the recycler view
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        searches_list.layoutManager = layoutManager

        btn_search.setOnClickListener {
            val searchTitle = search_bar.text.toString()
            search_bar.setText("")
            if(!TextUtils.isEmpty(searchTitle)){
                searchViewModel.insertSearch(SearchData(searchTitle = searchTitle,searchTime = System.currentTimeMillis()))

                val intent = Intent(this@SearchActivity, PlacesActivity::class.java);
                intent.putExtra(PlacesActivity.SEARCH_KEY, searchTitle)
                startActivity(intent)
            }else {
                Toast.makeText(this@SearchActivity, "Please input a proper search category", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateSearchList(searchList: List<SearchData>){
        searches_list_container.visibility = View.VISIBLE
        if(searchListAdapter == null){
            searchListAdapter = SearchListAdapter(searchList)
        } else {
            searchListAdapter?.updateSearchList(searchList)
        }
        searches_list.adapter = searchListAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.favorite){
            val intent = Intent(this@SearchActivity, PlacesActivity::class.java);
            intent.putExtra(PlacesActivity.FAVORITE_VIEW, true)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
