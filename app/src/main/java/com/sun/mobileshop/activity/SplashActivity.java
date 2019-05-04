package com.sun.mobileshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sun.mobileshop.R;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG="SplashActivity";
    private ImageView mSplash_load_it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView(){
        //动画效果
        mSplash_load_it=(ImageView)findViewById(R.id.splash_loading_item);
        //加载动画
        Animation translate= AnimationUtils.loadAnimation(this,R.anim.splash_loading);
        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //结束时跳转
                Intent intent=new Intent(SplashActivity.this,AdActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //执行动画
        mSplash_load_it.setAnimation(translate);
    }
}
