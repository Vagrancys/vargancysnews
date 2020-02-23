package com.vargancys.vargancysnews.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/23
 * version:1.0
 */
public class MemoryCacheUtils {
    private LruCache<String, Bitmap> lruCache;

    public MemoryCacheUtils(){
        int maxSize =(int) Runtime.getRuntime().maxMemory()/1024 / 8;
        lruCache = new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()* value.getHeight() /1024;
            }
        };
    }


    public Bitmap getBitmapFromUrl(String imageUrl) {
        return lruCache.get(imageUrl);
    }

    public void putBitmap(String imageUrl, Bitmap bitmap) {
        lruCache.put(imageUrl,bitmap);
    }
}
