package com.example.a60929.piying.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a60929.piying.MainActivity;
import com.example.a60929.piying.R;

import java.util.ArrayList;
import java.util.List;


//引导页
public class GuideActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager mViewPager;
    //容器，装载多个引导界面
    private List<View>mList=new ArrayList<>();
    private View view1,view2,view3;
    //小圆点
    private ImageView point1,point2,point3;
    //跳过
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();

    }

    //初始化view
    private void initView(){
        mViewPager=(ViewPager)findViewById(R.id.mViewPager);

        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);


        point1=(ImageView)findViewById(R.id.point1);
        point2=(ImageView)findViewById(R.id.point2);
        point3=(ImageView)findViewById(R.id.point3);

        //设置小圆点默认图片
        setPointimg(true,false,false);


        view1=View.inflate(this,R.layout.page_item_one,null);
        view2=View.inflate(this,R.layout.page_item_two,null);
        view3=View.inflate(this,R.layout.page_item_three,null);

        view3.findViewById(R.id.btn_start).setOnClickListener(this);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //适配器
        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager的滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {

            }

            //page 切换
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setPointimg(true,false,false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointimg(false,true,false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointimg(false,false,true);
                        iv_back.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //跳过按钮和进入主页按钮
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_start:
            case R.id.iv_back:
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }
    }

    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view,  Object object   ) {
            return view == object ;               //对比是否滑动切换
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);     //第一个滑到第二个，增加
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(mList.get(position));  //第二个滑到第一个，删除
           // super.destroyItem(container, position, object);
        }
    }

    //设置小圆点的选中效果
    private void setPointimg(boolean isCheck1,boolean isCheck2,boolean isCheck3){
        if(isCheck1){
            point1.setBackgroundResource(R.drawable.point_on);
        }
        else {
            point1.setBackgroundResource(R.drawable.point_off);
        }

        if(isCheck2){
            point2.setBackgroundResource(R.drawable.point_on);
        }
        else {
            point2.setBackgroundResource(R.drawable.point_off);
        }

        if(isCheck3){
            point3.setBackgroundResource(R.drawable.point_on);
        }
        else {
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }
}
