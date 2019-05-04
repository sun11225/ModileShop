package com.sun.mobileshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Administrator on 2019/5/4 0004.
 */

public class MyWebView extends WebView {

    //自定义接口
    private IWebViewScroll iWebViewScroll;

    public MyWebView(Context context){
        super(context,null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (iWebViewScroll!=null&&t==0){
            iWebViewScroll.onTop();
        }else if (iWebViewScroll!=null&&t!=0){
            iWebViewScroll.notOnTop();
        }
    }


    //滑动监听
    public void setOnCustomScrollChanged(IWebViewScroll listener){
        this.iWebViewScroll=listener;
    }

    public interface IWebViewScroll{
        void onTop();
        void notOnTop();
    }
}
