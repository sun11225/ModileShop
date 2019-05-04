package com.sun.mobileshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.sun.mobileshop.R;

/**
 * Created by Administrator on 2019/5/3 0003.
 */

public class NavigationFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout tabItemHome;
    private LinearLayout tabItemCategory;
    private LinearLayout tabItemCart;
    private LinearLayout tabItemPersonal;

    private ImageButton tabItemHomeBtn;
    private ImageButton tabItemCategoryBtn;
    private ImageButton tabItemCartBtn;
    private ImageButton tabItemPersonalBtn;

    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    public CartFragment cartFragment;
    private PersonalFragment personFragment;

    private FragmentManager fragmentManager;

    public int currentId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        //初始化
        initView(view);
        setTabSelection(R.id.tab_item_home);
        return view;
    }


    //初始化视图对象
    private void initView(View view) {

        //实例化 设置监听
        tabItemHome = (LinearLayout) view.findViewById(R.id.tab_item_home);
        tabItemHome.setOnClickListener(this);
        tabItemCategory = (LinearLayout) view.findViewById(R.id.tab_item_category);
        tabItemCategory.setOnClickListener(this);
        tabItemCart = (LinearLayout) view.findViewById(R.id.tab_item_cart);
        tabItemCart.setOnClickListener(this);
        tabItemPersonal = (LinearLayout) view.findViewById(R.id.tab_item_personal);
        tabItemPersonal.setOnClickListener(this);

        tabItemHomeBtn = (ImageButton) view.findViewById(R.id.tab_item_home_btn);
        tabItemCategoryBtn = (ImageButton) view.findViewById(R.id.tab_item_category_btn);
        tabItemCartBtn = (ImageButton) view.findViewById(R.id.tab_item_cart_btn);
        tabItemPersonalBtn = (ImageButton) view.findViewById(R.id.tab_item_personal_btn);

        //碎片管理类对象
        fragmentManager = getFragmentManager();


    }

    //点击事件
    @Override
    public void onClick(View v) {

        setTabSelection(v.getId());
    }

    //初始化设置tab选中
    public void setTabSelection(int id) {

        //重置所有tabItem状态
        tabItemHomeBtn.setImageResource(R.drawable.tab_item_home_focus);
        tabItemCategoryBtn.setImageResource(R.drawable.tab_item_category_focus);
        tabItemCartBtn.setImageResource(R.drawable.tab_item_cart_focus);
        tabItemPersonalBtn.setImageResource(R.drawable.tab_item_personal_focus);

        //动态转换碎片需要开启一个事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //隐藏所有Fragment
        if (homeFragment != null)
            fragmentTransaction.hide(homeFragment);
        if (categoryFragment != null)
            fragmentTransaction.hide(categoryFragment);
        if (cartFragment != null)
            fragmentTransaction.hide(cartFragment);
        if (personFragment != null)
            fragmentTransaction.hide(personFragment);

        //根据tanItem的id来执行操作
        switch (id) {
            case R.id.tab_item_home:
                tabItemHomeBtn.setImageResource(R.drawable.tab_item_home_normal);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                break;
            case R.id.tab_item_category:
                tabItemCategoryBtn.setImageResource(R.drawable.tab_item_category_normal);
                if (categoryFragment == null) {
                    categoryFragment = new CategoryFragment();
                    fragmentTransaction.add(R.id.content, categoryFragment);
                } else {
                    fragmentTransaction.show(categoryFragment);
                }
                break;
            case R.id.tab_item_cart:
                tabItemCartBtn.setImageResource(R.drawable.tab_item_cart_normal);
                if (cartFragment == null) {
                    cartFragment = new CartFragment();
                    fragmentTransaction.add(R.id.content, cartFragment);
                } else {
                    fragmentTransaction.show(cartFragment);
                }
                break;
            case R.id.tab_item_personal:
                tabItemPersonalBtn.setImageResource(R.drawable.tab_item_personal_normal);
                if (personFragment == null) {
                    personFragment = new PersonalFragment();
                    fragmentTransaction.add(R.id.content, personFragment);
                } else {
                    fragmentTransaction.show(personFragment);
                }
                break;
        }

        fragmentTransaction.commit();

        //记录当前点击的tab
        currentId=id;
    }

}

