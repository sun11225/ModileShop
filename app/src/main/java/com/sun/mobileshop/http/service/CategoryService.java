package com.sun.mobileshop.http.service;

import com.sun.mobileshop.entity.CategoryEntity;
import com.sun.mobileshop.entity.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2019/5/6 0006.
 */

public interface CategoryService {

    //加载一级分类
    @GET("cat/show")
    Observable<HttpResult<List<CategoryEntity>>> getTopList();

    //加载二级分类
    @GET("cat/show/{parentId}")
    Observable<HttpResult<List<CategoryEntity>>> getSecondList(
        @Path("parentId") int parentId
    );
}
