package com.warchaser.baselib.tools;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava相关工具
 * */
public class RxJavaUtil {

    private RxJavaUtil(){

    }

    /**
     * 简单实现
     * RxJava异步线程
     * */
    public static <T> void launchASyncTask(ObservableOnSubscribe<T> source) {
        Observable.create(source).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();
    }

    /**
     * 使用T作为数据源
     * R作为返回数据
     * 不支持背压
     * */
    public static synchronized <T, R> Disposable justNFlatMapO(T t, Function<T, ObservableSource<R>> in, Consumer<R> out, Consumer<Throwable> doOnError){
        return Observable.just(t)
                .flatMap(in)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(out, doOnError);
    }

    /**
     * 使用T作为数据源
     * R作为返回数据
     * 不支持背压
     * */
    public static synchronized <T, R> Disposable justNFlatMapOUI(T t, Function<T, ObservableSource<R>> in, Consumer<R> out, Consumer<Throwable> doOnError){

        if(t == null){
            return Observable.error(new NullPointerException("justNFlatMapOUI, Data source is null"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {

                        }
                    }, doOnError);
        }

        return Observable.just(t)
                .flatMap(in)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(out, doOnError);
    }

    public static synchronized <T, R> Disposable justNFlatMapOThread(T t, Function<T, ObservableSource<R>> in, Consumer<R> out, Consumer<Throwable> doOnError){
        if(t == null){
            return Observable.error(new NullPointerException("justNFlatMapOUI, Data source is null"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {

                        }
                    }, doOnError);
        }

        return Observable.just(t)
                .flatMap(in)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(out, doOnError);
    }

    /**
     * 使用T作为数据源
     * R作为返回数据
     * 支持背压
     * */
    public static synchronized <T, R> Disposable justNFlatMapF(T t, Function<T, Publisher<R>> in, Consumer<R> out, Consumer<Throwable> doOnError){
        return Flowable.just(t)
                .flatMap(in)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(out, doOnError);
    }

    /**
     * 使用T作为数据源
     * R作为返回数据
     * 支持背压
     * 可以延时（单位为毫秒）
     * */
    public static synchronized <T, R> Disposable justNFlatMapFDelay(T t, Function<T, Publisher<R>> in, Consumer<R> out, Consumer<Throwable> doOnError, long delayedTime){
        return Flowable.just(t)
                .flatMap(in)
                .delay(delayedTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(out, doOnError);
    }

    /**
     * 释放Disposable任务
     * @param disposable Observable返回的对象
     * */
    public static synchronized void dispose(Disposable disposable){
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
            disposable = null;
        }
    }

    public static Consumer<Throwable> getCommonError(){
        return mCommonError;
    }

    private static final Consumer<Throwable> mCommonError = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) throws Exception {
            if(throwable != null){
                throwable.printStackTrace();
            }
        }
    };

}
