package com.tianguo.zxz.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.db.ta.sdk.TmListener;
import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.R;
import com.tianguo.zxz.SampleApplicationLike;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.net.NetworkUtil;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.view.MySocWebView;

import butterknife.BindView;

/**
 * Created by lx on 2017/5/10.
 */

public class NewsWebFragment extends BaseFragment {

    @BindView(R.id.wv_baidu)
    MySocWebView wvNewSo;
    private String arr;
    @BindView(R.id.TMAw1)
    com.db.ta.sdk.TMAwView TMAw1;
    private MainActivity main;
    private AlphaAnimation alphaAnimation;
    @BindView(R.id.vv_loading_bg)
    View LodingBg;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        main = (MainActivity) getActivity();
        AlphaAnimation    alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(3000);
        setUrls(arr);
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
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (wvNewSo.canGoBack()) {
            wvNewSo.goBack(true);
        }
    }



    @Override
    public void onStop() {
        LogUtils.e(wvNewSo.canGoBack()+"goBack");
        if (wvNewSo.canGoBack()){
            wvNewSo.goBack(true);

        }
        super.onStop();
    }

    public void setUrls(final String ar) {
        try {
            if (!NetworkUtil.isNetworkAvailable(SampleApplicationLike.getContext())){
                ToastUtil.showMessage("网络连接失败请您检查网络");
                return;
            }
        }catch (Exception e){

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
            wvNewSo.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    wvNewSo.loadUrl(url);
                    return false;
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
                    LodingBg.setVisibility(View.VISIBLE);
                    alphaAnimation = new AlphaAnimation(1,0);
                    alphaAnimation.setDuration(3000);
                    wvNewSo.setVisibility(View.INVISIBLE);
                    LodingBg.startAnimation(alphaAnimation);
                    super.onPageStarted(view, url, favicon);
                }
            });
            wvNewSo.loadUrl("http://videoh5.eastday.com/?qid=onlytgzxz");
            main.setOnMicListner(new MainActivity.OnMusicListner() {
                @Override
                public void onmusiclistner() {
                    if (wvNewSo.canGoBack()){
                        LogUtils.e("bac" +
                                "sssss");
                        wvNewSo.goBack(false);
                    }
                }
                });
            main.setOnIsBackListner(new MainActivity.OnIsBackListner() {
                @Override
                public boolean OnBackListner() {
                    LogUtils.e("bac" +
                            "sssss");
                    if (wvNewSo.canGoBack()) {
                        wvNewSo.goBack(true);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }catch (Exception e){

        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manin_web;
    }
//    public void setArr(String arr) {
//        switch (arr) {
//            case "娱乐":
//                this.arr = "https://cpu.baidu.com/1001/a032e14d";
//                break;
//            case "视频":
//                this.arr = "https://cpu.baidu.com/1033/a032e14d";
//                break;
//            case "图片":
//                this.arr = "https://cpu.baidu.com/1003/a032e14d";
//
//                break;
//            case "美女":
//                this.arr = "https://cpu.baidu.com/1024/a032e14d";
//                break;
//            case "时尚":
//                this.arr = "https://cpu.baidu.com/1009/a032e14d";
//                break;
//            case "搞笑":
//                this.arr = "https://cpu.baidu.com/1025/a032e14d";
//                break;
//            case "女人":
//                this.arr = "https://cpu.baidu.com/1034/a032e14d";
//                break;
//            case "生活":
//                this.arr = "https://cpu.baidu.com/1035/a032e14d";
//                break;
//            case "科技":
//                this.arr = "https://cpu.baidu.com/1013/a032e14d";
//                break;
//        }
//    }
//    OnNewsScolisetner listner;
//    public  void setNewsListner(OnNewsScolisetner listner){
//this.listner =listner;
//    }
//public  interface  OnNewsScolisetner{
//        void  newsScolistner(boolean newSorl);
//}
}
