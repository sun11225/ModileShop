package com.sun.mobileshop.http.presenter;

import com.sun.mobileshop.entity.MemberEntity;
import com.sun.mobileshop.http.HttpMethods;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2019/5/4 0004.
 * 业务类，进行网络请求
 */

public class MemberPresenter extends HttpMethods {
    //用户注册
    public static void register(Subscriber<MemberEntity> subscriber,String userName,String email,String password){
        Observable observable=memberService.register(userName,password,email)
                .map(new HttpResultFunc<MemberEntity>());
        toSubscribe(observable,subscriber);
    }

    //用户登录
    public static void login(Subscriber<MemberEntity> subscriber,String userName,String password){
        Observable observable=memberService.login(userName,password)
                .map(new HttpResultFunc<MemberEntity>());
        toSubscribe(observable,subscriber);
    }

}
