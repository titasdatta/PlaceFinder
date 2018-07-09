package com.example.titas.placefinder.di.component

import android.app.Activity
import android.app.Application
import com.example.titas.placefinder.di.modules.AppModule
import com.example.titas.placefinder.di.modules.BuildersModule
import com.example.titas.placefinder.di.modules.NetModule
import com.example.titas.placefinder.di.modules.ViewModelModule
import com.example.titas.placefinder.view.SearchActivity
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Titas on 7/6/2018.
 */

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, BuildersModule::class, AppModule::class,
        NetModule::class, ViewModelModule::class))
interface AppComponent {
    fun inject(app: Application)
    fun inject(activity: SearchActivity)
}