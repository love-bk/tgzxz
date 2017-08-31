package com.tianguo.zxz.activity.MyActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.view.ProgressWebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lx on 2017/5/19.
 */

public class SoWebActivity extends BaseActivity {
    @BindView(R.id.tv_back)
    TextView tvBack;

    @BindView(R.id.wv_so_web)
    ProgressWebView wvNewSo;
    private String navFlag;
    private MyApplictation application;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sowebactivty;
    }

    @Override
    protected void onDestroy() {
        try{


            application.removeActivity(this);
        }catch (Exception e){

        }
        super.onDestroy();

    }
    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        tvBack.setVisibility(View.VISIBLE);
        String title = getIntent().getStringExtra(Constant.TITLE);
        if (!TextUtils.isEmpty(title))
            tvBack.setText(title);
        setUrls(getIntent().getStringExtra("url"));
            navFlag = getIntent().getStringExtra(Constant.NAV);
    }

    @Override
    protected void initData() {
    }
    public void setUrls(final String ar) {

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
        wvNewSo.getSettings().setDomStorageEnabled(true);
        wvNewSo.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        wvNewSo.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        wvNewSo.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        wvNewSo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e(url);
                LogUtils.e(url);
                if(url.startsWith("taobao")||url.startsWith("openapp")||url.startsWith("tmall")||url.startsWith("ucweb")) {
                    try{
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }catch (Exception e){
                        return true;
                    }
                }
                wvNewSo.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });
        wvNewSo.setWebChromeClient(new ProgressWebView.WebChromeClient());
        wvNewSo.loadUrl(ar);
    }

    @Override
    public void onBackPressed() {

        if (!TextUtils.isEmpty(navFlag)) {
            finish();
        }
        if (wvNewSo.canGoBack()){
            wvNewSo.goBack();
        }else {
            finish();
        }

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
