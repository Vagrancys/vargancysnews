package com.vargancys.vargancysnews.module.common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/21
 * version:1.0
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_text_size)
    ImageView imgTextSize;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress)
    ProgressBar progress;
    private String url;
    private WebSettings webSettings;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initView(Bundle save) {
        getData();
        imgBack.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgTextSize.setOnClickListener(this);
    }

    private void getData(){
        url = getIntent().getStringExtra("url");

        webSettings = webView.getSettings();
        //设置支持javaScript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setTextZoom(100);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(url);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_text_size:
                showChangeTextSizeDialog();
                //Toast.makeText(NewsDetailActivity.this,"设置文字",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_share:
                Toast.makeText(NewsDetailActivity.this,"分享",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private int tempSize = 2;
    private int realSize = tempSize;

    private void showChangeTextSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置文字大小");
        String[] items = {"超大字体","大字体","正常字体","小字体","超小字体"};
        builder.setSingleChoiceItems(items, realSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tempSize = which;
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realSize = tempSize;
                changeTextSize(realSize);
            }
        });
        builder.show();
    }

    private void changeTextSize(int realSize) {
        switch (realSize){
            case 0:
                webSettings.setTextZoom(200);
                break;
            case 1:
                webSettings.setTextZoom(150);
                break;
            case 2:
                webSettings.setTextZoom(100);
                break;
            case 3:
                webSettings.setTextZoom(75);
                break;
            case 4:
                webSettings.setTextZoom(50);
                break;
        }
    }
}
