package com.tianguo.zxz.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tianguo.zxz.Flat;
import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.JiangLiBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.MD5;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.view.ProgressWebView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/5/26.
 */

public class GGWed extends BaseActivity {
    @BindView(R.id.tv_back)
    TextView tvTitle;
    @BindView(R.id.wv_so_web)
    ProgressWebView wvNewSo;
    private int id;
    private MyApplictation application;
    private int taskId;
    private boolean isComplete;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sowebactivty;
    }

    @Override
    protected void initView() {
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        try {
            String title = getIntent().getStringExtra(Constant.TITLE);
            if (!TextUtils.isEmpty(title))
                tvTitle.setText(title);
            setUrls(getIntent().getStringExtra("url"));
            taskId = getIntent().getIntExtra("id", 0);
            if (taskId != 0) {
                taskDone();
                Intent intent = new Intent();
                LogUtils.e(getIntent().getIntExtra("id", 0) + "");
                intent.putExtra(Flat.REQUESTFLAG, true);
                setResult(1, intent);
            }
        } catch (Exception e) {

        }

    }

    @SuppressLint("JavascriptInterface")
    public void setUrls(final String ar) {
        try {

            wvNewSo.loadUrl(ar);

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
                            startActivity(intent);
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
            wvNewSo.setWebChromeClient(new ProgressWebView.WebChromeClient());
        } catch (Exception e) {

        }
    }

    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        public void openActivity() {
        }
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
    protected void initData() {
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (Constant.START.equals(getIntent().getStringExtra(Constant.SKIP_MARK))) {
            LogUtils.e("jinlale", "gjj");
            setResult(101, new Intent());
        }
        super.onBackPressed();
    }

    /**
     * 做完任务的接口
     */
    private void taskDone() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("m", new MD5().getMD5ofStr("ssd@#$%^&*!" + SharedPreferencesUtil.getID(this) + taskId));
        map.put("id", taskId);
        Observable doneTask = RetroFactory.getInstance().doneTask(map);
        doneTask.compose(composeFunction).subscribe(new BaseObserver<JiangLiBean>(this, pd) {
            @Override
            protected void onHandleSuccess(JiangLiBean jiangLiBean) {
                isComplete = true;
            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
            }
        });
    }

}
