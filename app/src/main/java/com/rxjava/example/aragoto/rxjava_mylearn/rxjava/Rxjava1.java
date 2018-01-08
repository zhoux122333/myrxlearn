package com.rxjava.example.aragoto.rxjava_mylearn.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ARAGoTo on 2017/11/9.
 * RxJava操作符
 * map zip concat flatMap concatMap
 */

public class Rxjava1 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RxJava();
//        rxjava1();
//        Zip();
//        rxconcat();
        flatMap();
        flatmap();
    }

    /**
     * 它可以把一个发射器  Observable 通过某种方法转换为多个 Observables，
     * 然后再把这些分散的 Observables装进一个单一的发射器 Observable。
     * 但有个需要注意的是，flatMap 并不能保证事件的顺序
     * concatMap可以保证顺序
     */
    private void flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(11);
                e.onNext(22);
                e.onNext(33);
            }
        }).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0;i<4;i++){
                    list.add("Integer"+integer);
                }
                int delayTime = (int) (1+Math.random()*10);

                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>(){

                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e("flatm",o.toString());
                    }
                });
    }
    private void flatmap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.e("12", "flatMap : accept : " + s + "\n");
                    }
                });

    }
    /**{
     * Concat
     * 对于单一的把两个发射器连接成一个发射器，虽然 zip 不能完成，但我们还是可以自力更生，官方提供的 concat 让我们的问题得到了完美解决。
     */
    private void rxconcat() {
        //下方getStringObservable和 getIntegerObservable加一起不行，应该是new的原因
        Observable.concat(Observable.just("1","2"),Observable.just(1,2,3))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e("object",o.toString());
                    }


                });
    }

    /**
     * zip 专用于合并事件，该合并不是连接（连接操作符后面会说），而是两两配对，也就意味着，最终配对出的 Observable 发射事件数目只和少的那个相同。
     */
    private void Zip() {
        Observable.zip(getStringObservable(), getInterObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s+integer;

            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
            Log.e("zip",s);
            }
        });
    }
    private Observable<String> getStringObservable(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                e.onNext("B");
//                e.onComplete();
                e.onNext("C");
            }
        });
    }
    private Observable<Integer> getInterObservable(){
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
//                e.onComplete();
                e.onNext(5);
            }
        });
    }
    private Observable<Integer> getInterObservable1(){
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
//                e.onComplete();
                e.onNext(5);
            }
        });
    }

    /**
     * Map 基本算是 RxJava 中一个最简单的操作符了，熟悉 RxJava 1.x 的知道，它的作用是对发射时间发送的每一个事件应用
     * 一个函数，是的每一个事件都按照指定的函数去变化，而在 2.x 中它的作用几乎一致。
     */
    private void rxjava1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer+"个";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("rx1","onsub");
            }

            @Override
            public void onNext(String s) {
                Log.d("rx1",s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("rx1","onerror");
            }

            @Override
            public void onComplete() {
                Log.d("rx1","oncom");
            }
        });
    }

    private void RxJava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("rxJava1", "accept : " + s +"\n" );
            }
        });

    }
}
