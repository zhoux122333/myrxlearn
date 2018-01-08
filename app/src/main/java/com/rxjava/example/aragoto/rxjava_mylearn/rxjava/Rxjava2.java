package com.rxjava.example.aragoto.rxjava_mylearn.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ARAGoTo on 2017/11/10.
 * 操作符 distinct filter buffer timer interval doOnNext skip take just
 */

public class Rxjava2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        distinct();
//        Filter();
        buffer();


    }

    /**
     * buffer 操作符接受两个参数，buffer(count,skip)，
     * 作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，然后生成一个  Observable 。
     * 跳过skip的个数开始重新取值
     *
     * timer 很有意思，相当于一个定时任务。在 1.x 中它还可以执行间隔逻辑
     * ，但在 2.x 中此功能被交给了 interval，下一个会介绍。但需要注意的是，timer 和 interval 均默认在新线程。
     */
    private void buffer() {

        Observable.interval(2, TimeUnit.SECONDS).just(1,2,3,4,5,6,7,8)

                .buffer(3,2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.e("bufffer","size:"+integers.size());
                        for (Integer integer : integers){
                            Log.e("buffer",integer+"");
                        }
                    }
                });
    }

    /**
     *filter 过滤器
     */
    private void Filter() {
        Observable.just(1, 20, 65, -5, 7, 19)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer >= 10;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e("filter", "filter : " + integer + "\n");
            }
        });

    }

    /**
     * distinct 去除重复的
     */
    private void distinct() {
        Observable.just(1,2,3,1,6,1,3,7,4)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("distinct",integer+"");
                    }
                });
    }
}
