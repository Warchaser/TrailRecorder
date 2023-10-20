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
import com.warchaser.baselib.tools.StringUtil
import com.warchaser.trailrecoder.tools.LocationUtil
import com.warchaser.trailrecoder.tools.ServiceUtil

class LocationService : Service() {

    private val TAG : String = PackageUtil.getSimpleClassName(this)

    private var mLocationClient : AMapLocationClient? = null

    private var mNotificationManager : NotificationManager? = null

    private var mNotification : Notification? = null

    private val CHANNEL_ID = "com.warchaser.trailrecoder.LocationService"

    private val NOTIFICATION_ID = 1001

    override fun onCreate() {
        super.onCreate()
        NLog.i(TAG, "LocationService.onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleCommand(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        NLog.i(TAG, "LocationService.onDestroy()")
        cancelNotification()
    }

    private fun handleCommand(intent: Intent?){
        intent?.run {
            val action : String? = action
            action?.run {
                NLog.i(TAG, "handleCommand, action is ${StringUtil.getRealValue(this)}")
                when(this){
                    ServiceUtil.START_LOCATING -> {
                        startLocating()
                    }
                    ServiceUtil.STOP_LOCATING -> {
                        stopLocating()
                    }
                }
            }
        }
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

    private fun stopLocating(){
        mLocationClient?.run {
            stopLocation()
            cancelNotification()
        }
    }

    private val mLocationListener = object : AMapLocationListener{
        override fun onLocationChanged(location: AMapLocation?) {
            NLog.i(TAG, "mLocationListener.onLocationChanged()")
            location?.run {
                NLog.i(TAG, "altitude is $altitude")
                NLog.i(TAG, "longitude is $longitude")
            }
        }
    }

    private fun startForeground(){
        startForeground(NOTIFICATION_ID, getNotification())
    }

    private fun getNotification() : Notification {
        NLog.i(TAG, "StartNotification()")
        if(mNotificationManager == null){
            mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        }

        Notification.Builder(this, CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher).run {
            NotificationChannel(CHANNEL_ID, TAG, NotificationManager.IMPORTANCE_HIGH).run {
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
        NLog.i(TAG, "CancelNotification()")
        mNotificationManager?.run {
            cancel(NOTIFICATION_ID)
            stopForeground(STOP_FOREGROUND_DETACH)
        }
        mNotificationManager = null
    }

    override fun onBind(intent: Intent?): IBinder? = null
}