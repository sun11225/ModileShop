package com.sun.mobileshop.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sun.mobileshop.R;
import com.sun.mobileshop.fragment.NavigationFragment;

/**
 * 设置当前Activity的启动模式为SingleTask.
 * 当通过Intent来启动的时候，不会再调用onCreate,替代的是onNewIntent
 */

public class MainActivity extends AppCompatActivity {
    private NavigationFragment navigationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //测试API
        //startActivity(new Intent(this, APITestActivity.class));

        //开启一个事务 添加碎片  添加主容器
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        navigationFragment = new NavigationFragment();
        transaction.add(R.id.main_frame, navigationFragment);
        transaction.commit();
    }
}
