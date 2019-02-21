package com.example.a60929.piying.ui;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a60929.piying.R;
import com.example.a60929.piying.application.BaseApplication;
import com.example.a60929.piying.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisteredActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_pass;
    private EditText et_password;
    private EditText et_email;
    private Button btn_Registered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        initView();
    }

    private void initView(){
        et_user=(EditText)findViewById(R.id.et_user);
        et_pass=(EditText)findViewById(R.id.et_pass);
        et_password=(EditText)findViewById(R.id.et_password);
        et_email=(EditText)findViewById(R.id.et_email);
        btn_Registered=(Button)findViewById(R.id.btn_Registered);
        btn_Registered.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Registered:
                //获取到输入框的值
                String name=et_user.getText().toString().trim();
                String pass=et_pass.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                String email=et_email.getText().toString().trim();

                //判断是否为空
                if(!TextUtils.isEmpty(name)&!TextUtils.isEmpty(pass)&!TextUtils.isEmpty(password)
                        &!TextUtils.isEmpty(email))
                {
                    //判断两次密码是否一致
                    if(pass.equals(password)){
                        //注册
                        MyUser user =new MyUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setEmail(email);

                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisteredActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(RegisteredActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    else {
                        Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
