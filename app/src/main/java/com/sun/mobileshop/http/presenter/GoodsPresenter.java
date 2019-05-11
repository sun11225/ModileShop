package com.sun.mobileshop.http.presenter;

import com.sun.mobileshop.entity.GoodsEntity;
import com.sun.mobileshop.http.HttpMethods;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2019/5/10 0010.
 */

public class GoodsPresenter extends HttpMethods{

    //获取商品分类
    public static void list(Subscriber<List<GoodsEntity>> subscriber,int catId){
        Observable<List<GoodsEntity>> observable=goodsService.list(catId)
                .map(new HttpResultFunc<List<GoodsEntity>>());

        toSubscribe(observable,subscriber);

    }

    //根据搜索关键词获取搜索结果集

    public static void listByKeywords(Subscriber<List<GoodsEntity>> subscriber,String keywords){

        Observable<List<GoodsEntity>> observable=goodsService.listByKeywords(keywords)
                .map(new HttpResultFunc<List<GoodsEntity>>());

        toSubscribe(observable,subscriber);

    }
}
