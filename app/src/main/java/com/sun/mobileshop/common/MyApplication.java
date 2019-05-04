package com.sun.mobileshop.common;

import android.app.Application;
import android.content.Context;

import com.sun.mobileshop.db.GreenDaoManager;
import com.sun.mobileshop.http.HttpMethods;

/**
 * Created by Administrator on 2019/5/3 0003.
 */

public class MyApplication extends Application {
    public static Context mContext;

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //应用到全局配置 获取实例
        GreenDaoManager.getmInstance();
        HttpMethods.getmInstance();
        ImageLoaderManager.getmInstance();
    }
}
