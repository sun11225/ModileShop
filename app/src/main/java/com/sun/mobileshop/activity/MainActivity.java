package com.sun.mobileshop.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.sun.mobileshop.R;
import com.sun.mobileshop.fragment.NavigationFragment;

public class MainActivity extends BaseActivity {
    private NavigationFragment navigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //开启一个事务 添加碎片
        navigationFragment = new NavigationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_frame, navigationFragment);
        transaction.commit();
    }
}
