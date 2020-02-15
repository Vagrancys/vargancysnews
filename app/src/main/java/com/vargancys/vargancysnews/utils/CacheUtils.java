package com.vargancys.vargancysnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/14
 * version:1.0
 */
public class CacheUtils {
    /**
     * 得到缓存值
     * @param context 上下文
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("vagrancy",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences("vagrancy",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
}
