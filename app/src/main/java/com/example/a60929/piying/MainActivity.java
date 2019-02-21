package com.example.a60929.piying;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.a60929.piying.fragment.ChatFragment;
import com.example.a60929.piying.fragment.GameFragment;
import com.example.a60929.piying.fragment.HomeFragment;
import com.example.a60929.piying.fragment.UserFragment;
import com.example.a60929.piying.utils.ShareUtils;
import com.example.a60929.piying.utils.StaticClass;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    //标题
    private List<String >mTitle;
    //碎片
    private List<Fragment>mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉阴影
        getSupportActionBar().setElevation(0);

        Bmob.initialize(this, StaticClass.BMOB_APP_ID);


        initData();
        initView();

    }

    //初始化数据
    private void initData(){
        //标题初始化
        mTitle=new ArrayList<>();
        mTitle.add("首页");
        mTitle.add("演绎圈");
        mTitle.add("娱乐");
        mTitle.add("我的");

        //碎片初始化
        mFragment=new ArrayList<>();
        mFragment.add(new HomeFragment());
        mFragment.add(new ChatFragment());
        mFragment.add(new GameFragment());
        mFragment.add(new UserFragment());

    }

    //初始化view
    private void initView(){
       mTabLayout=(TabLayout)findViewById(R.id.mTabLayout);
       mViewPager=(ViewPager)findViewById(R.id.mViewPager);

       //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //设置是适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item个数
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回的item个数
            @Override
            public int getCount() {
                return mFragment.size();       //获取个数（List长度）
            }

            //设置标题
            public CharSequence getPageTitle(int position){
                return mTitle.get(position);
            }
        });

        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
