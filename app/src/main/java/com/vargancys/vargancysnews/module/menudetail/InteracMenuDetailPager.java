package com.vargancys.vargancysnews.module.menudetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.MenuDetailBasePager;
import com.vargancys.vargancysnews.module.content.news.api.NewsDataInfo;
import com.vargancys.vargancysnews.module.menudetail.domain.PhotoMenuDetailBean;
import com.vargancys.vargancysnews.utils.BitmapCacheUtils;
import com.vargancys.vargancysnews.utils.CacheUtils;
import com.vargancys.vargancysnews.utils.Constants;
import com.vargancys.vargancysnews.utils.NetCacheUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/17
 * version:1.0
 */
public class InteracMenuDetailPager extends MenuDetailBasePager {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.photo_menu)
    ImageView photoMenu;

    private NewsDataInfo.News News;
    private String url;
    private String TAG = "InteracMenuDetailPager";
    private List<PhotoMenuDetailBean.Data.News> news;
    private PhotoMenuDetailPagerAdapter mAdapter;

    public InteracMenuDetailPager(Context context, NewsDataInfo.News news){
        super(context);
        News = news;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case NetCacheUtils.SUCCESS:
                    int position = msg.arg1;
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if(listView.isShown()){
                        ImageView imageView =(ImageView) listView.findViewWithTag(position);
                        if(imageView !=null&&bitmap !=null){
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                    if(gridView.isShown()){
                        ImageView imageView =(ImageView) gridView.findViewWithTag(position);
                        if(imageView !=null&&bitmap !=null){
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                    Log.e(TAG,"请求成功.."+position);
                    break;
                case NetCacheUtils.ERROR:
                    position =msg.arg1;
                    Log.e(TAG,"请求失败....");
                    break;
            }
        }
    };
    private BitmapCacheUtils bitmapCacheUtils;

    @Override
    public int getLayoutId() {
        return R.layout.photo_menudetail_pager;
    }

    private boolean isShowListView =true;
    @Override
    public void initView() {
        photoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowListView){
                    isShowListView = false;
                    mAdapter = new PhotoMenuDetailPagerAdapter();
                    gridView.setAdapter(mAdapter);
                    gridView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    photoMenu.setImageResource(R.drawable.photo_grid_normal);
                }else{
                    isShowListView = true;
                    mAdapter = new PhotoMenuDetailPagerAdapter();
                    listView.setAdapter(mAdapter);
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    photoMenu.setImageResource(R.drawable.photo_list_normal);
                }
            }
        });
        bitmapCacheUtils = new BitmapCacheUtils(handler);
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("news","图片");
        url = Constants.BASE_URI + News.getUrl();
        String saveJson = CacheUtils.getString(mContext,url);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getDataFromNet();

    }

    private void getDataFromNet(){
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onFinished() {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(mContext,url,result);
                processData(result);
            }
        });
    }

    private void processData(String result) {
        PhotoMenuDetailBean bean = parsedJson(result);
        Log.e(TAG,"互动解析成功=="+bean.getData().getNews().get(0).getTitle());
        news = bean.getData().getNews();
        mAdapter = new PhotoMenuDetailPagerAdapter();
        listView.setAdapter(mAdapter);
    }

    class PhotoMenuDetailPagerAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PhotoMenuDetailPager.ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(mContext,R.layout.item_photo_menudetail_pager,null);
                viewHolder = new PhotoMenuDetailPager.ViewHolder();
                viewHolder.photoImage =(ImageView) convertView.findViewById(R.id.photo_img);
                viewHolder.photoTitle =(TextView) convertView.findViewById(R.id.photo_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (PhotoMenuDetailPager.ViewHolder) convertView.getTag();
            }
            PhotoMenuDetailBean.Data.News newsData= news.get(position);
            viewHolder.photoTitle.setText(newsData.getTitle());
            //Glide.with(mContext).load(newsData.getListimage())
             //       .into(viewHolder.photoImage);
            viewHolder.photoImage.setTag(position);
            Bitmap bitmap = bitmapCacheUtils.getBitmap(newsData.getListimage(),position);
            if(bitmap !=null){
                viewHolder.photoImage.setImageBitmap(bitmap);
            }
            return convertView;
        }
    }

    static class ViewHolder{
        ImageView photoImage;
        TextView photoTitle;
    }

    private PhotoMenuDetailBean parsedJson(String result) {
        return new Gson().fromJson(result,PhotoMenuDetailBean.class);
    }
}
