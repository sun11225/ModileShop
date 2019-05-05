package com.sun.mobileshop.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sun.mobileshop.R;
import com.sun.mobileshop.activity.ChangePWDActivity;
import com.sun.mobileshop.activity.LoginActivity;
import com.sun.mobileshop.activity.MainActivity;
import com.sun.mobileshop.activity.MyFavoriteActivity;
import com.sun.mobileshop.activity.MyOrderActivity;
import com.sun.mobileshop.common.ImageLoaderManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/5/3 0003.
 */

public class PersonalFragment extends BaseFragment {

    private final int MY_FAVORITE = 1;
    private final int MY_ORDER = 2;
    private final int MY_ADDRESS = 3;
    private final int MY_ACCOUNT_BEFORE = 4;
    private final int MY_ACCOUNT_AFTER = 5;


    private MainActivity mainActivity;
    private ProgressDialog progressDialog;

    //    获取实例
    //用户头像
    @BindView(R.id.user_img_view)
    ImageView userImgView;
    //用户名
    @BindView(R.id.user_name)
    TextView userName;
    //用户级别
    @BindView(R.id.user_level)
    TextView userLevel;
    //登录按钮
    @BindView(R.id.personal_login)
    Button personalLogin;
    //登录时的布局
    @BindView(R.id.personal_for_login)
    RelativeLayout personalForLogin;
    //未登录时的布局
    @BindView(R.id.personal_for_not_login)
    RelativeLayout personalForNotLogin;
    //我的订单
    @BindView(R.id.person_my_order)
    RelativeLayout personalMyOrder;
    //我的收藏
    @BindView(R.id.my_collect)
    RelativeLayout personalMyCollect;
    //收货地址
    @BindView(R.id.my_address)
    RelativeLayout personalMyAddress;
    //我的账户
    @BindView(R.id.my_account)
    RelativeLayout personalMyAccount;
    //退出登录
    @BindView(R.id.person_logout_layout)
    RelativeLayout personalLogoutLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);
        mainActivity=(MainActivity)getActivity();

        init();
        return view;
    }

    //监听
    @OnClick({R.id.personal_login,
            R.id.person_my_order,
            R.id.my_collect,
            R.id.my_address,
            R.id.my_account,
            R.id.person_logout_layout})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_login: //登录
                Intent intent=new Intent(mainActivity, LoginActivity.class);
                startActivity(intent);

                break;
            case R.id.person_my_order: //我的订单

                break;
            case R.id.my_collect:  //我的收藏

                break;
            case R.id.my_address: //收货地址

                break;
            case R.id.my_account: //修改密码
                startActivityForResult(new Intent(mainActivity, ChangePWDActivity.class), MY_ACCOUNT_AFTER);

                break;
            case R.id.person_logout_layout: //退出登录
                new AlertDialog.Builder(mainActivity)
                        .setTitle("退出登录")
                        .setMessage("您确定退出登录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //关闭窗口
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case MY_ORDER:
                if (resultCode== Activity.RESULT_OK&&data.getBooleanExtra("logined",false)){
                    startActivity(new Intent(mainActivity,MyOrderActivity.class));
                }
                break;
            case MY_FAVORITE:
                if (resultCode==Activity.RESULT_OK&&data.getBooleanExtra("logined",false)){
                    startActivity(new Intent(mainActivity, MyFavoriteActivity.class));
                }
                break;

            case MY_ADDRESS:
                break;

            case MY_ACCOUNT_BEFORE://未登录时修改密码，修改密码后登陆
                if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("logined", false)) {
                    Intent intent = new Intent(mainActivity, ChangePWDActivity.class);
                    startActivityForResult(intent, MY_ACCOUNT_AFTER);
                }
                break;
            case MY_ACCOUNT_AFTER://登录时修改密码，修改完毕登录
                if (resultCode == Activity.RESULT_OK) {
                    startActivity(new Intent(mainActivity, LoginActivity.class));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    //退出登录，清除本地用户信息
    private void logout(){
        SharedPreferences.Editor editor=mainActivity.getSharedPreferences("user",0).edit();
        editor.remove("username");
        editor.remove("face");
        editor.remove("level");
        editor.commit();
        init();
        Toast.makeText(mainActivity,"退出登录成功！",Toast.LENGTH_SHORT).show();
    }

    //根据登录信息显示不同的布局
    private void init() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", 0);
        String username = sharedPreferences.getString("uname", "");
        if (TextUtils.isEmpty(username)) {   //未登录
            personalForLogin.setVisibility(View.GONE);
            personalForNotLogin.setVisibility(View.VISIBLE);
            personalLogoutLayout.setVisibility(View.GONE);
        } else {   //已登录
            personalForLogin.setVisibility(View.VISIBLE);
            personalForNotLogin.setVisibility(View.GONE);
            personalLogoutLayout.setVisibility(View.VISIBLE);
            userName.setText(username);

            String image = sharedPreferences.getString("image", "");
            if (!TextUtils.isEmpty(image)) {
                ImageLoader.getInstance().displayImage(image, userImgView, ImageLoaderManager.user_options);
            }
        }
    }
}
