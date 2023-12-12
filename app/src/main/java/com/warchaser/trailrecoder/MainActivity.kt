package com.warchaser.trailrecoder

import android.os.Bundle
import android.view.LayoutInflater
import com.warchaser.baselib.mvp.BasePresenter
import com.warchaser.baselib.mvp.BaseView
import com.warchaser.baselib.ui.BaseActivity
import com.warchaser.trailrecoder.databinding.ActivityMainBinding
import com.warchaser.trailrecoder.tools.ServiceUtil

class MainActivity : BaseActivity<BasePresenter<BaseView>, BaseView, ActivityMainBinding>(){

    override fun afterSetContentView(savedInstanceState: Bundle?) {
        initViews(savedInstanceState)
    }

    private fun initViews(savedInstanceState: Bundle?){
        viewBound.run {

            mMapView.run {
                onCreate(savedInstanceState)
                map.run {
                    isMyLocationEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true
                }
            }

            mBtnStartLocation.setOnClickListener { ServiceUtil.startLocating() }

            mBtnStopLocation.setOnClickListener { ServiceUtil.stopLocating() }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewBound.mMapView.onSaveInstanceState(outState)
    }

    override fun finishAction() {
        super.finishAction()
        viewBound.mMapView.onDestroy()
    }

    override fun inflate(): (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun onLoadPresenter(): BasePresenter<BaseView>? = null
}