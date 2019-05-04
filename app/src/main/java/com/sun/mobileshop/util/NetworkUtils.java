package com.sun.mobileshop.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2019/5/4 0004.
 * 判断网络状态
 */

public class NetworkUtils {
    public static boolean isNetWorkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        //获取手机所有连接管理对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            //获取NotworkInfo对象
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null && networkInfos.length > 0) {
                for (int i = 0; i < networkInfos.length; i++) {
                    System.out.println(i + "===状态===" + networkInfos[i].getState());
                    System.out.println(i + "===类型===" + networkInfos[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
