package com.warchaser.trailrecoder.tools

import com.amap.api.location.AMapLocation
import com.warchaser.baselib.tools.CopyUtil
import com.warchaser.baselib.tools.RxJavaUtil
import io.reactivex.ObservableOnSubscribe

class RecordManager {

    companion object{
        fun getInstance() = Instance.instance
    }

    object Instance{
        val instance = RecordManager()
    }

    /**
     * 当前用于存储位置的list
     * 有最大长度判断
     * */
    private val mRecordingList = arrayListOf<AMapLocation>()

    private val MAX_LENGTH : Int = 100

    fun insertRecord(location: AMapLocation){
        RxJavaUtil.launchASyncTask(ObservableOnSubscribe<Any> {
            if(mRecordingList.size >= MAX_LENGTH){
                val result = CopyUtil.copy(mRecordingList)
                flush2Database(result)

            }
            mRecordingList.add(location)
        })
    }

    private fun flush2Database(list : ArrayList<AMapLocation>){

        mRecordingList.clear()
    }

}