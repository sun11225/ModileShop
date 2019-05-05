package com.sun.mobileshop.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by Administrator on 2019/5/4 0004.
 * 封装一个有加载进度的Subscriber
 */

//abstract抽象化
public abstract class ProgressDialogSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private ProgressDialog dialog;

    public ProgressDialogSubscriber(Context context){
        this.mContext=context;
    }

    @Override
    public void onCompleted() {

        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException){
            Toast.makeText(mContext,"服务器响应的超时!",Toast.LENGTH_SHORT).show();
        }else if (e instanceof ConnectException){
            Toast.makeText(mContext,"服务器请求超时!",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext,"error: "+e.getMessage(),Toast.LENGTH_SHORT).show();

            System.out.print("error: "+e.getMessage());
        }
        dismissProgressDialog();
    }

    @Override
    public void onStart() {
        showProgressDialog();

    }

    private void showProgressDialog(){
        if (dialog==null){
            dialog=new ProgressDialog(mContext);
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //取消订阅，取消请求
                    ProgressDialogSubscriber.this.unsubscribe();
                }
            });
        }
        if (dialog!=null&&dialog.isShowing()){
            dialog.show();
        }
    }

    private void dismissProgressDialog(){
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
            dialog=null;
        }
    }

}
