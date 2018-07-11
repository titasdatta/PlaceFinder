package com.example.titas.placefinder.dao_tests

import com.example.titas.placefinder.repository.model.SearchData

/**
 * Created by Titas on 7/12/2018.
 */
class FakeSearchData {
    companion object {
        const val FAKE_SEARCH_TITLE: String = "Fake Search Title"

        fun getFakeSearchData(): SearchData = SearchData(searchTitle = FAKE_SEARCH_TITLE, searchTime =  System.currentTimeMillis())

        fun getFakeSearchDataList(count: Int): List<SearchData> {
            val searchDataList: ArrayList<SearchData> = ArrayList()
            for(i in 1..count){
                searchDataList.add(SearchData(searchTitle = "${FAKE_SEARCH_TITLE}_${i}", searchTime = System.currentTimeMillis()))
            }
            return searchDataList
        }
    }
}