package com.example.a60929.piying.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a60929.piying.MainActivity;
import com.example.a60929.piying.R;
import com.example.a60929.piying.entity.MyUser;
import com.example.a60929.piying.utils.ShareUtils;
import com.example.a60929.piying.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //注册按钮
    private Button btn_registered;
    private EditText et_name;
    private EditText et_password;
    private Button btn_login;
    private CheckBox keep_password;

    private TextView tv_forget;

    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    //初始化view
    private void initView(){

        btn_registered = (Button)findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        et_name=(EditText)findViewById(R.id.et_name);
        et_password=(EditText)findViewById(R.id.et_password);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        keep_password=(CheckBox)findViewById(R.id.keep_password);

        tv_forget=(TextView)findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        dialog =new CustomDialog(this,100,100,
                R.layout.dialog_loding,R.style.Theme_dialog,Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        //设置选中的状态
        boolean isCheck = ShareUtils.getBoolean(this, "keeppass", false);
        keep_password.setChecked(isCheck);
        if(isCheck){
            //设置密码
            et_name.setText(ShareUtils.getString(this,"name",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }
    }



    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_registered:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
            case R.id.btn_login:
                //获取输入框的值
                String name=et_name.getText().toString().trim();
                String password=et_password.getText().toString();
                //判断输入框是否为空（TextUtils当是空时返回true)
                if(!TextUtils.isEmpty(name)&!TextUtils.isEmpty(password)){
                    dialog.show();
                    //登录
                    final MyUser user=new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<Object>() {
                        @Override
                        public void done(Object o, BmobException e) {
                            dialog.dismiss();
                            if(e==null){
                                //判断是否验证邮箱
                                if (user.getEmailVerified()){
                                    //跳转到主界面，即登录成功
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this,"请前往邮箱里验证",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(LoginActivity.this,"登录失败"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
        }
    }

    //假设我现在输入用户名和密码，但是我不点击登录，而是直接退出了
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        ShareUtils.putBoolean(this, "keeppass", keep_password.isChecked());

        //是否记住密码
        if (keep_password.isChecked()) {
            //记住用户名和密码
            ShareUtils.putString(this, "name", et_name.getText().toString().trim());
            ShareUtils.putString(this, "password", et_password.getText().toString().trim());
        } else {
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }
    }
}

