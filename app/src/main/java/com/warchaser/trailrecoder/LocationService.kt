package com.warchaser.trailrecoder

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener
import com.warchaser.baselib.tools.NLog
import com.warchaser.baselib.tools.PackageUtil
import com.warchaser.trailrecoder.tools.LocationUtil

class LocationService : Service() {

    private val TAG : String = PackageUtil.getSimpleClassName(this)

    private var mLocationClient : AMapLocationClient? = null

    private var mNotificationManager : NotificationManager? = null

    private var mNotification : Notification? = null

    private val CHANNEL_ID = "com.warchaser.trailrecoder.LocationService"

    private val NOTIFICATION_ID = 1001

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelNotification()
    }

    private fun startLocating(){
        takeIf { mLocationClient == null }.run {
            try {
                mLocationClient = AMapLocationClient(this@LocationService)
            } catch (e : Throwable) {
                e.message?.run {
                    NLog.eWithFile(TAG, this)
                }
            }
        }

        mLocationClient?.run {
            setLocationListener(mLocationListener)
            setLocationOption(LocationUtil.getDefaultLocationOption())
            startLocation()
        }

        startForeground()
    }

    private val mLocationListener = object : AMapLocationListener{
        override fun onLocationChanged(location: AMapLocation?) {
            NLog.i(TAG, "mLocationListener.onLocationChanged()")

        }
    }

    private fun startForeground(){
        startForeground(NOTIFICATION_ID, getNotification())
    }

    private fun getNotification() : Notification {
        if(mNotificationManager == null){
            mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        }

        Notification.Builder(this, CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher).run {
            NotificationChannel(CHANNEL_ID, "LocationService", NotificationManager.IMPORTANCE_HIGH).run {
                setSound(null, null)
                mNotificationManager?.createNotificationChannel(this)
            }

            setOnlyAlertOnce(true)
            mNotification = build().apply {
                flags != Notification.FLAG_FOREGROUND_SERVICE
            }
        }

        return mNotification!!
    }

    private fun cancelNotification(){
        mNotificationManager?.run {
            NLog.e(TAG, "CancelNotification()")
            cancel(NOTIFICATION_ID)
            stopForeground(STOP_FOREGROUND_DETACH)
        }
        mNotificationManager = null
    }

    override fun onBind(intent: Intent?): IBinder? = null
}