package com.tianguo.zxz.activity.MyActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.db.ta.sdk.NonStandardTm;
import com.db.ta.sdk.NsTmListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.tianguo.zxz.Flat;
import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.GGWed;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.EastHeadlineBean;
import com.tianguo.zxz.bean.LoginBean;
import com.tianguo.zxz.bean.MyGGbean;
import com.tianguo.zxz.bean.ReCiBean;
import com.tianguo.zxz.bean.TuiKaBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.AdvancedCountdownTimer;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.DownGGUtils;
import com.tianguo.zxz.uctils.DowonGgDilaog;
import com.tianguo.zxz.uctils.GuanGaoUtils;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.MD5;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/6/9.
 */

public class OpenManeryActivity extends BaseActivity {
    @BindView(R.id.tv_east_title)
    TextView tvEastTitle;
    @BindView(R.id.iv_east_img)
    ImageView ivEastImg;
    @BindView(R.id.ll_east)
    LinearLayout llEast;
    @SuppressLint("HandlerLeak")
    private Intent intent;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.bannerContainer)
    ImageView bannerContainer;
    @BindView(R.id.xunfeibanner)
    LinearLayout xunfeibanner;
    @BindView(R.id.xunfeibanner2)
    LinearLayout xunfeibanne2;
    @BindView(R.id.baidubanner)
    LinearLayout baidubanner;
    @BindView(R.id.ll_cent_hengfu)
    LinearLayout llCentHengfu;
    @BindView(R.id.bannerimae)
    ImageView bannerimae;
    @BindView(R.id.tv_text1)
    TextView tv_text1;
    @BindView(R.id.iv_icone_gg)
    ImageView ivIconeGg;
    @BindView(R.id.tv_teile_gg)
    TextView tvTeileGg;
    @BindView(R.id.iv_cent_gg)
    TextView ivCentGg;
    @BindView(R.id.gv_cent_gg)
    ImageView gvCentGg;
    @BindView(R.id.in_my_gg)
    LinearLayout inMygg;
    @BindView(R.id.rl_banner)
    RelativeLayout rl_banner;
    @BindView(R.id.rl_bannerimage)
    RelativeLayout rl_bannerimage;
    @BindView(R.id.disanfang_xiaomi)
    ImageView XiaoMi;
    @BindView(R.id.gg_time)
    TextView ggtime;
    private double v;
    private AdvancedCountdownTimer advancedCountdownTimer;
    public int time = 60;
    private MD5 md5;
    private String wMd5;
    private Dialog dialog;
    private int count;
    private String md5s;
    private boolean isgg = false;
    private ImageView iv_icone_gg;
    private TextView tv_teile_gg;
    private TextView iv_cent_gg;
    private ImageView gv_cent_gg;
    private LinearLayout in_my_gg;
    int i = 1;
    private TextView cent;
    private long times = 10000;
    private boolean isStop = false;
    private int sis = 0;
    private List<MyGGbean.Cpa2Bean> myGG;
    private List<MyGGbean.Cpa1Bean> myGG1;

    private DowonGgDilaog dilaog;
    private NonStandardTm mNonStandardTm;
    private AdvancedCountdownTimer atime;
    private int percents = 5;
    private MyApplictation application;
    private String eastHeadlineUrl;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_onpenmanery_see;
    }

    private boolean istrue = true;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        tvBack.setText(getIntent().getStringExtra(Constant.TITLE));
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        try {
            MobileAds.initialize(this, "ca-app-pub-1819307126738194~9911430468");
            getIntent().getParcelableArrayExtra("myGG");
            GuanGaoUtils guanGaoUtils = new GuanGaoUtils(true, this);
            dialog = new Dialog(this, R.style.dialog);
            View inflate = View.inflate(this, R.layout.dialog_sese, null);
            inflate.findViewById(R.id.tv_sese_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();
                }
            });
            inflate.findViewById(R.id.tv_sese_bark).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            cent = (TextView) inflate.findViewById(R.id.tv_sese_cent);
            iv_icone_gg = (ImageView) inflate.findViewById(R.id.iv_icone_gg);
            tv_teile_gg = (TextView) inflate.findViewById(R.id.tv_teile_gg);
            iv_cent_gg = (TextView) inflate.findViewById(R.id.iv_cent_gg);
            gv_cent_gg = (ImageView) inflate.findViewById(R.id.gv_cent_gg);
            in_my_gg = (LinearLayout) inflate.findViewById(R.id.in_my_gg);
            dialog.setContentView(inflate);
            md5 = new MD5();
            wMd5 = md5.getMD5ofStr(getIntent().getStringExtra("wMd5") + "user_watch");
            if (getIntent().getIntExtra("type", 0) != 3) {
                ggtime.setVisibility(View.VISIBLE);
                advancedCountdownTimer = new AdvancedCountdownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished, int percent) {
                        time--;
                        ggtime.setText(time + "");
                        if (time <= 1) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                        cent.setText("您还有" + time + "秒就到1分钟了，再坚持一下就有钱了哦！~~");

                    }

                    @Override
                    public void onFinish() {
                        LogUtils.e(time + "sssssssssssssssss");
                        if (getIntent().getIntExtra("type", 0) != 3) {
                            if (time <= 1 && istrue) {
                                getLogin();
                                ggtime.setText(0 + "");
                            }
                        }
                    }
                };
                advancedCountdownTimer.start();
            } else {
                ggtime.setVisibility(View.GONE);

            }
            //推啊的banner广告
            getTuiaBanner();
            guanGaoUtils.setFhithListner(new GuanGaoUtils.onFhithListner() {
                @Override
                public void onfhioth() {

                }

                @Override
                public void onCLine() {
                    if (count < 3) {
                        onlicn();
                        isgg = true;
                    }
                }

                @Override
                public void onShow() {
                }
            });
            findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.e(time + "");
                    if (time > 1 && getIntent().getIntExtra("type", 0) != 3) {
                        i = 0;
                        MyGG(i);
                        if (dialog != null && !dialog.isShowing()) {
                            dialog.show();
                        }

                    } else {
                        finish();
                    }
                }
            });

            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdOpened() {
                    if (count < 3) {
                        onlicn();
                        isgg = true;
                    }
                }
            });
            myGG = Flat.list;
            myGG1 = Flat.cp1;
            MyGG(i);
            //讯 飞
            guanGaoUtils.xunfeiBannerAd(this, "66216EC25363F6B41D7BAA4A080C5D2D", xunfeibanner);
            //Adview
            guanGaoUtils.AdViewBanner("SDK20170914090420rr4pmvotgihhrps", baidubanner);
            //讯 飞
            guanGaoUtils.xunfeiBannerAd(this, "31BCF98830C245AB67EE9A415EF762F0", xunfeibanne2);

            //加载东方头条
            if (getIntent().getIntExtra("type", 0) == 3 &&
                    (TextUtils.isEmpty(SharedPreferencesUtil.getEast(this)) ||
                            (!TextUtils.isEmpty(SharedPreferencesUtil.getEast(this)) &&
                                    !SharedPreferencesUtil.getEast(this).equals(getNowDate(new Date()))))) {
                getEastHeadline();
            } else {
                xunfeibanne2.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }
    }

    //推啊的banner广告
    private void getTuiaBanner() {
        mNonStandardTm = new NonStandardTm(this);
        mNonStandardTm.loadAd(2915);
        mNonStandardTm.setAdListener(new NsTmListener() {
            @Override
            public void onReceiveAd(String s) {
                mNonStandardTm.adExposed();
                Gson gson = new Gson();
                final TuiKaBean bean = gson.fromJson(s, TuiKaBean.class);
                if (bean != null) {
                    LogUtils.e("推啊：" + bean.getImg_url());
                    Glide.with(OpenManeryActivity.this)
                            .load(bean.getImg_url())
                            .centerCrop()
                            .into(XiaoMi);
                    XiaoMi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                mNonStandardTm.adClicked();
                                LogUtils.e("sssssss");
                                if (count < 3) {
                                    onlicn();
                                    isgg = true;
                                }
                                Intent intent = new Intent(OpenManeryActivity.this, GGWed.class);
                                intent.putExtra("url", bean.getClick_url());
                                intent.putExtra(Constant.TITLE, bean.getAd_title());
                                startActivity(intent);
                            } catch (Exception e) {
                                LogUtils.e(e.toString());
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailedToReceiveAd() {

            }
        });
    }

    public void time() {
        advancedCountdownTimer = new AdvancedCountdownTimer(times, 1000) {
            @Override
            public void onTick(long millisUntilFinished, int percent) {
            }

            @Override
            public void onFinish() {
                times = 10000;
                i = 1;
                MyGG(i);
            }
        };
        advancedCountdownTimer.start();
    }

    @Override
    protected void initData() {
    }


    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;
        if (getIntent().getIntExtra("type", 0) != 3) {
            if (advancedCountdownTimer != null) {
                advancedCountdownTimer.pause();
            }
        }

//        if (atime!=null)
//            atime.pause();

    }

    @Override
    protected void onStart() {
        super.onStart();
        isStop = false;
        if (getIntent().getIntExtra("type", 0) != 3) {
            if (advancedCountdownTimer != null) {
                advancedCountdownTimer.start();
            }

        }

    }


    @Override
    public void onBackPressed() {
        LogUtils.e(time + "");
        if (time > 1 && getIntent().getIntExtra("type", 0) != 3) {
            i = 0;
            MyGG(i);
            if (dialog != null && !dialog.isShowing()) {

                dialog.show();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //倒计时看看红包
    public void getLogin() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(OpenManeryActivity.this));
        map.put("type", getIntent().getIntExtra("type", 0));
        map.put("result", wMd5);
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getSeeSee(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<ReCiBean>(this, pd) {
            @Override
            protected void onHandleSuccess(ReCiBean loginBean) {
                final Dialog dialog = new Dialog(OpenManeryActivity.this, R.style.dialog);
                View inflate = View.inflate(OpenManeryActivity.this, R.layout.dialog_hongbao, null);
                TextView tv_manery_text = (TextView) inflate.findViewById(R.id.tv_manery_text);
                dialog.setContentView(inflate);
                tv_manery_text.setText(loginBean.getAward() + "");
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 1000);
                istrue = false;
            }
        });

    }

    //点击广告红包
    public void onlicn() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(OpenManeryActivity.this));
        map.put("earnType", getIntent().getIntExtra("type", 0));
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getOncliGG(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<LoginBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LoginBean loginBean) {
                count = loginBean.getCount();
                md5s = loginBean.getwMd5();
                //需要把之前的计时器取消
                if (atime!=null){
                    atime.cancel();
                    percents = 5;
                }
                atime = new AdvancedCountdownTimer(6000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished, int percent) {
                        percents--;
                        LogUtils.e("时间出来了"+System.currentTimeMillis());
                    }

                    @Override
                    public void onFinish() {
                        LogUtils.e(percents + "sssssssssssssssss");

                        if (isgg && count < 3 && percents < 1) {
                            LogUtils.e("时间出来了zuihou"+System.currentTimeMillis());
                            backCliner();
                            isgg = false;
                        }
                    }
                };
                atime.start();
            }
        });

    }

    //退出广告红包
    public void backCliner() {
        LogUtils.e(md5s);
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(OpenManeryActivity.this));
        map.put("type", getIntent().getIntExtra("type", 0));
        map.put("result", new MD5().getMD5ofStr(md5s + "user_ad"));
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getBackGG(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<ReCiBean>(this, pd) {
            @Override
            protected void onHandleSuccess(ReCiBean loginBean) {
                ToastUtil.showMessage("恭喜你获得了"+loginBean.getAward()+"金币奖励");
            }
        });

    }

    @Override
    protected void onDestroy() {
        try {

            try {
                application.removeActivity(this);
            } catch (Exception e) {

            }
            Glide.with(this).pauseRequests();
            if (advancedCountdownTimer != null)
                advancedCountdownTimer.cancel();
            if (atime!=null)
                atime.cancel();
            if (dialog != null || dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            if (dilaog != null || dilaog.isShowing()) {
                dilaog.dismiss();
                dilaog = null;
            }
        } catch (Exception e) {

        }
        super.onDestroy();
    }

    @SuppressLint("WrongConstant")
    public void MyGG(int i) {
        if (isStop) {
            return;
        }
        time();
        if (myGG != null && myGG.size() > 0) {
            if (sis >= myGG.size()) {
                sis = 0;
            }
            final int point = sis++;
            if (i == 1) {

                Glide.with(OpenManeryActivity.this).load(myGG1.get(point).getP()).placeholder(R.mipmap.img_bg).centerCrop().into(bannerContainer);
                bannerContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(myGG1.get(point).getU())) {
                            if (dilaog == null) {
                                dilaog = new DowonGgDilaog(OpenManeryActivity.this, R.style.dialog, new DowonGgDilaog.OnDownLoadListner() {
                                    @Override
                                    public void istrue() {
                                        Intent service = new Intent(OpenManeryActivity.this, DownGGUtils.class);
                                        service.putExtra("downloadurl", myGG1.get(point).getW());
                                        service.putExtra("teile", myGG1.get(point).getA());
                                        service.putExtra("descrption", myGG1.get(point).getN());
                                        startService(service);
                                        if (dilaog != null && dialog.isShowing()) {
                                            dilaog.dismiss();

                                        }
                                    }

                                    @Override
                                    public void isflas() {
                                        if (dilaog != null && dialog.isShowing()) {

                                            dilaog.dismiss();
                                        }
                                    }
                                });
                            }
                            if (dilaog != null && !dialog.isShowing()) {

                                dilaog.show();
                            }

                        } else {
                            Intent intent = new Intent(OpenManeryActivity.this, GGWed.class);
                            intent.putExtra("url", myGG1.get(point).getU());
                            startActivity(intent);
                        }

                    }
                });
                inMygg.setVisibility(View.VISIBLE);
                tvTeileGg.setText(myGG.get(point).getA());
                ivCentGg.setText(myGG.get(point).getN());
                Glide.with(OpenManeryActivity.this).load(myGG.get(point).getP()).placeholder(R.mipmap.img_bg).centerCrop().into(gvCentGg);
                Glide.with(OpenManeryActivity.this).load(myGG.get(point).getC()).placeholder(R.mipmap.img_bg).centerCrop().into(ivIconeGg);
                inMygg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(myGG.get(point).getU())) {
                            if (dilaog == null) {
                                dilaog = new DowonGgDilaog(OpenManeryActivity.this, R.style.dialog, new DowonGgDilaog.OnDownLoadListner() {
                                    @Override
                                    public void istrue() {
                                        Intent service = new Intent(OpenManeryActivity.this, DownGGUtils.class);
                                        service.putExtra("downloadurl", myGG.get(point).getW());
                                        service.putExtra("teile", myGG.get(point).getA());
                                        service.putExtra("descrption", myGG.get(point).getN());
                                        startService(service);
                                        if (dilaog != null && dialog.isShowing()) {
                                            dilaog.dismiss();

                                        }
                                    }

                                    @Override
                                    public void isflas() {
                                        if (dilaog != null && dialog.isShowing()) {

                                            dilaog.dismiss();
                                        }
                                    }
                                });
                            }
                            if (dilaog != null && !dialog.isShowing()) {

                                dilaog.show();
                            }

                        } else {
                            Intent intent = new Intent(OpenManeryActivity.this, GGWed.class);
                            intent.putExtra("url", myGG.get(point).getU());
                            startActivity(intent);
                        }

                    }
                });
            } else {
                in_my_gg.setVisibility(View.VISIBLE);
                tv_teile_gg.setText(myGG.get(point).getA());
                iv_cent_gg.setText(myGG.get(point).getN());
                Glide.with(OpenManeryActivity.this).load(myGG.get(point).getP()).placeholder(R.mipmap.img_bg).centerCrop().into(gv_cent_gg);
                Glide.with(OpenManeryActivity.this).load(myGG.get(point).getC()).placeholder(R.mipmap.img_bg).centerCrop().into(iv_icone_gg);
                in_my_gg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(myGG.get(point).getU())) {
                            Intent service = new Intent(OpenManeryActivity.this, DownGGUtils.class);
                            service.putExtra("downloadurl", myGG.get(point).getW());
                            service.putExtra("teile", myGG.get(point).getA());
                            service.putExtra("descrption", myGG.get(point).getN());
                            startService(service);
                        } else {
                            Intent intent = new Intent(OpenManeryActivity.this, GGWed.class);
                            intent.putExtra("url", myGG.get(point).getU());
                            startActivity(intent);
                        }

                    }
                });
            }
        } else {
            inMygg.setVisibility(View.GONE);
        }
    }


    //东方头条的点击事件
    @OnClick(R.id.ll_east)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(eastHeadlineUrl)) {
            if (count < 3) {
                onlicn();
                isgg = true;
            }
            //点击之后，下次进来后不能再展示
            SharedPreferencesUtil.saveEast(this, getNowDate(new Date()));
            llEast.setVisibility(View.GONE);
            xunfeibanne2.setVisibility(View.VISIBLE);
            Intent intent = new Intent(OpenManeryActivity.this, GGWed.class);
            intent.putExtra("url", eastHeadlineUrl);
            startActivity(intent);
        }
    }

    public static String getNowDate(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(currentTime);
    }

    public void getEastHeadline() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        final Observable eastHeadline = RetroFactory.getInstance().getEastHeadline(map);
        eastHeadline.compose(composeFunction).subscribe(new BaseObserver<EastHeadlineBean>(this, pd) {
            @Override
            protected void onHandleSuccess(EastHeadlineBean eastHeadlineBean) {
                if (eastHeadlineBean != null) {
                    eastHeadlineUrl = eastHeadlineBean.getUrl();
                    llEast.setVisibility(View.VISIBLE);
                    Glide.with(OpenManeryActivity.this).load(eastHeadlineBean.getPic()).
                            placeholder(R.mipmap.img_bg).centerCrop().into(ivEastImg);
                    tvEastTitle.setText(eastHeadlineBean.getTitle());
                } else {
                    xunfeibanne2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
                xunfeibanne2.setVisibility(View.VISIBLE);
            }
        });

    }

}
