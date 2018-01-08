package com.rxjava.example.aragoto.rxjava_mylearn;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by ARAGoTo on 2017/11/20.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
