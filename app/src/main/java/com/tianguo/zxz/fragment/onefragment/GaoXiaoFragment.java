package com.tianguo.zxz.fragment.onefragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.MYBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.MD5;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.Observable;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by lx on 2017/5/19.
 */

public class GaoXiaoFragment extends BaseFragment {
    String name;
    String useid;
    String phone;
    String haded;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.wv_so_web)
    WebView wvNewSo;
    private TelephonyManager tm;
    private MD5 m;
    HashMap<String, String> map = new HashMap<>();

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvBack.setVisibility(View.GONE);
        m = new MD5();
        tm = (TelephonyManager) main.getSystemService(TELEPHONY_SERVICE);
        getLogin();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        try {
            if (isVisibleToUser){
                if (map!=null){
                    map.put("click_search","虎牙直播");
                    MobclickAgent.onEvent(main, "search", map);
                }
            }else {
                if (wvNewSo!=null){
                    if (wvNewSo.canGoBack()){
                        wvNewSo.goBack();
                    }
                }
            }
            super.setUserVisibleHint(isVisibleToUser);
        }catch (Exception e ){

        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sowebactivty;
    }

    public void getLogin() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        map.put("devid",SharedPreferencesUtil.getOnlyID(main));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(main));
        Observable loginss = RetroFactory.getInstance().getME(map);
        loginss.compose(main.composeFunction).subscribe(new BaseObserver<MYBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(MYBean bean) {
                useid = bean.getUser().getU() + "";
                if (TextUtils.isEmpty(bean.getUser().getHead())) {
                    haded = "http://news.wondersea.mobi/news/img/wxlogo.png";
                } else {
                    haded = bean.getUser().getHead();
                }
                if (TextUtils.isEmpty(bean.getUser().getNick())) {
                    name = bean.getUser().getU() + "";
                } else {
                    name = bean.getUser().getNick();
                }
                if (TextUtils.isEmpty(bean.getUser().getPhone())) {
                    phone = tm.getLine1Number();// 手机号码  
                } else {
                    phone = bean.getUser().getPhone();
                }
                String md = "birthday=0&nickname=" + name + "&sex=0&xavatar=" + haded + "&xphone=" + phone + "&xuid=" + useid;
                String info = "{" + "\"birthday\"" + ":" + 0 + "," + "\"nickname\"" + ":" + "\"" + name + "\"" + "," + "\"sex\"" + ":" + 0 + "," + "\"xavatar\"" + ":" + "\"" + haded + "\"" + ","
                        + "\"xphone\"" + ":" + "\"" + phone + "\"" + "," + "\"xuid\"" + ":" + "\"" + useid + "\"" + "}";
                LogUtils.e(md);
                setUrls("http://zbapi.mumayi.com/user/yzbh5/yizhibo.html?appkey=3d5301536205b3eb5geI8VAG++5ro+bwdDuEmZ5Buriob859n+I4e1GtRnIPEqkG9A&xinfo="
                        + info
                        + "&sign=" + m.getMD5ofStr(m.getMD5ofStr(md) + "3d5301536205b3eb5geI8VAG++5ro+bwdDuEmZ5Buriob859n+I4e1GtRnIPEqkG9A"));

            }
        });

    }

    public boolean isBack() {
        if (null == wvNewSo) {
            return true;

        }

        if (wvNewSo.canGoBack()) {
            wvNewSo.goBack();
            return false;
        } else {
            return true;
        }
    }

    public void setUrls(final String ar) {
        LogUtils.e(ar);
        //支持插件
        try {
            wvNewSo.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            wvNewSo.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            wvNewSo.getSettings().setLoadWithOverviewMode(true);
            wvNewSo.getSettings().setBlockNetworkImage(false);//解决图片不显示
            //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
            wvNewSo.getSettings().setJavaScriptEnabled(true);
            wvNewSo.getSettings().setUseWideViewPort(true);
            wvNewSo.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
            wvNewSo.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
            wvNewSo.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return false;
                }
            });
            wvNewSo.loadUrl(ar);
        } catch (Exception e) {
        }

    }

}
