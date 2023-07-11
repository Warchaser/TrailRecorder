package com.warchaser.trailrecoder.tools

import com.amap.api.location.AMapLocationClientOption

class LocationUtil {

    companion object{
        /**
         * 获取默认定位选项
         * */
        @JvmStatic
        fun getDefaultLocationOption() : AMapLocationClientOption{
            return AMapLocationClientOption().apply {
                //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
                locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
                isGpsFirst = true
                //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
                httpTimeOut = 30000
                //可选，设置定位间隔。默认为2秒
                interval = 2000
                //可选，设置是否返回逆地理地址信息。默认是true
                isNeedAddress = true
                //可选，设置是否单次定位。默认是false
                isOnceLocation = false
                //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
                isOnceLocationLatest = false
                //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
                AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTPS)
                //可选，设置是否使用传感器。默认是false
                isSensorEnable = true
                //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
                isWifiScan = true
                //可选，设置是否使用缓存定位，默认为true
                isLocationCacheEnable = true
            }
        }
    }

}