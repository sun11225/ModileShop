package com.sun.mobileshop.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sun.mobileshop.R;
import com.sun.mobileshop.fragment.NavigationFragment;

public class MainActivity extends AppCompatActivity {
    private NavigationFragment navigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //开启一个事务 添加碎片  添加主容器
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        navigationFragment = new NavigationFragment();
        transaction.add(R.id.main_frame, navigationFragment);
        transaction.commit();
    }
}
