package com.warchaser.baselib.mvp;

public interface IBasePresenter<V extends IBaseView> {

    void attachView(V view);

    void detachView();

}
