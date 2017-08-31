package com.tianguo.zxz.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.dianjoy.video.InterstitialAd;
import com.dianjoy.video.InterstitialAdListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.BannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.tianguo.zxz.Flat;
import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.WebListDataAdaperte;
import com.tianguo.zxz.adapter.gcSoAdapter;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.MyGGbean;
import com.tianguo.zxz.bean.NewXQBean;
import com.tianguo.zxz.bean.NewsDataBean;
import com.tianguo.zxz.bean.ReCiBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.Httpget;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.AdvancedCountdownTimer;
import com.tianguo.zxz.uctils.DownGGUtils;
import com.tianguo.zxz.uctils.DowonGgDilaog;
import com.tianguo.zxz.uctils.GuanGaoUtils;
import com.tianguo.zxz.uctils.HongShowUtils;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.MD5;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.tianguo.zxz.view.MyGridView;
import com.tianguo.zxz.view.MyScrollView;
import com.tianguo.zxz.view.ShareDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lx on 2017/4/20.
 */

public class WebListActivity extends BaseActivity {
    @BindView(R.id.wv_main_newxs)
    WebView wvMainNewxs;
    StringBuilder sb = new StringBuilder();
    @BindView(R.id.tv_main_news_teile)
    TextView tvMainNewsTeile;
    @BindView(R.id.tv_main_news_time)
    TextView tvMainNewsTime;
    @BindView(R.id.tv_help_back)
    ImageView tvHelpBack;
    @BindView(R.id.tv_cent_gg_teile)
    TextView tvCentGgTeile;
    @BindView(R.id.tv_x)
    TextView tvx;
    @BindView(R.id.sc_cent_web)
    MyScrollView scCentWeb;
    @BindView(R.id.gv_cent_shishi)
    MyGridView gvCentShishi;
    @BindView(R.id.iv_hong_xiao)
    ImageView ivnewhongbao;
    @BindView(R.id.iv_news_fenxiang)
    ImageView iv_news_fenxiang;
    @BindView(R.id.ryl_relist)
    RecyclerView rylRelist;
    @BindView(R.id.ll_cent_web)
    LinearLayout llcentweb;
    @BindView(R.id.ll_current_search)
    LinearLayout llCurrentSearch;
    @BindView(R.id.ll_sponsors)
    RelativeLayout llSponsors;
    @BindView(R.id.baidubanner)
    LinearLayout baidubanner;
    @BindView(R.id.detail_line1)
    View detailLine1;
    @BindView(R.id.detail_line2)
    View detailLine2;
    @BindView(R.id.detail_line3)
    View detailLine3;
    @BindView(R.id.in_my_gg)
    LinearLayout in_my_gg;
    @BindView(R.id.iv_cent_gg)
    TextView ivCentGg;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.gv_cent_gg)
    ImageView gvCentGg;
    @BindView(R.id.tv_look_all)
    TextView tvLookAll;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_open_down)
    TextView tvOpenDown;
    @BindView(R.id.tv_ad_source)
    TextView tvAdSource;
    private ViewGroup bannerContainer;
    boolean istent = true;
    AQuery $;
    int baoguang = 0;
    boolean isShowGG = false;
    private BannerView bvs;

    private LinearLayout bannerContainer_fubiao;
    private BannerView bv;
    private gcSoAdapter gcSo;
    String urls = "https://yz.m.sm.cn/s?from=wm280283&q=";
    ArrayList<ReCiBean> datas = new ArrayList<>();
    private HashMap<String, String> map;
    private String[] split;
    private String title;
    private GuanGaoUtils guanGaoUtils;
    private int ps;
    public int i = 0;
    private ShareDialog diogUtils;
    private List<Object> listData = new ArrayList<>();
    private WebListDataAdaperte webdata;
    private String awardKey;
    private boolean isShowAward = true;
    private Dialog dialog;
    @BindView(R.id.iv_tian_hong)
    ImageView iv_tian_hong;
    private AdvancedCountdownTimer timer;
    public int times = 12;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initBanner(times);
        }
    };
    private long isAward;

    private List<MyGGbean.Cpa4Bean> myGG = Flat.cp4;
    private DowonGgDilaog dilaog;
    private int webid;
    private String url;
    private boolean isZan;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main_web;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        try {
            ivnewhongbao.setVisibility(View.GONE);
            tvZan.setText((int) (Math.random() * 100) + 1 + "");
            bv = new BannerView(this, ADSize.BANNER, "1106101702", "3000726422111291");
            guanGaoUtils = new GuanGaoUtils(false, this);
            webid = getIntent().getIntExtra("id", 0);
            ps = getIntent().getIntExtra("ps", 0);
            diogUtils = new ShareDialog(this);
            map = new HashMap<>();
            iv_tian_hong.setTag(R.id.red_packet_tag, getString(R.string.red_packet_tag));
            if (iv_tian_hong.getTag(R.id.red_packet_tag) != null) {
                String data = SharedPreferencesUtil.getSwitch(this, iv_tian_hong.getTag(R.id.red_packet_tag).toString());
                if (!TextUtils.isEmpty(data)) {
                    iv_tian_hong.setVisibility(View.VISIBLE);
                    HongShowUtils.show(this, iv_tian_hong, tvMainNewsTeile);
                }
            }

            iv_news_fenxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(WebListActivity.this) + "红包会更多！");
                    if (diogUtils != null && !diogUtils.isShowing()) {
                        diogUtils.isShow(llcentweb, webid, split[0], title);
                    }
                    WindowManager.LayoutParams attributes = getWindow().getAttributes();
                    attributes.alpha = 0.6f;
                    getWindow().setAttributes(attributes);
                }
            });
            diogUtils.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams attributes = getWindow().getAttributes();
                    //当弹出Popupwindow时，背景变半透明
                    attributes.alpha = 1f;
                    getWindow().setAttributes(attributes);
                }
            });
            $ = new AQuery(this);
            getGvData(0);
            bannerContainer_fubiao = (LinearLayout) this.findViewById(R.id.bannerContainer_fubiao);
            getNewsDate();
            gcSo = new gcSoAdapter(this, datas);
            gcSo.setOnSoListner(new gcSoAdapter.OnSoListner() {
                @Override
                public void onsoListner(String lls) {
                    Intent intent = new Intent(WebListActivity.this, ReWebActivity.class);
                    intent.putExtra("urls", urls + lls.trim());
                    startActivity(intent);
                }
            });
            final String TAG = "weblistactivli";
            gvCentShishi.setAdapter(gcSo);
            bannerContainer = (ViewGroup) this.findViewById(R.id.bannerContainer);

            InterstitialAd ad = new InterstitialAd(this, "37f3abca969b37a7", "");
            ad.setAdListener(new InterstitialAdListener() {
                @Override
                public void onAdReady() {
                    Log.i(TAG, "onAdReady");
                }

                @Override
                public void onAdPresent() {
                    Log.i(TAG, "onPresent");
                }

                @Override
                public void onAdClick() {
                    Log.i(TAG, "onAdClick");
                }

                @Override
                public void onAdDismissed() {
                    Log.i(TAG, "onAdDismissed");
                }

                @Override
                public void onAdFailed(String msg) {
                    Log.i(TAG, "onAdFailed:" + msg);
                }
            });
            ad.loadAd();
            guanGaoUtils.setOnShowListner(new GuanGaoUtils.OnisShowGGListner() {
                @Override
                public void shouGG() {
                    isShowGG = true;
                }
            });
            timer = new AdvancedCountdownTimer(12000, 1000) {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onTick(long millisUntilFinished, int percent) {
                    if (times == 4 || times == 12 || times == 8) {
                        handler.sendEmptyMessage(1);
                    }
                    tvx.setText(times + "");
                    if (times < 0) {
                        times = 0;
                    } else {
                        times--;

                    }

                }

                @Override
                public void onFinish() {
                    if (times - 1 <= 0) {
                        tvx.setText("x");
                        tvx.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tvx.setVisibility(View.GONE);
                                bannerContainer_fubiao.setVisibility(View.GONE);
                            }
                        });
                        if (isShowGG) {
                            getNewsAwrad();

                        }
                    }
                }
            };
            timer.start();
        } catch (Exception e) {

        }
    }


    @Override
    public void onBackPressed() {
        try {
            if (diogUtils != null && diogUtils.isShowing()) {
                diogUtils.dismiss();
                return;
            }
        } catch (Exception e) {

        }
        finish();
    }

    private void initBanner(int times) {
        try {


            LogUtils.e(times + "ssssss");
            if (times == 11) {
                bv.setADListener(new BannerADListener() {
                    @Override
                    public void onNoAD(int i) {

                    }

                    @Override
                    public void onADReceiv() {
                        isShowGG = true;
                    }

                    @Override
                    public void onADExposure() {

                    }

                    @Override
                    public void onADClosed() {

                    }

                    @Override
                    public void onADClicked() {
                        map.put("click_banner", "广点通横幅");
                        MobclickAgent.onEvent(WebListActivity.this, "banner", map);
                    }

                    @Override
                    public void onADLeftApplication() {

                    }

                    @Override
                    public void onADOpenOverlay() {

                    }

                    @Override
                    public void onADCloseOverlay() {

                    }
                });
                bv.loadAD();
                bannerContainer_fubiao.removeAllViews();
                bannerContainer_fubiao.addView(bv);
                return;
            }
            switch (ps) {
                case 7:
                    if (times == 3) {
                        bv.loadAD();
                        bannerContainer_fubiao.removeAllViews();
                        bannerContainer_fubiao.addView(bv);

                    } else {
                        LogUtils.e(times + "ssssss");
                        guanGaoUtils.xunfeiBannerAd(this, "D432EADD5E5E01CD7E1933B736C7A68D", bannerContainer_fubiao);


                    }
                    break;
                case 2:
                case 4:
                    LogUtils.e(times + "ssssss");

                    if (times == 3) {
                        guanGaoUtils.AdViewBanner("SDK20170914090420rr4pmvotgihhrps", bannerContainer_fubiao);

                    } else {
                        guanGaoUtils.xunfeiBannerAd(this, "31BCF98830C245AB67EE9A415EF762F0", bannerContainer_fubiao);

                    }
                    break;
                case 5:
                case 6:
                    LogUtils.e(times + "ssssss");

                    if (times == 3) {
                        guanGaoUtils.AdViewBanner("SDK20170914090420rr4pmvotgihhrps", bannerContainer_fubiao);
                    } else {
                        guanGaoUtils.xunfeiBannerAd(this, "66216EC25363F6B41D7BAA4A080C5D2D", bannerContainer_fubiao);

                    }
                    break;
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        try {
            bannerContainer_fubiao.removeAllViews();
            i = 0;
            if (diogUtils != null) {
                if (diogUtils.isShowing()) {
                    diogUtils.dismiss();

                }
                diogUtils = null;
            }
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                dialog = null;
            }
            if (bv != null) {
                baoguang = 0;
                bv.destroy();
                bv = null;
            }
            if (timer != null) {
                timer.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rylRelist.setLayoutManager(linearLayoutManager);
        webdata = new WebListDataAdaperte(this, listData, ps);
        rylRelist.setAdapter(webdata);
        getNewslist();

    }

    public void getNewslist() {
        try {
            setLoadingFlag(false);
            HashMap<String, Object> map = new HashMap<>();
            map.put("type", ps);
            map.put("devid", SharedPreferencesUtil.getOnlyID(this));
            map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
            map.put("sso", SharedPreferencesUtil.getSSo(this));
            Observable observable = RetroFactory.getInstance().getNewsDataList(map);
            observable.compose(composeFunction).subscribe(new BaseObserver<NewsDataBean>(this, pd) {
                @SuppressLint("WrongConstant")
                @Override
                protected void onHandleSuccess(final NewsDataBean bean) {
                    LogUtils.e("cpa广告"+bean.getAd(),"gjj");
                    listData.addAll(bean.getNews());
                    if (listData != null && listData.size() > 0) {
                        listData.add(0, bean.getAd());
                    }
                    if (webdata != null) {
                        webdata.notifyDataSetChanged();
                    }
                }

                @Override
                public void onHandleError(int code, String message) {
                    super.onHandleError(code, message);
                }
            });
        } catch (Exception e) {

        }
    }

    public void getNewsDate() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", ps);
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        map.put("id", getIntent().getIntExtra("id", -1));
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        Observable observable = RetroFactory.getInstance().gitNewsCent(map);
        observable.compose(composeFunction).subscribe(new BaseObserver<NewXQBean>(this, pd) {
            @SuppressLint("WrongConstant")
            @Override
            protected void onHandleSuccess(final NewXQBean bean) {
                if (bean == null) {
                    return;
                }
                isAward = bean.getIsAward();
                if (bean.getIsAward() != 0) {
                    ToastUtil.showMessage("继续阅读，可以获得红包奖励哦");

                }
                scCentWeb.setScanScrollChangedListener(new MyScrollView.ISmartScrollChangedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onScrolledToBottom() {
                        if (i == 0) {
                            initBanners();
                            bvs.loadAD();
                        }

                    }

                    @Override
                    public void onScrolledToTop() {

                    }

                    @Override
                    public void onScrolled() {
                    }
                });
                awardKey = bean.getAwardKey();
                split = bean.getNews().getThumb().trim().split("[,]");
                title = bean.getNews().getTitle();
                tvMainNewsTeile.setText(bean.getNews().getTitle());
                tvMainNewsTime.setText(bean.getNews().getAuth() + "   |   " + bean.getNews().getDay());
                //拼接一段HTML代码
                sb.append("<html>");
                sb.append("<head>");
                sb.append("<title>");
                sb.append(bean.getNews().getTitle());
                sb.append("</title>");
                sb.append("</head>");
                sb.append("<body>");
                sb.append(bean.getNews().getBody());
                sb.append("</body>");
                sb.append("</html>");
                wvMainNewxs.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
                wvMainNewxs.setWebViewClient(new WebViewClient() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        rylRelist.setVisibility(View.VISIBLE);
                        llCurrentSearch.setVisibility(View.VISIBLE);
                        llSponsors.setVisibility(View.VISIBLE);
                        detailLine1.setVisibility(View.VISIBLE);
                        detailLine2.setVisibility(View.VISIBLE);
                        detailLine3.setVisibility(View.VISIBLE);
                        llcentweb.setVisibility(View.VISIBLE);
                        showMyGG();
                    }
                });
            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
            }
        });
    }

    public void getNewsAwrad() {
        setLoadingFlag(false);
        try {
            if (!isShowAward || isAward == 0) {
                return;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("devid", SharedPreferencesUtil.getOnlyID(this));
            map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
            map.put("id", getIntent().getIntExtra("id", -1));
            if (awardKey != null && !awardKey.isEmpty()) {
                map.put("ak", awardKey);
            }
            map.put("t", isAward);
            map.put("m", new MD5().getMD5ofStr("ssd@#$%^*!@#" + SharedPreferencesUtil.getID(this) + getIntent().getIntExtra("id", -1) + isAward).substring(12, 20));
            map.put("sso", SharedPreferencesUtil.getSSo(this));
            Observable observable = RetroFactory.getInstance().getNewsAwrad(map);
            observable.compose(composeFunction).subscribe(new BaseObserver<ReCiBean>(this, pd) {
                @SuppressLint("WrongConstant")
                @Override
                protected void onHandleSuccess(final ReCiBean bean) {
                    if (bean.getAward() != 0) {
                        isShowAward = false;
                        isAward = 0;
                        dialog = new Dialog(WebListActivity.this, R.style.dialog);
                        View inflate = View.inflate(WebListActivity.this, R.layout.dialog_hongbao, null);
                        TextView tv_manery_text = (TextView) inflate.findViewById(R.id.tv_manery_text);
                        dialog.setContentView(inflate);
                        dialog.setCancelable(false);
                        tv_manery_text.setText((int) bean.getAward() + "");
                        Intent intents = new Intent("com.action.HONG_BAO");
                        intents.putExtra("isDON", true);
                        intents.setPackage(getPackageName());
                        sendBroadcast(intents);
                        Intent intent = getIntent();
                        intent.putExtra("position", intent.getIntExtra("position", 0));
                        setResult(204, intent);
                        ivnewhongbao.setVisibility(View.GONE);
                        if (bean.getAward() == 0) {
                            return;
                        }
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        } else {
                            bean.setAward(0);
                            if (dialog != null && !dialog.isShowing()) {
                                dialog.show();

                            }
                        }
                        bean.setAward(0);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();

                                }
                            }
                        }, 1000);

                    }
                }

                @Override
                public void onHandleError(int code, String message) {
                    super.onHandleError(code, message);
                }
            });
        } catch (Exception e) {

        }
    }

    public void getGvData(int i) {
        try {
            Httpget.executeGet("http://api.m.sm.cn/rest?method=tools.hot&start=1&from=wm160974").enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string();
                    if (s != null) {
                        try {
                            Gson gson = new Gson();
                            List<ReCiBean> bean = gson.fromJson(s, new TypeToken<List<ReCiBean>>() {
                            }.getType());
                            for (int l = 0; l < bean.size(); l++) {
                                datas.add(bean.get(l));
                            }
                        } catch (Exception e) {
                            datas.clear();
                        }
                    }
                }
            });

        } catch (Exception e) {
        }

    }

    @OnClick({R.id.tv_help_back, R.id.tv_look_all, R.id.iv_wx, R.id.iv_detail_qq, R.id.iv_detail_pyq,
            R.id.iv_detail_qqkj, R.id.ll_zan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_help_back:
                finish();
                break;
            case R.id.ll_zan:
                if (!isZan) {
                    isZan = true;
                    int zan = Integer.valueOf(tvZan.getText().toString()) + 1;
                    tvZan.setText(zan + "");
                }
                break;
            case R.id.tv_look_all:
                //LinearLayout 为父容器布局
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                p.setMargins(40, 16, 40, 0);
                wvMainNewxs.setLayoutParams(p); //设置framelayout宽高（MATCH_PARENT，MATCH_PARENT）
                tvLookAll.setVisibility(View.GONE);
                break;
            case R.id.iv_wx:
                map.put("type", "微信");
                Wechat.ShareParams wexin = new Wechat.ShareParams();
                wexin.setShareType(Platform.SHARE_WEBPAGE);
                wexin.title = this.getResources().getString(R.string.app_name);
                wexin.text = title;
                wexin.setComment("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(this) + "红包会更多！");
                url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363789133793";
                wexin.imageUrl = getResources().getString(R.string.shareimage_toutiao);
                wexin.url = url;
                Platform Weixin = ShareSDK.getPlatform(Wechat.NAME);
                Weixin.setPlatformActionListener(new PlatformActionListener() {
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                        LogUtils.e(arg0 + "" + arg1 + "" + arg2 + "wwwww");
                        LogUtils.e("微信回调出错了" + arg0 + "" + arg1 + "" + arg2, "gjj");
                    }

                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        LogUtils.e(arg0 + "" + arg1 + "" + arg2 + "w2wwww");
                        //分享成功的回调
                    }

                    public void onCancel(Platform arg0, int arg1) {
                        LogUtils.e(arg0 + "" + arg1 + "" + "www3ww");
                        //取消分享的回调
                    }
                });
                Weixin.share(wexin);
                break;
            case R.id.iv_detail_qq:
                map.put("type", "QQ");
                QQ.ShareParams qq = new QQ.ShareParams();
                qq.setTitle(this.getResources().getString(R.string.app_name));
                url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881185054";
                qq.setImageUrl(this.getResources().getString(R.string.shareimage_toutiao));
                qq.setTitleUrl(url); // 标题的超链接
                qq.setText(title);
                qq.setComment("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(this) + "红包会更多！\n");
                Platform QQ = ShareSDK.getPlatform(cn.sharesdk.tencent.qq.QQ.NAME);
                QQ.setPlatformActionListener(new PlatformActionListener() {
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                    }

                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        //分享成功的回调
                    }

                    public void onCancel(Platform arg0, int arg1) {
                        //取消分享的回调
                    }
                });
                QQ.share(qq);
                break;
            case R.id.iv_detail_qqkj:
                map.put("type", "QQ空间");
                QZone.ShareParams sp = new QZone.ShareParams();
                sp.setTitle(this.getResources().getString(R.string.app_name));
                sp.setComment("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(this) + "红包会更多");
                url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881484428";
                sp.setImageUrl(this.getResources().getString(R.string.shareimage_toutiao));
                sp.setTitleUrl(url); // 标题的超链接
                sp.setSiteUrl(url);
                sp.setText(title);
                Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                qzone.setPlatformActionListener(new PlatformActionListener() {
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                        LogUtils.e("qq空间回调出错了" + arg2, "gjj");
                    }

                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        //分享成功的回调
                    }

                    public void onCancel(Platform arg0, int arg1) {
                        //取消分享的回调
                    }
                });
                qzone.share(sp);
                break;
            case R.id.iv_detail_pyq:
                map.put("type", "朋友圈");
                WechatMoments.ShareParams weibo = new WechatMoments.ShareParams();
                weibo.setShareType(Platform.SHARE_WEBPAGE);
                weibo.setVenueDescription("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(this) + "红包会更多！");
                weibo.setComment("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(this) + "红包会更多！");
                url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881185053";
                weibo.imageUrl = this.getResources().getString(R.string.shareimage_toutiao);
                weibo.title = title;
                weibo.url = url;
                Platform weibos = ShareSDK.getPlatform(WechatMoments.NAME);
                weibos.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.e("朋友圈空间进来了" + throwable.getMessage(), "gjj");
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                    }
                }); // 设置分享事件回调
                weibos.share(weibo);
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("WrongConstant")
    private void initBanners() {
        i = 1;
        bvs = new BannerView(this, ADSize.BANNER, "1106101702", "3010823292288765");
        bannerContainer.addView(bvs);
        bvs.setADListener(new BannerADListener() {
            @Override
            public void onNoAD(int i) {

            }

            @Override
            public void onADReceiv() {

            }

            @Override
            public void onADExposure() {

            }

            @Override
            public void onADClosed() {

            }

            @Override
            public void onADClicked() {
                map.put("click_banner", "广点通横幅");
                MobclickAgent.onEvent(WebListActivity.this, "banner", map);
            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADOpenOverlay() {

            }

            @Override
            public void onADCloseOverlay() {

            }
        });
        LinearLayout layout_ads = (LinearLayout) findViewById(R.id.xunfeibanner);
        switch (ps) {
            case 7:
                guanGaoUtils.xunfeiBannerAd(this, "D432EADD5E5E01CD7E1933B736C7A68D", layout_ads);
                baidubanner.setVisibility(View.GONE);
                break;
            case 2:
            case 4:
                guanGaoUtils.AdViewBanner("SDK20170914090420rr4pmvotgihhrps", baidubanner);
                guanGaoUtils.xunfeiBannerAd(this, "31BCF98830C245AB67EE9A415EF762F0", layout_ads);
                break;
            case 5:
            case 6:
                guanGaoUtils.AdViewBanner("SDK20170914090420rr4pmvotgihhrps", baidubanner);
                guanGaoUtils.xunfeiBannerAd(this, "66216EC25363F6B41D7BAA4A080C5D2D", layout_ads);
                break;
            default:
                guanGaoUtils.xunfeiBannerAd(this, "D432EADD5E5E01CD7E1933B736C7A68D", layout_ads);
                break;
        }
    }

    //显示CAP4的广告
    private void showMyGG() {
        if (myGG != null && myGG.size() > 0) {
            LogUtils.e("数据出拉多jfk劳动法" + myGG, "gjj");
            in_my_gg.setVisibility(View.VISIBLE);
            tvAdSource.setText(myGG.get(0).getA()+"  广告");
            if (TextUtils.isEmpty(myGG.get(0).getU())){
                tvOpenDown.setText("下载");
            }else {
                tvOpenDown.setText("打开");
            }
            ivCentGg.setText(myGG.get(0).getN());
            Glide.with(getApplicationContext()).load(myGG.get(0).getP()).placeholder(R.mipmap.img_bg).centerCrop().into(gvCentGg);
//            R.id.tv_open_down
            in_my_gg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(myGG.get(0).getU())) {
                        if (dilaog == null) {
                            dilaog = new DowonGgDilaog(WebListActivity.this, R.style.dialog, new DowonGgDilaog.OnDownLoadListner() {
                                @Override
                                public void istrue() {
                                    Intent service = new Intent(WebListActivity.this, DownGGUtils.class);
                                    service.putExtra("downloadurl", myGG.get(0).getW());
                                    service.putExtra("teile", myGG.get(0).getA());
                                    service.putExtra("descrption", myGG.get(0).getN());
                                    startService(service);
                                    dilaog.dismiss();
                                }

                                @Override
                                public void isflas() {
                                    dilaog.dismiss();
                                }
                            });
                        }
                        dilaog.show();
                    } else {
                        Intent intent = new Intent(WebListActivity.this, GGWed.class);
                        intent.putExtra("url", myGG.get(0).getU());
                        startActivity(intent);
                    }
                }
            });
        } else {
            in_my_gg.setVisibility(View.GONE);
        }
    }

}
