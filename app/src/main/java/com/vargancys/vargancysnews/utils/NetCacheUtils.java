package com.vargancys.vargancysnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/23
 * version:1.0
 */
public class NetCacheUtils {
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;
    private final Handler handler;
    private ExecutorService service;
    private LocalCacheUtils localCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;
    public NetCacheUtils(Handler handler, LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils){
        this.handler = handler;
        this.localCacheUtils = localCacheUtils;
        this.memoryCacheUtils = memoryCacheUtils;
        service = Executors.newFixedThreadPool(10);
    }
    //联网请求得到图片
    public void getBitmapFromNet(String imageUrl,int position){

        service.execute(new MyRunnable(imageUrl,position));
    }

    class MyRunnable implements Runnable{
        private final String imageUrl;
        private final int position;
        public MyRunnable(String imageUrl,int position){
            this.imageUrl = imageUrl;
            this.position = position;
        }
        @Override
        public void run() {
            //请求网络图片
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(4000);
                connection.setReadTimeout(4000);
                connection.connect();
                int code = connection.getResponseCode();
                if(code == 200){
                    InputStream is = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Message msg =Message.obtain();
                    msg.what = SUCCESS;
                    msg.arg1 = position;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);

                    memoryCacheUtils.putBitmap(imageUrl,bitmap);
                    localCacheUtils.putBitmap(imageUrl,bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Message msg =Message.obtain();
                msg.what = ERROR;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        }
    }
}
