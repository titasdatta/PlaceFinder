package com.example.titas.placefinder.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.titas.placefinder.di.ViewModelKey
import com.example.titas.placefinder.viewmodel.SearchViewModel
import com.example.titas.placefinder.viewmodel.CustomViewModelFactory
import com.example.titas.placefinder.viewmodel.PlacesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Titas on 7/9/2018.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlacesViewModel::class)
    internal abstract fun bindPlacesViewModel(placesViewModel: PlacesViewModel): ViewModel

    @Binds
    internal abstract fun bindSearchViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory
}