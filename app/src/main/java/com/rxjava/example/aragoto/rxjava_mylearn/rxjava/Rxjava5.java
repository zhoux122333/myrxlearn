package com.rxjava.example.aragoto.rxjava_mylearn.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.rxjava.example.aragoto.rxjava_mylearn.gank.GankList;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ARAGoTo on 2017/11/20.
 *
 */

public class Rxjava5 extends AppCompatActivity {
    private static final String TAG = "Rxjava5";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxJava();
    }

    private void RxJava() {
        Rx2AndroidNetworking.get("http://gank.io/api/search/query/listview/category/all/count/10/page/1")
                .build()
                .getObjectObservable(GankList.class)
                .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(new Consumer<GankList>() {
                @Override
                public void accept(GankList gankList) throws Exception {
                    Log.e(TAG,"onNext:"+Thread.currentThread().getName());
                    Log.e(TAG,"onNext:"+gankList.toString());
                }
            }).map(new Function<GankList, Object>() {
            @Override
            public Object apply(GankList gankList) throws Exception {
                Log.e(TAG,"map:"+Thread.currentThread().getName());
                Log.e(TAG,"map:"+gankList.toString());
                return gankList.getResults();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e(TAG, "成功:" + Thread.currentThread().getName());
                        Log.e(TAG, "成功:" + o.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "失败:" + Thread.currentThread().getName());
                        Log.e(TAG, "失败:" + throwable.toString());
                    }
                });
    }

}
