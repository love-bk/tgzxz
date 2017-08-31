package com.tianguo.zxz.activity;

import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lx on 2017/4/26.
 */

public class ReWebActivity extends BaseActivity {
    @BindView(R.id.wv_re_cent)
    WebView wvNewSo;
    @BindView(R.id.tv_back)
    TextView tvTitle;
    private MyApplictation application;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_re_wed;
    }

    @Override
    protected void onDestroy() {
        try {


            application.removeActivity(this);
        } catch (Exception e) {

        }
        super.onDestroy();
    }

    @Override
    protected void initView() {
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String urls = getIntent().getStringExtra("urls");
//支持插件
        wvNewSo.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        wvNewSo.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvNewSo.getSettings().setLoadWithOverviewMode(true);
        wvNewSo.getSettings().setBlockNetworkImage(false);//解决图片不显示
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        wvNewSo.getSettings().setJavaScriptEnabled(true);
        wvNewSo.getSettings().setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvNewSo.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wvNewSo.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        wvNewSo.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        wvNewSo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wvNewSo.loadUrl(url);
                return true;
            }
        });
        wvNewSo.loadUrl(urls);

    }

    @Override
    public void onBackPressed() {
        //返回wv里面的页面
        if (wvNewSo.canGoBack()) {
            wvNewSo.goBack();
        } else {
            finish();
        }

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
