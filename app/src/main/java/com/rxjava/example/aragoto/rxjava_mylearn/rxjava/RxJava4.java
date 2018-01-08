package com.rxjava.example.aragoto.rxjava_mylearn.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.rxjava.example.aragoto.rxjava_mylearn.gank.gank1;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by ARAGoTo on 2017/11/10.
 */

public class RxJava4 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //简单网络请求
        rxjavaNet();
    }

    private void rxjavaNet() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> e) throws Exception {
                Request.Builder builder = new Request.Builder().url("http://gank.io/api/data/Android/10/1");
                Request request = builder.build();
                Call call = new OkHttpClient().newCall(request);
                Response response = call.execute();
                e.onNext(response);
            }
        }).map(new Function<Response, gank1>() {
            @Override
            public gank1 apply(Response response) throws Exception {
                Log.i("线程名称：", "线程：" + Thread.currentThread().getName());
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        Log.e("map转换前：", "map转换前：" + response.body());
                        return new Gson().fromJson(responseBody.string(),gank1.class);
                    }
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<gank1>() {
                    @Override
                    public void accept(gank1 gank1) throws Exception {
                        Log.i("线程名称：", "线程：" + Thread.currentThread().getName());
                        Log.i("gank1:","gank1:"+gank1.getResults().toString());
                    }
                }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<gank1>() {
                    @Override
                    public void accept(gank1 gank1) throws Exception {
                        Log.i("线程名称：", "线程：" + Thread.currentThread().getName());
                        Log.i("gank1:", "gank1:" + gank1.getResults().toString());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("线程名称：", "线程：" + Thread.currentThread().getName());
                        Log.i("失败","失败"+throwable.getMessage());
                    }
                });
    }
}
