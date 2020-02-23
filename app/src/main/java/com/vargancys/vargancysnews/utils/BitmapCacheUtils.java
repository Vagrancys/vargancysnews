package com.vargancys.vargancysnews.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/23
 * version:1.0
 */
public class BitmapCacheUtils {
    private static final String TAG = "bitmapcacheutils";
    private Handler handler;
    private LocalCacheUtils localCacheUtils;
    /**
     * 网络保存工具类
     */
    private NetCacheUtils netCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;

    public BitmapCacheUtils(Handler handler){
        this.handler = handler;
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils();
        netCacheUtils = new NetCacheUtils(handler,localCacheUtils,memoryCacheUtils);
    }
    /**
     * 从内存中取图片
     * 从本地文件中取图片
     * 向内存保存一份
     * 请求网络图片
     * 向内存保存一份
     * 向本地文件保存一份
     * @param imageUrl
     * @return
     */

    public Bitmap getBitmap(String imageUrl,int position){
        if(memoryCacheUtils !=null){
            Bitmap bitmap = memoryCacheUtils.getBitmapFromUrl(imageUrl);
            if(bitmap !=null){
                Log.e(TAG,"内存加载图片成功....");
                return bitmap;
            }
        }
        if(localCacheUtils != null){
            Bitmap bitmap = localCacheUtils.getBitmapFromUrl(imageUrl);
            if(bitmap !=null){
                Log.e(TAG,"本地加载图片成功....");
                return bitmap;
            }
        }
        netCacheUtils.getBitmapFromNet(imageUrl,position);
        return null;
    }

}
