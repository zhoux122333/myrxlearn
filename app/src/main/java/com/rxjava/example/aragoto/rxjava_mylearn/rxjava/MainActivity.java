package com.rxjava.example.aragoto.rxjava_mylearn.rxjava;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rxjava.example.aragoto.rxjava_mylearn.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * RxJava的基本使用
 */

public class MainActivity extends AppCompatActivity {
    public static Context context;
    public static final String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxJava();
    }

    private void RxJava() {
        //1.创建被观察者Observable对象
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            //2.在复写的subscribe里面定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();

            }
        });
        /**
         * 扩展：RxJava提供其他方法用于创建被观察者对象Observable
         */
        // 方法1：just(T...)直接将传入的参数依次发送出来
        Observable<String> observable1 = observable.just("A", "B", "C");
        // 将会依次调用：
        // onNext("A");
        // onNext("B");
        // onNext("C");
        // onCompleted();

        //方法2：from(T[])/from(Iterable<? extends T>) : 将传入的数组/Iterable 拆分成具体对象后
        //依次发出
        String[] words = {"A", "B", "C"};
        Observable observable2 = Observable.fromArray(words);
        // 将会依次调用：
        // onNext("A");
        // onNext("B");
        // onNext("C");
        // onCompleted();

        /**
         * 创建观察者
         * 方式1：采用Observer接口
         */
        //1.创建观察者(Observer)对象
        Observer<Integer> observer = new Observer<Integer>() {
            //2.创建对象时通过对应事件方法来相应对应事件

            //观察者接收事件前，默认最先调用复写 onSubscribe()
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            //当被观察者生产Next事件&观察者接收到时，会调用复写该方法，进行响应
            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "对Next事件做出响应" + integer);
            }

            //当被观察者生产Error事件&观察者收到时，会调用复写该方法，进行响应
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件做出响应");
            }

            //当被观察者生产Complete事件&观察者收到时，会调用复写该方法，进行响应
            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件做出响应");
            }
        };
        Observer<String> observer1 = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "observer1开始采用subscribe连接");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "observer1对Next事件做出响应" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "observer1对Error事件做出响应");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "observer1对Error事件做出响应");

            }
        };
        /**
         * 方式2:采用Subscribe 抽象类
         * 说明：Subscriber类 = RxJava 内置的一个实现了Observer的抽象类
         * 对Observer进行了扩展
         */

        //1.创建观察者对象
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "对Next事件做出响应" + s);

            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "对Error事件做出响应");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件做出响应");

            }
        };

        Subscriber<Integer> subscriber1 = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "subscriber1对Next事件做出响应" + integer);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        /**
         * 特别注意：2种方法的区别，即Subscriber 抽象类与Observer接口的区别
         */
        //相同点： 二者基本使用方式完全一致（实质上，在RxJava的subscribe过程中，Observer总是会先被
        // 转换成Subscriber再使用)
        //不同点：Subscriber抽象类对Observer接口进行了扩展，新增了两个方法：
        //1.onStart():在还未响应事件前调用，用于做一些初始化工作
        //2.unSubscribe():用于取消订阅。在该方法被调用后，观察者将不再接收&响应事件
        //调用方法前，先使用isUnsubscribed()判断状态，确定被观察者Observable是否还持有观察者Subscriber的引用，
        //如果引用不能及时释放，就会出现内存泄漏

        /**
         * 通过订阅Subscribe连接观察者和被观察者
         */
        observable.subscribe(observer);
        observable2.subscribe(observer1);
//        observable.subscribe(subscriber1);

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(4);
                e.onNext(3);
                e.onNext(2);
                e.onComplete();
                e.onNext(1);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe：");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }
}
