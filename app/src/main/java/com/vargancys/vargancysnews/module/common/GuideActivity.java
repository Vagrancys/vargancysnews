package com.vargancys.vargancysnews.module.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.BaseActivity;
import com.vargancys.vargancysnews.module.SplashActivity;
import com.vargancys.vargancysnews.utils.CacheUtils;
import com.vargancys.vargancysnews.utils.DensityUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/14
 * version:1.0
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.guide_button)
    TextView guideButton;
    @BindView(R.id.guide_point)
    LinearLayout guidePoint;
    @BindView(R.id.guide_red)
    ImageView guideRed;
    private ArrayList<ImageView> imageViews;
    //两点的间距
    private int leftMax;
    private int widthDpi;
    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView(Bundle save) {
        int[] ids = new int[]{
                R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3
        };

        widthDpi = DensityUtils.dip2px(this,10);
        imageViews = new ArrayList<>();
        for (int i = 0; i <ids.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);
            imageViews.add(imageView);
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthDpi,widthDpi);
            if(i != 0){
                params.leftMargin = widthDpi;
            }
            point.setLayoutParams(params);
            guidePoint.addView(point);
        }

        viewPager.setAdapter(new MyPagerAdapter());

        guideRed.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        //得到屏幕滑动的百分比
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        guideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtils.putBoolean(GuideActivity.this, SplashActivity.START_MAIN,true);
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        /**
         * 当页面被选中的时候，回调这个方法
         * @param position 被选中页面的对应的位置
         */
        @Override
        public void onPageSelected(int position) {
            if(position == imageViews.size() - 1){
                guideButton.setVisibility(View.VISIBLE);
            }else{
                guideButton.setVisibility(View.GONE);
            }
        }

        /**
         * 当页面滑动了会回调这个方法
         * @param position 当前滑动页面的位置
         * @param positionOffset 页面滑动的百分比
         * @param positionOffsetPixels 滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //两点间移动的距离 = 屏幕滑动百分比 * 间距
            //int leftMargin = (int) (positionOffset * leftMax);
            //两点间滑动距离对应的坐标 = 原来的起始位置 + 两点间移动的距离
            int leftMargin = (int) (position * leftMax + (positionOffset * leftMax));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) guideRed.getLayoutParams();
            //params.leftMargin = 两点间滑动距离对应的坐标
            params.leftMargin = leftMargin;
            guideRed.setLayoutParams(params);
        }

        /**
         * 当viewPager页面滑动状态发生变化的时候
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{
        @Override
        public void onGlobalLayout() {
            guideRed.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            //间距
            leftMax = guidePoint.getChildAt(1).getLeft() - guidePoint.getChildAt(0).getLeft();

        }
    }

    class MyPagerAdapter extends PagerAdapter{

        /**
         * 返回的个数
         * @return
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * 判断
         * @param view 当前创建的视图
         * @param o 上面instantiateItem返回的结果值
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        /**
         * 作用 getView
         * @param container viewPager
         * @param position 要创建页面的位置
         * @return 返回和创建当前页面有关系的值
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        /**
         * 销毁页面
         * @param container viewPager
         * @param position 要销毁页面的位置
         * @param object 要销毁的页面
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
