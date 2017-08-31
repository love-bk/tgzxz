package com.tianguo.zxz.activity.MyActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.view.ProgressWebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lx on 2017/8/22.
 */

public class Nowactivity extends BaseActivity {
    @BindView(R.id.wv_activity)
    ProgressWebView wvNewSo;
    @BindView(R.id.tv_back)
    TextView tvBack;
    private String enterCategory;
    private String url;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_app;

    }

    @Override
    protected void initView() {
        enterCategory = getIntent().getStringExtra(Constant.ST_TYPE);
        url = getIntent().getStringExtra(Constant.URL);
        //支持插件
        wvNewSo.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        wvNewSo.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvNewSo.getSettings().setLoadWithOverviewMode(true);
        wvNewSo.getSettings().setBlockNetworkImage(false);//解决图片不显示
        wvNewSo.getSettings().setJavaScriptEnabled(true);
        wvNewSo.getSettings().setUseWideViewPort(true);
        wvNewSo.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wvNewSo.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvNewSo.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wvNewSo.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        wvNewSo.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
// 设置与Js交互的权限

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        wvNewSo.addJavascriptInterface(new AndroidtoJs(), "test");//AndroidtoJS类对象映射到js的test对象
        // 加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
        wvNewSo.loadUrl(url);

//            wvNewSo.addJavascriptInterface(new JavaScriptInterface(this), "AndroidFunction");
        wvNewSo.setWebChromeClient(new ProgressWebView.WebChromeClient());
        wvNewSo.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                LogUtils.e("数据出来了"+view.getTitle(),"gjj");
                tvBack.setText(view.getTitle());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initData() {

    }

    // 继承自Object类
    public class AndroidtoJs extends Object {
        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void openMain() {//调用该方法跳转到邀请主页
            if (!TextUtils.isEmpty(enterCategory)){
                switch (enterCategory){
                    case Constant.SRMX:
                        Intent intent = new Intent(Nowactivity.this, MainActivity.class);
                        intent.putExtra("type",2);
                        startActivity(intent);
                        break;
                }
            }
            finish();
            LogUtils.e("JS调用了Android的hello方法");
        }
        @JavascriptInterface
        public void openInviteRule() {//调用该方法跳转到
            finish();
        }
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (wvNewSo.canGoBack())
            wvNewSo.goBack();
        else {
            super.onBackPressed();
        }
    }
}
