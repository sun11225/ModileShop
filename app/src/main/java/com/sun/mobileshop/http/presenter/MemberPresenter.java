package com.sun.mobileshop.http.presenter;

import com.sun.mobileshop.entity.HttpResult;
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

    //修改密码
    public static void changePassword(Subscriber subscriber, String memberId, String old_pwd, String new_pwd){
        Observable observable=memberService.changePassword(memberId,old_pwd,new_pwd);
        toSubscribe(observable,subscriber);
    }

    //找回密码,修改成功或失败后的data数据为null
    public static void findPassword(Subscriber subscriber, String email) {
        Observable observable = memberService.findPassword(email);
        toSubscribe(observable, subscriber);
    }

}
