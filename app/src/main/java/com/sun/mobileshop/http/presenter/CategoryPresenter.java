package com.sun.mobileshop.http.presenter;

import com.nostra13.universalimageloader.utils.L;
import com.sun.mobileshop.entity.CategoryEntity;
import com.sun.mobileshop.http.HttpMethods;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2019/5/6 0006.
 */

public class CategoryPresenter extends HttpMethods {

    //获取一级分类列表
    public static void getTopList(Subscriber<List<CategoryEntity>> subscriber) {
        Observable<List<CategoryEntity>> observable = categoryService.getTopList()
                .map(new HttpResultFunc<List<CategoryEntity>>());
        toSubscribe(observable, subscriber);
    }

    //获取二级分类列表
    public static void getSecondList(Subscriber<List<CategoryEntity>> subscriber, int parentId) {
        Observable<List<CategoryEntity>> observable = categoryService.getSecondList(parentId)
                .map(new HttpResultFunc<List<CategoryEntity>>());
        toSubscribe(observable, subscriber);
    }

}
