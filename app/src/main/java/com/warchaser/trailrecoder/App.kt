package com.warchaser.trailrecoder

import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.multidex.MultiDex

class App : Application(){

    private lateinit var mInstance : App

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        Intent(this, LocationService::class.java).run {
            startService(this)
        }
    }

    fun getInstance() = mInstance

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}