package com.sun.mobileshop.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.sun.mobileshop.R;
import com.sun.mobileshop.activity.MainActivity;
import com.sun.mobileshop.util.NetworkUtils;
import com.sun.mobileshop.view.MyWebView;

/**
 * Created by Administrator on 2019/5/3 0003.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG ="HomeFragment";
    public HomeFragment(){}
    private MainActivity mainActivity;
    private final int SEARCH_ACTIVITY=1;
    private MyWebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新布局
    private Button testBt;
    private TextView searchTV;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);

        mainActivity=(MainActivity)getActivity();
        //搜索
        searchTV=(TextView)view.findViewById(R.id.home_search);
        searchTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainActivity,"等待开发",Toast.LENGTH_SHORT).show();
            }
        });

        //下拉刷新
        mWebView=(MyWebView)view.findViewById(R.id.webView);
        mSwipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);

        initMyWebView(view);
        initSwipeRefreshLayout();
        return view;
    }



    @SuppressLint("JavascriptInterface")
    private void initMyWebView(View view) {
        mWebView.addJavascriptInterface(this, "app");//添加javascript接口监听和接口名称
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);


        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//启动JS脚本
        settings.setSupportZoom(false);//支持缩放
        settings.setBuiltInZoomControls(false);//启用内置缩放装置

        //对webview是否处于顶部进行监听，解决webview往下拉后无法往上拉的冲突（和SwipeRefreshLayout冲突）
        mWebView.setOnCustomScrollChanged(new MyWebView.IWebViewScroll() {
            @Override
            public void onTop() {
                mSwipeRefreshLayout.setEnabled(true);
            }

            @Override
            public void notOnTop() {
                mSwipeRefreshLayout.setEnabled(false);
            }
        });

        //点击后退按钮，让WebView后退
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }

                return false;
            }
        });

        //页面加载处理
        //点击链接时希望覆盖而不是打开浏览器窗口
//        mWebView.setWebViewClient(new WebViewClient());
//
//        mWebView.loadUrl("https://www.jd.com/?cu=true&utm_source=baidu-pinzhuan&utm_medium=cpc&utm_campaign=t_288551095_baidupinzhuan&utm_term=0f3d30c8dba7459bb52f2eb5eba8ac7d_0_e01829678c98415db22bfb876f376877");

        mWebView.setWebViewClient(new WebViewClient() {
            // 当点击链接时,希望覆盖而不是打开浏览器窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e(TAG, "onReceivedError");
                //用javascript隐藏系统定义的404页面信息
                mWebView.loadUrl("file:///android_asset/error.html");
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mSwipeRefreshLayout.setRefreshing(true);
                Log.e(TAG, "onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onPageFinished");
            }
        });
        //加载Url
        mWebView.loadUrl("http://www.apple.com/cn-k12/shop");
    }




    //设置下拉刷新样式和监听
    private void initSwipeRefreshLayout(){
        //下拉刷新的颜色样式
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //有网络才刷新
                if (NetworkUtils.isNetWorkAvailable(mainActivity)){
                    //重新加载
                    mWebView.reload();
                }else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(mainActivity,"网络不可用",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
