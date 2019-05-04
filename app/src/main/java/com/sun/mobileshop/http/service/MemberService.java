package com.sun.mobileshop.http.service;

import com.sun.mobileshop.entity.HttpResult;
import com.sun.mobileshop.entity.MemberEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2019/5/4 0004.
 * 处理与用户操作相关的网络请求
 */

public interface MemberService {
    //用户注册
    @FormUrlEncoded
    @POST("member")
    Observable<HttpResult<MemberEntity>> register(
        @Field("uname") String name,
        @Field("password") String password,
        @Field("email") String email);

    //登录
    @FormUrlEncoded
    @POST("member/login")//url后缀
    Observable<HttpResult<MemberEntity>> login(
            @Field("uname") String name,
            @Field("password") String password);

    }

