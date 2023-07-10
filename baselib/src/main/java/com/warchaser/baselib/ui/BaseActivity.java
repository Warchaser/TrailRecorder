package com.warchaser.baselib.ui;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.warchaser.baselib.mvp.BasePresenter;
import com.warchaser.baselib.mvp.BaseView;
import com.warchaser.baselib.tools.AppManager;
import com.warchaser.baselib.tools.PackageUtil;

import kotlin.jvm.functions.Function1;

@SuppressWarnings({"unused", "EmptyMethod", "unchecked"})
public abstract class BaseActivity<P extends BasePresenter<V>, V extends BaseView, VB extends ViewBinding> extends AppCompatActivity implements BaseView{

    protected String TAG;
    protected P mPresenter;

    private VB mViewBound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //解决切换系统语言后布局错乱的问题
        if(savedInstanceState != null){
            savedInstanceState.clear();
        }
        AppManager.getInstance().addActivity(this);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        TAG = PackageUtil.getSimpleClassName(this);
        mPresenter = onLoadPresenter();
        if(mPresenter != null){
            mPresenter.attachView((V)this);
        }

        beforeSetContentView(savedInstanceState);
        mViewBound = inflate().invoke(getLayoutInflater());
        onSetContentView();
        afterSetContentView(savedInstanceState);
    }

    protected abstract Function1<LayoutInflater, VB> inflate();

    protected final VB getViewBound(){
        return mViewBound;
    }

    protected final View getRootView(){
        return getViewBound().getRoot();
    }

    protected void onSetContentView(){
        setContentView(mViewBound.getRoot());
    }

    protected boolean isPresenterNotNull(){
        return mPresenter != null;
    }

    /**
     * setContent之前操作
     */
    protected void beforeSetContentView(Bundle savedInstanceState){

    }

    /**
     * setContent之后操作
     */
    protected void afterSetContentView(Bundle savedInstanceState){

    }

    @SuppressWarnings("SameReturnValue")
    protected abstract P onLoadPresenter();

    @Override
    public void finish() {
        super.finish();
        AppManager.getInstance().removeActivity(this);
        finishAction();
    }

    protected void finishAction(){
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {

            final Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
            window.setAttributes(params);
        }
    }

    public boolean isSoftShowing() {
        // 获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        // 获取View可见区域的bottom
        Rect rect = new Rect();
        // DecorView即为activity的顶级view
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // 考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        // 选取screenHeight*2/3进行判断
        return screenHeight*2/3 > rect.bottom;
    }

    protected void setClickListener(View.OnClickListener listener, View... views){
        for (View view : views) {
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }
}
