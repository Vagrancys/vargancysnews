package com.vargancys.vargancysnews.module;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.BaseActivity;
import com.vargancys.vargancysnews.module.common.GuideActivity;
import com.vargancys.vargancysnews.module.common.MainActivity;
import com.vargancys.vargancysnews.utils.CacheUtils;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/14
 * version:1.0
 */
public class SplashActivity extends BaseActivity {
    public static final String START_MAIN = "start_main";
    @BindView(R.id.relative)
    RelativeLayout Relative;
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle save) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        //alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        //scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        //rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(rotateAnimation);
        set.setDuration(2000);
        Relative.startAnimation(set);
        set.setAnimationListener(new MyAnimationListener());
    }

    class MyAnimationListener implements Animation.AnimationListener{
        //动画结束
        @Override
        public void onAnimationEnd(Animation animation) {
            boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);
            Intent intent;
            if(isStartMain){
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }else{
                intent = new Intent(SplashActivity.this, GuideActivity.class);
            }
            startActivity(intent);
            finish();
        }

        //动画重复播放
        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        //动画开始
        @Override
        public void onAnimationStart(Animation animation) {

        }
    }
}
