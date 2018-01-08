package com.rxjava.example.aragoto.rxjava_mylearn.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rxjava.example.aragoto.rxjava_mylearn.R;
import com.rxjava.example.aragoto.rxjava_mylearn.gank.gank1;
import com.rxjava.example.aragoto.rxjava_mylearn.httpdata.RetrofitUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ARAGoTo on 2017/11/20.
 */

public class Rx_Retrofit extends AppCompatActivity {
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava_retrofit);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt1, R.id.bt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                NoRx();
                break;
            case R.id.bt2:
                Rx();

                break;
        }
    }

    private void Rx() {
        final long time1 = System.currentTimeMillis();
        RetrofitUtil.getInstance().getApi().getAllGank()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<gank1>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(gank1 gank1) {
                        Log.e("onNext", gank1.toString());
                        long time2 = System.currentTimeMillis();
                        Log.e("norx", "请求耗时：" + (time2 - time1) + "ms");
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", "错误" + e.getMessage());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void NoRx() {
        final long time1 = System.currentTimeMillis();
        Call<ResponseBody> call = RetrofitUtil.getInstance().getApi()
                .getAllGankNoRx();
        long time3 = System.currentTimeMillis();
        Log.e("norx", "请求耗时1：" + (time3 - time1) + "ms");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    long time2 = System.currentTimeMillis();
                    String result = response.body().string();
                    Log.e("norx", "norx:" + result);
                    Log.e("norx", "请求耗时2：" + (time2 - time1) + "ms");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Rx_Retrofit.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
