package com.vargancys.vargancysnews.module.menudetail.tabdetailpager;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.MenuDetailBasePager;
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
    @BindView(R.id.view_pager)
    HorizontalScrollViewPager viewPager;
    @BindView(R.id.ll_point_group)
    LinearLayout llPointGroup;
    @BindView(R.id.list_view)
    RefreshListView ListView;
    @BindView(R.id.banner_title)
    TextView bannerTitle;
    private static final String TAG = "TabDetailPager";
    private final NewsDataInfo.News.Children children;
    private List<TabDetailPagerBean.DataEntity.Topnews> topnews;
    private TabDetailPagerListAdapter mAdapter;
    private List<TabDetailPagerBean.DataEntity.News> news;
    private String url;
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
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(mContext,url,result);
                Log.e(TAG,children.getTitle()+"页面数据请求成功=="+result);
                processData(result);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG,children.getTitle()+"页面数据请求onCancelled=="+cex.getMessage());
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

        ListView.addHeaderView(topNewsView);
    }

    private void processData(String json){
        TabDetailPagerBean bean = parsedJson(json);
        Log.e(TAG,"解析成功=="+bean.getData().getNews().get(0).getTitle());
        topnews = bean.getData().getTopicnews();
        viewPager.setAdapter(new TabDetailPagerTopicNewsAdapter());

        addPoint();
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        bannerTitle.setText(topnews.get(0).getTitle());

        news = bean.getData().getNews();

        ListView.setAdapter(new TabDetailPagerListAdapter());
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

}