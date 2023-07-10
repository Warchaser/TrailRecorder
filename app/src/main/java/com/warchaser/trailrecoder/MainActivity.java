package com.warchaser.trailrecoder;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.warchaser.baselib.mvp.BasePresenter;
import com.warchaser.baselib.mvp.BaseView;
import com.warchaser.baselib.ui.BaseActivity;
import com.warchaser.trailrecoder.databinding.ActivityMainBinding;

import kotlin.jvm.functions.Function1;

public class MainActivity extends BaseActivity<BasePresenter<BaseView>, BaseView, ActivityMainBinding> {

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        super.afterSetContentView(savedInstanceState);
    }

    @Override
    protected Function1<LayoutInflater, ActivityMainBinding> inflate() {
        return ActivityMainBinding::inflate;
    }

    @Override
    protected BasePresenter onLoadPresenter() {
        return null;
    }
}