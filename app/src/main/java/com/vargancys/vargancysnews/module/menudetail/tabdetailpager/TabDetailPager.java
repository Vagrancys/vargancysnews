package com.vargancys.vargancysnews.module.menudetail.tabdetailpager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.MenuDetailBasePager;
import com.vargancys.vargancysnews.module.common.NewsDetailActivity;
import com.vargancys.vargancysnews.module.content.news.api.NewsDataInfo;
import com.vargancys.vargancysnews.module.menudetail.domain.TabDetailPagerBean;
import com.vargancys.vargancysnews.utils.CacheUtils;
import com.vargancys.vargancysnews.utils.Constants;
import com.vargancys.vargancysnews.utils.DensityUtils;
import com.vargancys.vargancysnews.view.HorizontalScrollViewPager;
import com.vargancys.vargancysnews.view.RefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/19
 * version:1.0
 */
public class TabDetailPager extends MenuDetailBasePager {
    public static final String READ_ARRAY_ID = "read_array_id";
    @BindView(R.id.view_pager)
    HorizontalScrollViewPager viewPager;
    @BindView(R.id.ll_point_group)
    LinearLayout llPointGroup;
    @BindView(R.id.list_view)
    RefreshListView ListView;
    @BindView(R.id.banner_title)
    TextView bannerTitle;
    private String moreUrl;
    private static final String TAG = "TabDetailPager";
    private final NewsDataInfo.News.Children children;
    private List<TabDetailPagerBean.DataEntity.Topnews> topnews;
    private TabDetailPagerListAdapter mAdapter;
    private List<TabDetailPagerBean.DataEntity.News> news;
    private String url;
    private boolean isLoadMore = false;
    private InternalHandler internalHandler;
    private boolean isDragging;

    public TabDetailPager(Context context,NewsDataInfo.News.Children children) {
        super(context);
        this.children = children;
    }

