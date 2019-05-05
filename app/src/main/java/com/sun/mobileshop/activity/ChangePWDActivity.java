package com.sun.mobileshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sun.mobileshop.R;
import com.sun.mobileshop.entity.HttpResult;
import com.sun.mobileshop.http.ProgressDialogSubscriber;
import com.sun.mobileshop.http.presenter.MemberPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class ChangePWDActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.password_input_oldpass)
    EditText passwordInputOldpass;
    @BindView(R.id.password_input_newpass)
    EditText passwordInputNewpass;
    @BindView(R.id.password_input_repass)
    EditText passwordInputRepass;
    @BindView(R.id.change_button)
    Button changeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.title_back, R.id.change_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.change_button:
                changePassword();
                break;
        }
    }

    private void changePassword(){

        String old_password = passwordInputOldpass.getText().toString().trim();
        String new_password = passwordInputNewpass.getText().toString().trim();
        String new_rePassword = passwordInputRepass.getText().toString().trim();

        //本地检验
        checkPassword(old_password,new_password,new_rePassword);

        String member_id = getSharedPreferences("user", 0).getString("member_id", "");
        if (TextUtils.isEmpty(member_id)) {
            Toast.makeText(this, "取出本地用户id失败，请重新登录！", Toast.LENGTH_SHORT).show();
            return;
        }

        //修改密码

        MemberPresenter.changePassword(new ProgressDialogSubscriber<HttpResult>(this) {
            @Override
            public void onNext(HttpResult httpResult) {
                if (httpResult.getStatus()==0){//请求成功
                    Toast.makeText(ChangePWDActivity.this, "修改密码成功,请您重新登录！", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor=getSharedPreferences("user",0).edit();
                    editor.remove("member_id");
                    editor.remove("uname");
                    editor.remove("email");
                    editor.remove("image");
                    editor.commit();

                    //返回到PersonFragment页面重新登录
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("changepass", true);
                    setResult(RESULT_OK, returnIntent);
                    finish();

                }
            }
        },old_password,new_password,new_rePassword);

    }

    private void checkPassword(String old_password, String new_password, String new_rePassword) {
        if (TextUtils.isEmpty(old_password) || TextUtils.isEmpty(new_password) || TextUtils.isEmpty(new_rePassword)) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!new_password.equals(new_rePassword)) {
            Toast.makeText(this, "两次输入的新密码不一致！", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
