package com.example.a60929.piying.utils;

//工具统一类

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UtilTools {

    //保存图片到ShareUtils
    public static void putImageToShare(Context mcontext,ImageView imageView){
        //保存
        BitmapDrawable drawable=(BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        //1.将bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //2.利用Base64将我们的字节数组输入流转化成String
        byte[] byteArray=byStream.toByteArray();
        String imgString=new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //3.将String保存shareUtils
        ShareUtils.putString(mcontext,"image_title",imgString);
    }

    //读取新的头像的图片
    public static void getImageToShare(Context mcontext,ImageView imageView){
        //1.拿到图片转化出的string
        String imgString=ShareUtils.getString(mcontext,"image_title","");
        if(!imgString.equals("")){
            //2.利用Base64将我们的String转换
            byte [] byteArray= Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream byStream=new ByteArrayInputStream(byteArray);
            //3.生成bitmap  使用BitmapFactory可以将读取流，文件，byte 数组转换成Bitmap对象
            Bitmap bitmap=BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);

        }
    }
}
