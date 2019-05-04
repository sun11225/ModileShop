package com.sun.mobileshop.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/5/3 0003.
 * 把所有的json数据序列化成HttpResult<T>
 */

//  T 把泛型指定为抽象类型
public class HttpResult<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer();
        sb.append("status="+status+",msg="+msg);
        if (null!=data){
            sb.append(",data="+data.toString());
        }
        return sb.toString();
    }
}
