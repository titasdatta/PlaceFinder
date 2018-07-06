package com.example.titas.placefinder.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.titas.placefinder.R
import dagger.android.AndroidInjection

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        AndroidInjection.inject(this)
    }
}
