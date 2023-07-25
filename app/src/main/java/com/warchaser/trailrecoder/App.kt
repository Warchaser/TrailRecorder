package com.warchaser.trailrecoder

import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.multidex.MultiDex
import com.amap.api.location.AMapLocationClient
import com.warchaser.baselib.tools.NLog

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        NLog.initLogFile(this)

        AMapLocationClient.updatePrivacyAgree(this, true)
        AMapLocationClient.updatePrivacyShow(this, true, true)

        Intent(this, LocationService::class.java).run {
            startService(this)
        }
    }

    companion object{

        private lateinit var mInstance : App
        @JvmStatic
        fun getInstance() = mInstance
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}