    @Override
    public int getLayoutId() {
        return R.layout.tabdetail_pager;
    }

    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URI + children.getUri();
        String saveJson = CacheUtils.getString(mContext,url);
        if(!TextUtils.isEmpty(saveJson)){
            //解析数据
            processData(saveJson);
        }else{
            getDataFromNet();
        }
    }

    private void getDataFromNet(){
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(4000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(mContext,url,result);
                Log.e(TAG,children.getTitle()+"页面数据请求成功=="+result);
                processData(result);
                ListView.onRefreshFinish(true);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG,children.getTitle()+"页面数据请求onCancelled=="+cex.getMessage());
                ListView.onRefreshFinish(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG,children.getTitle()+"页面数据请求失败=="+ex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e(TAG,children.getTitle()+"页面数据请求onFinished==");
            }
        });
    }

    @Override
    public void initView() {
        Log.e("tab",children.getTitle());
        Log.e("tab","url"+children.getUri());
        View topNewsView = View.inflate(mContext,R.layout.item_top_news,null);

        //ListView.addHeaderView(topNewsView);
        ListView.addTopHeaderView(topNewsView);

        ListView.setOnRefreshListener(new MyOnRefreshListener());
        ListView.setOnItemClickListener(new MyOnItemClickListener());
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int realPosition = position - 1;
            TabDetailPagerBean.DataEntity.News newsData = news.get(realPosition);
            //Toast.makeText(mContext,"id = "+newsData.getId(),Toast.LENGTH_SHORT).show();
            String idArray = CacheUtils.getString(mContext, READ_ARRAY_ID);
            if(!idArray.contains(newsData.getId()+"")){
                CacheUtils.putString(mContext,READ_ARRAY_ID,idArray+newsData.getId()+",");
                mAdapter.notifyDataSetChanged();//getCount() ->getView();
            }

            Intent intent = new Intent(mContext, NewsDetailActivity.class);
            intent.putExtra("url",Constants.BASE_URI+newsData.getUrl());
            mContext.startActivity(intent);
        }
    }

    class MyOnRefreshListener implements RefreshListView.OnRefreshListener{
        @Override
        public void onPullDownRefresh() {
            getDataFromNet();
        }

        @Override
        public void onLoadMore() {
            if(TextUtils.isEmpty(moreUrl)){
                Toast.makeText(mContext,"没有数据",Toast.LENGTH_SHORT).show();
                ListView.onMoreFinish();
            }else{
                getMoreDataFromNet();
            }

        }
    }

    private void getMoreDataFromNet() {
        RequestParams params = new RequestParams(moreUrl);
        params.setConnectTimeout(4000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG,children.getTitle()+"加载更多联网成功=="+result);
                isLoadMore = true;
                processData(result);
                ListView.onMoreFinish();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG,children.getTitle()+"加载更多联网onCancelled=="+cex.getMessage());
                ListView.onMoreFinish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG,children.getTitle()+"加载更多联网失败=="+ex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e(TAG,children.getTitle()+"加载更多联网onFinished==");
            }
        });
    }

    private void processData(String json){
        TabDetailPagerBean bean = parsedJson(json);
        Log.e(TAG,"解析成功=="+bean.getData().getNews().get(0).getTitle());
        moreUrl = bean.getData().getMore();
        if(TextUtils.isEmpty(moreUrl)){
            moreUrl = "";
        }else {
            moreUrl = Constants.BASE_URI + moreUrl;
        }

        if(!isLoadMore){
            topnews = bean.getData().getTopicnews();
            viewPager.setAdapter(new TabDetailPagerTopicNewsAdapter());

            addPoint();
            viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
            bannerTitle.setText(topnews.get(0).getTitle());

            news = bean.getData().getNews();
            mAdapter = new TabDetailPagerListAdapter();
            ListView.setAdapter(mAdapter);
        }else{
            isLoadMore = false;
            //List<TabDetailPagerBean.DataEntity.News> moreNews = bean.getData().getNews();
            news.addAll(bean.getData().getNews());
            mAdapter.notifyDataSetChanged();
        }

        if(internalHandler !=null){
            internalHandler = new InternalHandler();
        }

        //把消息队列所有的消息和回调移除
        internalHandler.removeCallbacksAndMessages(null);
        internalHandler.postDelayed(new MyRunnable(),4000);
    }

    class MyRunnable implements Runnable{
        @Override
        public void run() {
            internalHandler.sendEmptyMessageDelayed(0,4000);
        }
    }

    class TabDetailPagerListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(mContext,R.layout.item_tabdetail_pager,null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon =  convertView.findViewById(R.id.ic_icon);
                viewHolder.tv_title =  convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time =  convertView.findViewById(R.id.tv_time);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            TabDetailPagerBean.DataEntity.News newsData = news.get(position);
            String url = Constants.BASE_URI+newsData.getListimage();
            Glide.with(mContext).load(url).into(viewHolder.iv_icon);
            viewHolder.tv_title.setText(newsData.getTitle());
            viewHolder.tv_time.setText(newsData.getPubdate());
            String idArray = CacheUtils.getString(mContext,READ_ARRAY_ID);
            if(idArray.contains(newsData.getId()+"")){
                viewHolder.tv_title.setTextColor(Color.GRAY);
            }else{
                viewHolder.tv_title.setTextColor(Color.BLACK);
            }
            return convertView;
        }
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }

    private void addPoint(){
        llPointGroup.removeAllViews();
        for (int i = 0; i<topnews.size();i++){
            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundResource(R.drawable.point_enabled_selector);
            if(i== 0){
                imageView.setEnabled(true);
            }else{
                imageView.setEnabled(false);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(mContext,5),DensityUtils.dip2px(mContext,5));
            imageView.setLayoutParams(params);
            llPointGroup.addView(imageView);
        }
    }

    private int prePosition;

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrollStateChanged(int i) {
            if(i == ViewPager.SCROLL_STATE_DRAGGING){
                isDragging = true;
                internalHandler.removeCallbacksAndMessages(null);
            }else if(i == ViewPager.SCROLL_STATE_SETTLING){
                isDragging = false;
                internalHandler.removeCallbacksAndMessages(null);
                internalHandler.postDelayed(new MyRunnable(),4000);
            }else if(i == ViewPager.SCROLL_STATE_IDLE){
                isDragging = false;
                internalHandler.removeCallbacksAndMessages(null);
                internalHandler.postDelayed(new MyRunnable(),4000);
            }
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            bannerTitle.setText(topnews.get(i).getTitle());
            llPointGroup.getChildAt(prePosition).setEnabled(false);
            llPointGroup.getChildAt(i).setEnabled(true);
            prePosition = i;
        }
    }

    class TabDetailPagerTopicNewsAdapter extends PagerAdapter{
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundResource(R.drawable.black_background);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(mContext).load(Constants.BASE_URI+topnews.get(position).getTopimage())
            .into(imageView);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            internalHandler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            internalHandler.postDelayed(new MyRunnable(),4000);
                            break;
                    }
                    return true;
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            //super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }

    private TabDetailPagerBean parsedJson(String json){
        return new Gson().fromJson(json,TabDetailPagerBean.class);
    }

    class InternalHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = (viewPager.getCurrentItem()+1)%topnews.size();
            viewPager.setCurrentItem(item);
            internalHandler.postDelayed(new MyRunnable(),4000);
        }
    }
}
