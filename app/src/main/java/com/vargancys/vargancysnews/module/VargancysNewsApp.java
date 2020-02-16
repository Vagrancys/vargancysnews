package com.vargancys.vargancysnews.module;

import android.app.Application;

import org.xutils.x;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/16
 * version:1.0
 */
public class VargancysNewsApp extends Application {
    /**
     * 所有组件被创建之前执行
     */
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
