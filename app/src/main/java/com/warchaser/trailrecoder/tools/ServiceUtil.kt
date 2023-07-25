package com.warchaser.trailrecoder.tools

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.warchaser.trailrecoder.App
import com.warchaser.trailrecoder.LocationService

class ServiceUtil {

    companion object{

        const val START_LOCATING : String = "START_LOCATING"

        const val STOP_LOCATING : String = "STOP_LOCATING"

        fun sendAction2Service(context : Context, action : String){
            Intent(context, LocationService::class.java).run {
                setAction(action)
                putExtras(Bundle())
                context.startService(this)
            }
        }

        fun startLocating(){
            sendAction2Service(App.getInstance(), START_LOCATING)
        }

        fun stopLocating(){
            sendAction2Service(App.getInstance(), STOP_LOCATING)
        }
    }

}