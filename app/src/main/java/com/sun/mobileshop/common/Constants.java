package com.sun.mobileshop.common;

/**
 * Created by Administrator on 2019/5/4 0004.
 * 恒定的数据
 */

public class Constants {
    /**
     *Base url
     */
    public static String BASE_URL="http://172.56.2.138:8080/MobileShop/";
    /**
     * 广告url
     */
    public static String AD_URL = BASE_URL + "";
    public static String API_KEY_FOR_MOB_SMS = "182489edf1060";
    public static String API_SECRET_FOR_MOB_SMS = "5955939eb23eb90c2baa227f87de43a0";

    /**
     * 广告显示时长 ，单位：ms
     */
    public static int AD_TIME_SECOND = 3000;

    /**
     * 列表页面右侧列表的列数
     */
    public static int SPAN_COUNT = 3;
}
