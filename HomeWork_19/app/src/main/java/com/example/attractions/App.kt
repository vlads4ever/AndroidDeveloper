package com.example.attractions

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(YANDEX_MAPS_API_KEY)
    }

    companion object {
        const val YANDEX_MAPS_API_KEY = "34f8162e-fefb-44e8-ad62-10af56a245b1"
        const val OPEN_TRIP_MAP_API_KEY = "5ae2e3f221c38a28845f05b617ec4e72f2c39d183c6dd72ce3270618"
    }
}