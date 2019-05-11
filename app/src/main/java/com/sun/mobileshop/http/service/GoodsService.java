package com.sun.mobileshop.http.service;

import com.sun.mobileshop.entity.GoodsEntity;
import com.sun.mobileshop.entity.HttpResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2019/5/10 0010.
 */

// HttpResult类把所有的json数据序列化成HttpResult<T>

public interface GoodsService {

    //获取商品列表
    @GET("goods/cat/{catId}")
    Observable<HttpResult<List<GoodsEntity>>> list(
            @Path("catId") int catId
    );

    //根据搜索关键词获取搜索结果集
    @POST("good/find")
    Observable<HttpResult<List<GoodsEntity>>> listByKeywords(
            @Field("input") String keywords
    );
}
