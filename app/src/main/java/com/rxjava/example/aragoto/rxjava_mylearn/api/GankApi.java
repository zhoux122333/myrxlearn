package com.rxjava.example.aragoto.rxjava_mylearn.api;

import com.rxjava.example.aragoto.rxjava_mylearn.gank.gank1;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ARAGoTo on 2017/11/20.
 *
 */

public interface GankApi {
    //http://gank.io/api
    //所有干货
    @GET("data/Android/10/1")
    Observable<gank1> getAllGank();


    @GET("data/Android/10/1")
    Call<ResponseBody> getAllGankNoRx();
}
