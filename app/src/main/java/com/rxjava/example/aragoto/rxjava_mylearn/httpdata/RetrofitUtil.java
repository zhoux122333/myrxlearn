package com.rxjava.example.aragoto.rxjava_mylearn.httpdata;

import com.rxjava.example.aragoto.rxjava_mylearn.api.GankApi;
import com.rxjava.example.aragoto.rxjava_mylearn.constant.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ARAGoTo on 2017/11/20.
 * 单例
 */

public class RetrofitUtil {
    private volatile static RetrofitUtil sInstance;
    private Retrofit mRetrofit;
    private GankApi mGankApi;
    private RetrofitUtil(){
        mRetrofit = new Retrofit.Builder()
                .client(getHttpClient())
                .baseUrl(Constant.host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mGankApi = mRetrofit.create(GankApi.class);
    }


    public static RetrofitUtil getInstance(){
        if (sInstance==null){
            synchronized (RetrofitUtil.class){
                if (sInstance == null){
                    sInstance = new RetrofitUtil();
                }
            }
        }
        return sInstance;
    }
    public GankApi getApi(){
        return  mGankApi;
    }
    private static OkHttpClient getHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .addInterceptor(new ljq())
                .build();
    }
}
