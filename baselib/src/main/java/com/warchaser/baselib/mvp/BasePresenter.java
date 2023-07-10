package com.warchaser.baselib.mvp;

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V>{

    protected V view;

    public BasePresenter(){

    }

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    protected boolean isViewNull(){
        return view == null;
    }

    protected boolean isViewNotNull(){
        return !isViewNull();
    }
}
