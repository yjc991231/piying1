package com.example.a60929.piying.ui;

//启动界面

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a60929.piying.MainActivity;
import com.example.a60929.piying.R;
import com.example.a60929.piying.utils.ShareUtils;
import com.example.a60929.piying.utils.StaticClass;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {

    private TextView tv_splash;
    private ImageView iv_splash;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否为第一次运行
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }
                    else {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    //初始化view
    private void initView(){

        //开始界面延时时间
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        iv_splash=(ImageView) findViewById(R.id.iv_splash);
    }

    //判断是否第一次运行
    private boolean isFirst(){
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            //是第一次
            return true;
        }
        //不是第一次运行
        else {
            return false;
        }
    }

    //禁止返回键
    @Override
    public void onBackPressed(){
        //super.onBackPressed();
    }
}
