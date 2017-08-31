package com.tianguo.zxz.activity.MyActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.datouniao.AdPublisher.AppConfig;
import com.datouniao.AdPublisher.AppConnect;
import com.db.ta.sdk.NonStandardTm;
import com.db.ta.sdk.NsTmListener;
import com.dc.wall.DianCai;
import com.dlnetwork.DevInit;
import com.google.gson.Gson;
import com.qm.pw.Conn;
import com.tianguo.zxz.Flat;
import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.GGWed;
import com.tianguo.zxz.activity.PermissionCheckActivity;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.LoginBean;
import com.tianguo.zxz.bean.MyGGbean;
import com.tianguo.zxz.bean.TuiKaBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.AdvancedCountdownTimer;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.DownGGUtils;
import com.tianguo.zxz.uctils.DowonGgDilaog;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.wmcsk.defaultImp.DefaultGuangaoAdapter;
import com.wmcsk.manager.DoInNumActivityManager;
import com.zy.phone.SDKInit;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/6/8.
 */

public class StarManeyActivity extends BaseActivity {
    @SuppressLint("HandlerLeak")
    @BindView(R.id.bannerContainer)
    ImageView bannerContainer;
    @BindView(R.id.tv_tishi)
    TextView tvTiShi;
    @BindView(R.id.tv_open_meny)
    TextView tvOpenMeny;
    @BindView(R.id.rl_tuia_banner)
    RelativeLayout rl_tuia_banner;
    @BindView(R.id.rl_ky_container)
    RelativeLayout rlKyContainer;
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
    @BindView(R.id.tv_back)
    TextView tvTitle;
    private boolean isStop = false;
    private double v;
    private AdvancedCountdownTimer advancedCountdownTimer;
    private long time = 10000;
    public int sis = 0;
    private List<MyGGbean.Cpa2Bean> myGG;
    private List<MyGGbean.Cpa1Bean> myGG1;
    private DowonGgDilaog dilaog;
    private int type;
    private NonStandardTm mNonStandardTm;
    private String integralWall = "";
    private int lookCount;
    private MyApplictation application;
    private AppConnect appConnectInstance;
    private int cpa1 = 0;
    private AdvancedCountdownTimer cpa1Timer;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_jd_open;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void initView() {
        tvTitle.setText(getIntent().getStringExtra(Constant.TITLE));
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        type = getIntent().getIntExtra("type", 0);
        integralWall = getIntent().getStringExtra(Constant.TITLE);
        lookCount = getIntent().getIntExtra(Constant.LOOK_COUNT, 0);
        if (type == 1) {
            tvTiShi.setText("温馨提示：\n" +
                    "1.停留一分钟，即得20金币奖励\n" +
                    "2.每天可看" + lookCount + "次，每次时间间隔10分钟\n" +
                    "3.点击广告浏览5秒以上，即可获奖励（每天2次机会）,点击不同广告，可以增加大额奖励概率呦！\n" +
                    "4.如果一次进入没有展示广告退出请重新进入，因不同地区广告投放时间可能不同，您可以在一天内持续关注！");
        } else if (type == 3) {
            tvTiShi.setText("温馨提示：\n" +
                    "1.点击带\"广告\"标志的内容，即可获得随机奖励；（每天2次）\n" +
                    "2.进入广告页面后，再次点击可增大随机奖励高额奖励概率哦！\n" +
                    "3.点击实时热搜获得额外惊喜哦！");
        } else if (type == 4) {
            //初始化积分墙
            initIntegralWall();
            //积分墙
            tvTiShi.setText("赚钱小技巧：\n" +
                    "1.下载安装后体验几分钟钱就到啦~\n" +
                    "2.新用户有任务20-50个左右，每天都不定时更新；\n" +
                    "3.安装完成不卸载还可以追加奖励，推荐指数五颗星！");
        } else {

            tvTiShi.setText(
                    "温馨提示：\n" +
                            "1.停留一分钟，即得20金币奖励\n" +
                            "2.每天可看" + lookCount + "次，每次时间间隔5分钟\n" +
                            "3.如果一次进入没有展示广告退出请重新进入，因不同地区广告投放时间可能不同，您可以在一天内持续关注！");

        }
        tvTiShi.setVisibility(View.VISIBLE);
        myGG = Flat.list;
        myGG1 = Flat.cp1;
        MyGG();
//        //获取推啊和快眼的广告开关
        if (rl_tuia_banner.getTag() != null) {
            String data = SharedPreferencesUtil.getSwitch(this, rl_tuia_banner.getTag().toString());
            rl_tuia_banner.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(data)) {
                //加载推啊广告
                initTuia();
            } else {
                initCPA1();
//                rlKyContainer.setVisibility(View.VISIBLE);
            }
        }

    }

    private void initCPA1() {
        if (isStop) {
            return;
        }
        cap1Time();
        if (myGG1 != null && myGG1.size() > 0) {
            if (cpa1 >= myGG1.size()) {
                cpa1 = 0;
            }
            final int point = cpa1++;
            LogUtils.e("广告出来了"+myGG1.size(),"gjj");
            Glide.with(StarManeyActivity.this).load(myGG1.get(point).getP()).placeholder(R.mipmap.img_bg).centerCrop().into(bannerContainer);
            bannerContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(myGG1.get(point).getU())) {
                        if (dilaog == null) {
                            dilaog = new DowonGgDilaog(StarManeyActivity.this, R.style.dialog, new DowonGgDilaog.OnDownLoadListner() {
                                @Override
                                public void istrue() {
                                    Intent service = new Intent(StarManeyActivity.this, DownGGUtils.class);
                                    service.putExtra("downloadurl", myGG1.get(point).getW());
                                    service.putExtra("teile", myGG1.get(point).getA());
                                    service.putExtra("descrption", myGG1.get(point).getN());
                                    startService(service);
                                }

                                @Override
                                public void isflas() {

                                }
                            });
                        }
                    } else {
                        Intent intent = new Intent(StarManeyActivity.this, GGWed.class);
                        intent.putExtra("url", myGG1.get(point).getU());
                        startActivity(intent);
                    }

                }
            });
        }
    }

    private void cap1Time() {
        cpa1Timer = new AdvancedCountdownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished, int percent) {
            }

            @Override
            public void onFinish() {
                time = 10000;
                initCPA1();
            }
        };
        cpa1Timer.start();
    }



    private void initTuia() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        ViewGroup.LayoutParams linearParams = bannerContainer.getLayoutParams();
        linearParams.width = defaultDisplay.getWidth();
        LogUtils.e(linearParams.width + "");
        double i = 0.15;
        v = defaultDisplay.getWidth() * i;
        linearParams.height = (int) v;
        bannerContainer.setLayoutParams(linearParams);
        mNonStandardTm = new NonStandardTm(this);
        mNonStandardTm.loadAd(2915);
        mNonStandardTm.adExposed();
        mNonStandardTm.setAdListener(new NsTmListener() {
            @Override
            public void onReceiveAd(String s) {
                try {
                    mNonStandardTm.adExposed();
                    Gson gson = new Gson();
                    final TuiKaBean bean = gson.fromJson(s, TuiKaBean.class);
                    if (bean != null) {
                        LogUtils.e("dizhi" + bean.getImg_url());
                        Glide.with(getApplicationContext())
                                .load(bean.getImg_url())
                                .centerCrop()
                                .into(bannerContainer);
                        bannerContainer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    mNonStandardTm.adClicked();
                                    Intent intent = new Intent(StarManeyActivity.this, GGWed.class);
                                    intent.putExtra("url", bean.getClick_url());
                                    intent.putExtra(Constant.TITLE, bean.getAd_title());
                                    startActivity(intent);
                                } catch (Exception e) {
                                    LogUtils.e(e.toString());
                                }
                            }
                        });
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailedToReceiveAd() {

            }
        });
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
    public void onPause() {
        super.onPause();

    }

    public void time() {
        advancedCountdownTimer = new AdvancedCountdownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished, int percent) {
            }

            @Override
            public void onFinish() {
                time = 10000;
                MyGG();
            }
        };
        advancedCountdownTimer.start();
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onStart() {
        super.onStart();
        isStop = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;

    }

    private void initIntegralWall() {
        //点财
        DianCai.initApp(this, "16185", "d21f058ebe594338bf5b6fd5d33e4523");
        DianCai.setUserId(String.valueOf(SharedPreferencesUtil.getID(this)));
        //趣米
        Conn.getInstance(this).set("51e22c264bbc2476", "5f2075703efb1283", String.valueOf(SharedPreferencesUtil.getID(this)));
        //中亿
        SDKInit.initAd(this, "373a370b329bfde4", String.valueOf(SharedPreferencesUtil.getID(this)));
        //点乐
        DevInit.initGoogleContext(this, "c71bbe9b83e50aa0a83ca62f8af7e9d2");
        DevInit.setCurrentUserID(this, String.valueOf(SharedPreferencesUtil.getID(this)));
        //大头鸟
        AppConfig config = new AppConfig();
        config.setAppID("38f40bd3-760e-49c2-8167-b35574a1a296");
        config.setSecretKey("skvqjtzrktcw");
        config.setCtx(this);
        config.setClientUserID(String.valueOf(SharedPreferencesUtil.getID(this)));
        appConnectInstance = AppConnect.getInstance(config);
    }

    @OnClick({R.id.iv_back, R.id.tv_open_meny})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_open_meny:
                if (type == 3) {
                    Intent intent = new Intent(StarManeyActivity.this, MainActivity.class);
                    intent.putExtra("type", 3);
                    startActivity(intent);
                    finish();
                } else if (type == 4) {
                    switch (integralWall) {
                        case Constant.YOUMI:
                            startActivity(new Intent(StarManeyActivity.this, PermissionCheckActivity.class));
                            break;
                        case Constant.DIANCAI:
                            DianCai.showOfferWall();
                            break;
                        case Constant.QUMI:
                            Conn.getInstance(this).launch();
                            break;
                        case Constant.ZHOGNYI:
                            SDKInit.initAdList(this);
                            break;
                        case Constant.DIANLE:
                            DevInit.showOffers(this);
                            break;
                        case Constant.DATOUNIAO:
                            appConnectInstance.ShowAdsOffers();
                            break;


                    }
                } else {
                    finish();
                    getLogin();
                }

                break;
        }
    }

    @SuppressLint("WrongConstant")
    public void MyGG() {
        if (isStop) {
            return;
        }
        time();
        if (myGG != null && myGG.size() > 0) {
            if (sis >= myGG.size()) {
                sis = 0;
            }
            final int point = sis++;
            inMygg.setVisibility(View.VISIBLE);
            tvTeileGg.setText(myGG.get(point).getA());
            ivCentGg.setText(myGG.get(point).getN());
            Glide.with(StarManeyActivity.this).load(myGG.get(point).getP()).placeholder(R.mipmap.img_bg).centerCrop().into(gvCentGg);
            Glide.with(StarManeyActivity.this).load(myGG.get(point).getC()).placeholder(R.mipmap.img_bg).centerCrop().into(ivIconeGg);
            inMygg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(myGG.get(point).getU())) {
                        if (dilaog == null) {
                            dilaog = new DowonGgDilaog(StarManeyActivity.this, R.style.dialog, new DowonGgDilaog.OnDownLoadListner() {
                                @Override
                                public void istrue() {
                                    Intent service = new Intent(StarManeyActivity.this, DownGGUtils.class);
                                    service.putExtra("downloadurl", myGG.get(point).getW());
                                    service.putExtra("teile", myGG.get(point).getA());
                                    service.putExtra("descrption", myGG.get(point).getN());
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
                        Intent intent = new Intent(StarManeyActivity.this, GGWed.class);
                        intent.putExtra("url", myGG.get(point).getU());
                        startActivity(intent);
                    }

                }
            });
        } else {
            inMygg.setVisibility(View.GONE);
        }
    }

    public void getLogin() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("earnType", getIntent().getIntExtra("type", 0));
        map.put("sso", SharedPreferencesUtil.getSSo(StarManeyActivity.this));
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getManery(map);

        logins.compose(composeFunction).subscribe(new BaseObserver<LoginBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LoginBean loginBean) {
                Intent intent = new Intent(StarManeyActivity.this, OpenManeryActivity.class);
                intent.putExtra("type", getIntent().getIntExtra("type", 0));
                intent.putExtra("wMd5", loginBean.getwMd5());
                intent.putExtra(Constant.TITLE, getIntent().getStringExtra(Constant.TITLE));
                startActivity(intent);
            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
                LogUtils.e("出问题了" + message);
            }
        });

    }

}