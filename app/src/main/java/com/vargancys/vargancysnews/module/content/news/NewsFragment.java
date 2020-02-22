package com.vargancys.vargancysnews.module.content.news;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.BaseFragment;
import com.vargancys.vargancysnews.base.MenuDetailBasePager;
import com.vargancys.vargancysnews.module.common.MainActivity;
import com.vargancys.vargancysnews.module.common.fragment.LeftMenuFragment;
import com.vargancys.vargancysnews.module.content.news.api.NewsDataInfo;
import com.vargancys.vargancysnews.module.menudetail.InteracMenuDetailPager;
import com.vargancys.vargancysnews.module.menudetail.NewsMenuDetailPager;
import com.vargancys.vargancysnews.module.menudetail.PhotoMenuDetailPager;
import com.vargancys.vargancysnews.module.menudetail.TopicMenuDetailPager;
import com.vargancys.vargancysnews.utils.Https;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/16
 * version:1.0
 */
public class NewsFragment extends BaseFragment {
    @BindView(R.id.news_title)
    TextView NewsTitle;
    @BindView(R.id.fl_content)
    FrameLayout FlContent;
    private ArrayList<MenuDetailBasePager> detailBasePagers;
    public static NewsFragment newInstance(){
        return new NewsFragment();
    }


    private ArrayList<NewsDataInfo.News> news = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet(){
        Https.getNewsService().getNewsDataInfo().enqueue(new Callback<NewsDataInfo>() {
            @Override
            public void onResponse(Call<NewsDataInfo> call, Response<NewsDataInfo> response) {
                if(response.body().getError() == 200){
                    news = response.body().getNews();
                    finishData();
                }else{
                    Toast.makeText(context,"错误信息 = "+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsDataInfo> call, Throwable t) {
                Toast.makeText(context,"联网失败了!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void finishData(){
        MainActivity mainActivity = (MainActivity) context;
        //得到左侧菜单
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        detailBasePagers = new ArrayList<>();
        detailBasePagers.add(new NewsMenuDetailPager(context,news.get(0)));
        detailBasePagers.add(new TopicMenuDetailPager(context));
        detailBasePagers.add(new PhotoMenuDetailPager(context,news.get(2)));
        detailBasePagers.add(new InteracMenuDetailPager(context));
        //把数据存进左侧菜单
        leftMenuFragment.setData(news);

    }

    public void switchPager(int position){
        NewsTitle.setText(news.get(position).getTitle());
        FlContent.removeAllViews();
        MenuDetailBasePager detailBasePager = detailBasePagers.get(position);
        View rootView = detailBasePager.rootView;
        detailBasePager.initData();
        FlContent.addView(rootView);
    }
}
