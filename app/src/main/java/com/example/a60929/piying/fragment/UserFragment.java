package com.example.a60929.piying.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a60929.piying.R;
import com.example.a60929.piying.entity.MyUser;
import com.example.a60929.piying.ui.LoginActivity;
import com.example.a60929.piying.utils.ShareUtils;
import com.example.a60929.piying.utils.UtilTools;
import com.example.a60929.piying.view.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_jianjie;
    private Button btn_update_ok;
    //圆形头像
    private CircleImageView touxiang;
    //点击头像后弹出的提示框
    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savdInstanceState){
        View view = inflater.inflate(R.layout.fragment_user,null);
        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view){
        btn_exit_user=(Button)view.findViewById(R.id. btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user=(TextView)view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);

        et_username=view.findViewById(R.id.et_username);
        et_age=view.findViewById(R.id.et_age);
        et_sex=view.findViewById(R.id.et_sex);
        et_jianjie=view.findViewById(R.id.et_jianjie);

        btn_update_ok=view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        touxiang=(CircleImageView)view.findViewById(R.id.touxiang);
        touxiang.setOnClickListener(this);

        //1.拿到图片转化出的string
        String imgString=ShareUtils.getString(getActivity(),"image_title","");
        if(!imgString.equals("")){
            //2.利用Base64将我们的String转换
            byte [] byteArray= Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream byStream=new ByteArrayInputStream(byteArray);
            //3.生成bitmap  使用BitmapFactory可以将读取流，文件，byte 数组转换成Bitmap对象
            Bitmap bitmap=BitmapFactory.decodeStream(byStream);
            touxiang.setImageBitmap(bitmap);

        }


        //初始化dialog
        dialog =new CustomDialog(getActivity(),100,100,
                R.layout.dialog_photo,R.style.Theme_dialog,Gravity.BOTTOM,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        btn_camera=(Button)dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture=(Button)dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel=(Button)dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        //默认不可点击，当点击编辑资料时，才可以点击。
        setEnabled(false);

        //设置具体的值
        MyUser userInfo=BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_sex.setText(userInfo.isSex()?"男":"女");
        et_age.setText(userInfo.getAge()+"");
        et_jianjie.setText(userInfo.getJianjie());

    }

    //控制焦点
    private void setEnabled(boolean is){
        et_username.setEnabled(is);
        et_age.setEnabled(is);
        et_sex.setEnabled(is);
        et_jianjie.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //退出登录
            case R.id.btn_exit_user:
                //清除缓存用户对象
                MyUser.logOut();
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
                break;
            //编辑个人资料
            case R.id.edit_user:
                setEnabled(true);   //可点击输入框
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //拿到输入框的值
                String username=et_username.getText().toString();
                String age=et_age.getText().toString();
                String sex=et_sex.getText().toString();
                String jianjie=et_jianjie.getText().toString();

                //判断是否为空
                if(!TextUtils.isEmpty(username)&!TextUtils.isEmpty(age)&!TextUtils.isEmpty(sex)){
                      //更新属性
                    MyUser user=new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    if(sex.equals("男")){
                        user.setSex(true);
                    }
                    else {
                        user.setSex(false);
                    }
                    //简介
                    if(!TextUtils.isEmpty(jianjie)){
                        user.setJianjie(jianjie);
                    }
                    else {
                        user.setJianjie("这个人很懒，什么都没有留下");
                    }

                    BmobUser bmobUser=BmobUser.getCurrentUser(BmobUser.class);
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.touxiang:
                dialog.show();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }

    private static final String PHOTO_IMAGE_FILE_NAME="fileImg.jpg";
    private static final int CAMERA_REQUEST_CODE=100;
    private static final int IMAGE_REQUEST_CODE=101;
    private static final int RESULT_REQUEST_CODE=102;

    private File tempFile =null;


    //跳转相机
    private void toCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用,可用的话进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转相册
    private void toPicture() {
        Intent intent=new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    startActivityForResult(intent,IMAGE_REQUEST_CODE);
    dialog.dismiss();
    }

    //获得返回值，相册中的图片
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=0){
            switch (requestCode){
                //相册的数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    tempFile=new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }

        }
    }

    //裁剪图片
    private void startPhotoZoom(Uri uri){
        if(uri == null){
            return;
        }
        Intent intent=new Intent("com.android.camera.action.CROP"); //裁剪
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop","true");
        //裁剪宽高
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁剪图片质量
        intent.putExtra("outputX",320);
        intent.putExtra("outputY",320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent,RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            touxiang.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存头像图片
         UtilTools.putImageToShare(getActivity(),touxiang);
    }

    //读取新的头像图片的方法 封装到UtilTools中了
}
