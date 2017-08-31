package com.tianguo.zxz.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyWebViewActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tvTitle;
    @BindView(R.id.wv_so_web)
    WebView wvNewSo;
    private ProgressDialog pd;
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
        tvTitle.setVisibility(View.VISIBLE);
        pd = new ProgressDialog(this);
        pd.setMessage("加载中请稍候");
        setUrls(getIntent().getStringExtra("url"));
    }

    @Override
    protected void initData() {
    }
    public void setUrls(final String ar) {
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        wvNewSo.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvNewSo.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wvNewSo.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        wvNewSo.getSettings().setDomStorageEnabled(true);
        wvNewSo.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
//        wvNewSo.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                wvNewSo.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                pd.show();
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (pd.isShowing()){
//                    pd.dismiss();
//                }
//            }
//        });
        wvNewSo.loadUrl(ar);
    }

    @Override
    public void onBackPressed() {
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

}
