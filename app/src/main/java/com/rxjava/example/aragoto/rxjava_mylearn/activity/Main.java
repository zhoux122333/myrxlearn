package com.rxjava.example.aragoto.rxjava_mylearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rxjava.example.aragoto.rxjava_mylearn.R;
import com.rxjava.example.aragoto.rxjava_mylearn.rxjava.MainActivity;
import com.rxjava.example.aragoto.rxjava_mylearn.rxjava.Rx_Retrofit;
import com.rxjava.example.aragoto.rxjava_mylearn.rxjava.Rxjava1;
import com.rxjava.example.aragoto.rxjava_mylearn.rxjava.Rxjava2;
import com.rxjava.example.aragoto.rxjava_mylearn.rxjava.Rxjava3;
import com.rxjava.example.aragoto.rxjava_mylearn.rxjava.Rxjava5;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ARAGoTo on 2017/11/9.
 */

public class Main extends AppCompatActivity {
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.bt3)
    Button bt3;
    @BindView(R.id.bt4)
    Button bt4;
    @BindView(R.id.bt5)
    Button bt5;
    @BindView(R.id.bt6)
    Button tv6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4,R.id.bt6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.bt2:
                startActivity(new Intent(this, Rxjava1.class));
                break;
            case R.id.bt3:
                startActivity(new Intent(this, Rxjava2.class));
                break;
            case R.id.bt4:
                startActivity(new Intent(this, Rxjava3.class));
                break;
            case  R.id.bt6:
                startActivity(new Intent(this, Rx_Retrofit.class));
                break;
        }
    }

    @OnClick(R.id.bt5)
    public void onViewClicked() {
        startActivity(new Intent(this, Rxjava5.class));
    }


}
