package com.warchaser.trailrecoder

import android.os.Bundle
import android.view.LayoutInflater
import com.warchaser.baselib.mvp.BasePresenter
import com.warchaser.baselib.mvp.BaseView
import com.warchaser.baselib.ui.BaseActivity
import com.warchaser.trailrecoder.databinding.ActivityMainBinding

class MainActivity : BaseActivity<BasePresenter<BaseView>, BaseView, ActivityMainBinding>(){

    override fun afterSetContentView(savedInstanceState: Bundle?) {
        viewBound.run {
            mMapView.onCreate(savedInstanceState)

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