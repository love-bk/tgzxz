package com.tianguo.zxz.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.db.ta.sdk.TmListener;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.uctils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lx on 2017/4/18.
 */

public class VidoFragment extends BaseFragment {
    @BindView(R.id.ll_news_fore)
    LinearLayout llNewsFore;
    @BindView(R.id.TMAw1)
    com.db.ta.sdk.TMAwView TMAw1;
    @BindView(R.id.wv_activity)
    WebView wvNewSo;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        mContentView = (VideoEmbedPortalView) view.findViewById(R.id.portal_view);
//        mContentView.callOnFocus(true);
        TMAw1.setAdListener(new TmListener() {
            @Override
            public void onReceiveAd() {
                Log.d("========", "onReceiveAd");
            }

            @Override
            public void onFailedToReceiveAd() {
                Log.d("========", "onFailedToReceiveAd");
            }

            @Override
            public void onLoadFailed() {
                Log.d("========", "onLoadFailed");
            }

            @Override
            public void onCloseClick() {
                Log.d("========", "onCloseClick");
            }

            @Override
            public void onAdClick() {
                Log.d("========", "onAdClick");
            }

            @Override
            public void onAdExposure() {
                Log.d("========", "onAdExposure");
            }
        });
        TMAw1.loadAd(2573);
        wvNewSo.loadUrl("http://videoh5.eastday.com/?qid=onlytgzxz");
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
//            wvNewSo.addJavascriptInterface(new JavaScriptInterface(this), "AndroidFunction");
        wvNewSo.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e(url);
                if (url.startsWith("openapp") || url.startsWith("openapp")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        LogUtils.e("scheme");
                        return true;
                    } catch (Exception e) {
                        return true;
                    }
                }
                view.loadUrl(url);
                return true;
            }
        });
//
//        Bundle params = main.getIntent().getExtras();
//        if (params == null) {
//            params = new Bundle();
//            params.putBoolean(NewsExportArgsUtil.KEY_ENABLE_INVIEW_SEARCHBAR, true);
//            params.putInt(NewsExportArgsUtil.KEY_SCENE, NewsConst.NEWS_PROTAL_SCENE);
//            params.putInt(NewsExportArgsUtil.KEY_SUBSCENE, NewsConst.NEWS_PROTAL_SUBSCENE);
//            params.putInt(NewsExportArgsUtil.KEY_REFER_SCENE, 0);
//            params.putInt(NewsExportArgsUtil.KEY_REFER_SUBSCENE, 0);
//            params.putString(NewsExportArgsUtil.KEY_CHANNEL, "video");
//            mContentView.manualStart(params);
//        } else {
//            params.putInt(NewsExportArgsUtil.KEY_SCENE, NewsConst.NEWS_PROTAL_SCENE);
//            params.putInt(NewsExportArgsUtil.KEY_SUBSCENE, NewsConst.NEWS_PROTAL_SUBSCENE);
//            params.putString(NewsExportArgsUtil.KEY_CHANNEL, "video");
//            mContentView.manualStart(params);
//        }
//        mContentView.callOnCreate();

    }

    @Override
    protected int getLayoutId() {
//        try {
//            main.getWindow().setFormat(PixelFormat.TRANSPARENT);  // 防止添加surfaceView 时闪下黑屏的问题
//
//        } catch (Exception e) {
//            //
//        }
        return R.layout.fragment_main_void;
    }

//
//    @Override
//    public void onDestroy() {
////        if (TMAw1 != null) {
////            TMAw1.destroy();
////        }
////        super.onDestroy();
////        if (mContentView != null) {
////            mContentView.callOnDestroy();
////
////        }
//
//        super.onDestroy();
//    }
//
//    @Override
//    public void onResume() {
////        if (mContentView != null) {
////            mContentView.callOnResume();
////
////        }
////        super.onResume();
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        if (mContentView != null) {
//            mContentView.callOnPause();
//
//        }
//        super.onPause();
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        if (hidden) {
//            if (mContentView != null) {
//                mContentView.callOnDestroy();
//            }
//        }
//        super.onHiddenChanged(hidden);
//    }
//
//    public boolean getIsShow() {
//        return mContentView.callOnBackPressed();
//    }

    @OnClick(R.id.ll_news_fore)
    public void onViewClicked() {
        main.hideSystemKeyBoard(llNewsFore);
    }
}