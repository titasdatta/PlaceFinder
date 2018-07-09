package com.example.titas.placefinder.di

import android.app.Activity
import android.app.Application
import com.example.titas.placefinder.common.Constants
import com.example.titas.placefinder.di.component.AppComponent
import com.example.titas.placefinder.di.component.DaggerAppComponent
import com.example.titas.placefinder.di.modules.AppModule
import com.example.titas.placefinder.di.modules.NetModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Titas on 7/6/2018.
 */
class PlaceFinderApplication: Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(Constants.MAPS_REQUEST_BASE_URL))
                .build()
        component.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}