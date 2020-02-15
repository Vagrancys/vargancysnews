package com.vargancys.vargancysnews.utils;

import android.content.Context;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/15
 * version:1.0
 */
public class DensityUtils {
    //根据手机的分辨率将dip 的单位 转换成 px (像素)
    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale +0.5f);
    }

    //根据手机的分辨率 将 px(像素) 的单位 转换成 dp
    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale +0.5f);
    }
}
