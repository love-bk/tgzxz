package com.tianguo.zxz.activity.MyActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.DuiBaBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.NetworkUtil;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.tianguo.zxz.view.ProgressWebView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class CreditActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tvTitle;
    @BindView(R.id.wv_duiba)
    ProgressWebView wvNewSo;
    @BindView(R.id.vv_loading_bg)
    View LodingBg;
    private AlphaAnimation alphaAnimation;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_duidba_web;

    }


    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        tvTitle.setText("兑换商城");
        wvNewSo.setVisibility(View.INVISIBLE);
        LodingBg.setVisibility(View.VISIBLE);
        alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setDuration(3000);
        LodingBg.startAnimation(alphaAnimation);
        setLoadingFlag(false);
        goDuiba();
    }

    public void goDuiba() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("devid",SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getDuiBa(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<DuiBaBean>(this, pd) {
            @Override
            protected void onHandleSuccess(DuiBaBean bean) {
                if (bean==null){
                    ToastUtil.showMessage("网络连接失败请您检查网络");
                    return;
                }
                if (null!=bean.getUrl()){
                    wvNewSo.loadUrl(bean.getUrl());
                    setUrls(bean.getUrl());
                }

            }
        });
    }
    public void setUrls(final String ar) {
        if (!NetworkUtil.isNetworkAvailable(this)){
            ToastUtil.showMessage("网络连接失败请您检查网络");
            return;
        }
        try {
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
            wvNewSo.setWebChromeClient(new ProgressWebView.WebChromeClient());
            wvNewSo.setWebViewClient(new WebViewClient() {


                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    wvNewSo.loadUrl(url);
                    return true;
                }

                @SuppressLint("WrongConstant")
                @Override
                public void onPageFinished(WebView view, String url) {
                    LodingBg.clearAnimation();
                    LodingBg.invalidate();
                    LodingBg.setVisibility(View.GONE);
                    wvNewSo.setVisibility(View.VISIBLE);
                    super.onPageFinished(view, url);
                }

                @SuppressLint("WrongConstant")
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {

                    wvNewSo.setVisibility(View.INVISIBLE);

                    super.onPageStarted(view, url, favicon);
                }
            });
            wvNewSo.loadUrl(ar);
        }catch (Exception e){

        }

    }

    @Override
    public void onBackPressed() {
        if (wvNewSo.canGoBack()){
            wvNewSo.goBack();
        }else {
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
