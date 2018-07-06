package com.example.titas.placefinder.di.modules

import com.example.titas.placefinder.view.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Titas on 7/6/2018.
 */
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchActivity(): SearchActivity
}