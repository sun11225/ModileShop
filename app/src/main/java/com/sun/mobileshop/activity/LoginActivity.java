package com.sun.mobileshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.mobileshop.R;
import com.sun.mobileshop.entity.MemberEntity;
import com.sun.mobileshop.http.ProgressDialogSubscriber;
import com.sun.mobileshop.http.presenter.MemberPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class LoginActivity extends AppCompatActivity {

    //返回按钮
    @BindView(R.id.title_back)
    ImageView titleBack;
    //用户名
    @BindView(R.id.login_input_name)
    EditText loginInputName;
    //密码
    @BindView(R.id.login_input_password)
    EditText loginInputPassword;
    //登录按钮
    @BindView(R.id.login_button)
    Button loginButton;
    //快速注册
    @BindView(R.id.register_link)
    TextView registerLink;
    //找回密码
    @BindView(R.id.find_password_text)
    TextView findPasswordText;

    private final int REQUEST_CODE_REGISTER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.login_button,R.id.title_back,R.id.find_password_text,R.id.register_link})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.title_back: //返回
                finish();
                break;
            case R.id.login_button://登录
                login();
                break;
            case R.id.find_password_text://找回密码
                startActivity(new Intent(LoginActivity.this, FindPWDActivity.class));
                break;
            case R.id.register_link://快速注册
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REQUEST_CODE_REGISTER);
                break;
        }

    }

    private void login(){

        String password=loginInputPassword.getText().toString().trim();
        String username=loginInputName.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            Toast.makeText(LoginActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
        }

        //联网请求
        MemberPresenter.login(new ProgressDialogSubscriber<MemberEntity>(this) {
            @Override
            public void onNext(MemberEntity memberEntity) {
                //登录成功后返回用户信息
                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                //使用SharedPreferences将用户信息保存到本地
                SharedPreferences.Editor editor=getSharedPreferences("user",0).edit();
                editor.putInt("member_id",memberEntity.getMember_id());
                editor.putString("uname",memberEntity.getuName());
                editor.putString("email",memberEntity.getEmail());
                editor.putString("image",memberEntity.getImage());
                editor.commit();

                //登陆成功后返回到之前的页面，并且传回数据
                Intent intent=new Intent();
                intent.putExtra("logined",true);
                setResult(RESULT_OK,intent);
                finish();
            }
        },username,password);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK&&requestCode==REQUEST_CODE_REGISTER){
            Intent intent=new Intent();
            intent.putExtra("logined",true);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
