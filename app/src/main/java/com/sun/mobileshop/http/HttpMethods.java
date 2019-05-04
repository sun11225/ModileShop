package com.sun.mobileshop.http;

import android.util.Log;

import com.sun.mobileshop.common.Constants;
import com.sun.mobileshop.entity.HttpResult;
import com.sun.mobileshop.http.service.MemberService;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2019/5/4 0004.
 */

public class HttpMethods {

    protected static final String TAG = "HttpMethods";
    protected static final String BASE_URL = Constants.BASE_URL;
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    //配置 获取实例
    private static HttpMethods mInstance;
    protected static MemberService memberService;


    //构造器
    public HttpMethods() {

        if (mInstance == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();

            //创建MemberService代理对象 获取实例
            memberService = retrofit.create(MemberService.class);
        }

    }


    //单例模式
    // 使用synchronized 进行同步处理，并且双重判断是否为null，
    // 我们看到synchronized (Singleton.class)里面又进行了是否为null的判断，
    // 这是因为一个线程进入了该代码，如果另一个线程在等待，
    // 这时候前一个线程创建了一个实例出来完毕后，另一个线程获得锁进入该同步代码，
    // 实例已经存在，没必要再次创建，因此这个判断是否是null还是必须的。

    public static HttpMethods getmInstance() {
        if (mInstance == null) {
            synchronized (HttpMethods.class) {
                if (mInstance == null) {
                    mInstance = new HttpMethods();
                }
            }
        }
        return mInstance;
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            Log.i(TAG, "status: " + httpResult.getStatus());
            Log.i(TAG, "msg: " + httpResult.getMsg());
            Log.i(TAG, "data: " + httpResult.getData());

            return httpResult.getData();
        }
    }

    //公共部分 切换线程
    public static <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                //被观察者的线程
                .subscribeOn(Schedulers.io())
                //观察者的线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}
