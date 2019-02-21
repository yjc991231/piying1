package com.example.a60929.piying.application;

import android.app.Application;

import com.example.a60929.piying.utils.StaticClass;

import cn.bmob.v3.Bmob;

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bmob
        Bmob.initialize(this,StaticClass.BMOB_APP_ID);
    }
}
