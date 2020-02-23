package com.vargancys.vargancysnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import org.xutils.common.util.MD5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/23
 * version:1.0
 */
public class LocalCacheUtils {
    //根据url获取图片
    public Bitmap getBitmapFromUrl(String imageUrl){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                String fileName = MD5.md5(imageUrl);
                File file = new File(Environment.getExternalStorageDirectory()+"/beijingnews",fileName);
                if(file.exists()){
                    FileInputStream is = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    return bitmap;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("local","获取失败");
            }
        }
        return null;
    }


    //根据url保存图片
    public void putBitmap(String imageUrl, Bitmap bitmap) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                String fileName = MD5.md5(imageUrl);
                File file = new File(Environment.getExternalStorageDirectory()+"/beijingnews",fileName);
                File parentFile = file.getParentFile();
                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }
                if(!file.exists()){
                    file.createNewFile();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(file));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("local","保存失败");
            }
        }

    }
}
