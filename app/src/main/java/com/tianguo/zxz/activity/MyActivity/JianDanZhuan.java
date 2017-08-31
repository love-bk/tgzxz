package com.tianguo.zxz.activity.MyActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.Flat;
import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.GGWed;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.LoginBean;
import com.tianguo.zxz.bean.LookBean;
import com.tianguo.zxz.bean.MyGGbean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.AdvancedCountdownTimer;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.DownGGUtils;
import com.tianguo.zxz.uctils.DowonGgDilaog;
import com.tianguo.zxz.uctils.GuanGaoUtils;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/5/31.
 */

public class JianDanZhuan extends BaseActivity {
    @BindView(R.id.ll_chuantou)
    LinearLayout llChuantou;
    @BindView(R.id.iv_icone_gg)
    ImageView ivIconeGg;
    @BindView(R.id.tv_teile_gg)
    TextView tvTeileGg;
    @BindView(R.id.iv_cent_gg)
    TextView ivCentGg;
    @BindView(R.id.gv_cent_gg)
    ImageView gvCentGg;
    @BindView(R.id.tv_look_num)
    TextView tvLookNum;
    @BindView(R.id.tv_dian_num)
    TextView tvDianNum;
    @BindView(R.id.tv_back)
    TextView tvBack;
    private Intent intent;
    @BindView(R.id.in_my_gg)
    LinearLayout inMygg;
    private GuanGaoUtils guanGaoUtils;
    private AdvancedCountdownTimer advancedCountdownTimer;
    long time = 10000;
    private boolean isStop = false;
    private int sis = 0;
    private List<MyGGbean.Cpa2Bean> myGG;
    private DowonGgDilaog dilaog;
    private HashMap<String, String> map = new HashMap<>();
    private int lookLookTotal;
    private int lookDianTotal;

    private boolean requestFlag = true;
    private MyApplictation application;


    @Override
    protected int getContentViewId() {
        return R.layout.activty_jiandana;
    }

    @Override
    protected void initView() {
        tvBack.setText(getIntent().getStringExtra(Constant.TITLE));
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        intent = new Intent(JianDanZhuan.this, StarManeyActivity.class);
        guanGaoUtils = new GuanGaoUtils(false, this);
        MyGG();
        llChuantou.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        getLookNum();
    }

    @Override
    public void onPause() {
        super.onPause();
        requestFlag = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!requestFlag) {
            getLookNum();
        }
    }

    private void getLookNum() {
        setLoadingFlag(false);
        Map<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(JianDanZhuan.this));
        Observable logins = RetroFactory.getInstance().getLookNum(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<LookBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LookBean lookBean) {
                if (lookBean != null) {
                    lookLookTotal = lookBean.getLookTotal();
                    lookDianTotal = lookBean.getWatchTotal();
                    tvLookNum.setText(lookBean.getLookCount() + "/" + lookBean.getLookTotal());
                    tvDianNum.setText(lookBean.getWatchCount() + "/" + lookBean.getWatchTotal());
                }
            }
        });
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


    @Override
    protected void onDestroy() {
        try {


            application.removeActivity(this);
        } catch (Exception e) {

        }
        super.onDestroy();

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

    @SuppressLint("WrongConstant")
    public void MyGG() {
        if (isStop) {
            return;
        }
        time();
        myGG = Flat.list;
        if (myGG != null && myGG.size() > 0) {
            if (sis >= myGG.size()) {
                sis = 0;
            }
            final int point = sis++;
            LogUtils.e(point + "wwwwwww");
            inMygg.setVisibility(View.VISIBLE);
            tvTeileGg.setText(myGG.get(point).getA());
            ivCentGg.setText(myGG.get(point).getN());
            Glide.with(JianDanZhuan.this).load(myGG.get(point).getP()).placeholder(R.mipmap.img_bg).centerCrop().into(gvCentGg);
            Glide.with(JianDanZhuan.this).load(myGG.get(point).getC()).placeholder(R.mipmap.img_bg).centerCrop().into(ivIconeGg);
            inMygg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(myGG.get(point).getU())) {
                        if (dilaog == null) {
                            dilaog = new DowonGgDilaog(JianDanZhuan.this, R.style.dialog, new DowonGgDilaog.OnDownLoadListner() {
                                @Override
                                public void istrue() {
                                    Intent service = new Intent(JianDanZhuan.this, DownGGUtils.class);
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
                        Intent intent = new Intent(JianDanZhuan.this, GGWed.class);
                        intent.putExtra("url", myGG.get(point).getU());
                        startActivity(intent);
                    }

                }
            });
        } else {
            inMygg.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_back, R.id.rl_zhuan_see, R.id.rl_zhuan_see_see, R.id.rl_zhuan_cliner, R.id.rl_zhuan_sharp, R.id.rl_down_maney, R.id.rl_lainmeng_maney})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.rl_lainmeng_maney://有米积分墙
//                startActivity(new Intent(JianDanZhuan.this, PermissionCheckActivity.class));
                startActivity(new Intent(JianDanZhuan.this, IntegralActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_zhuan_see://看看赚
                map.put("click_siple_money", "看赚");
                MobclickAgent.onEvent(JianDanZhuan.this, "feed", map);
                if (intent !=null){
                    intent.putExtra("type", 1);
                    intent.putExtra(Constant.TITLE,getString(R.string.looklook));
                    intent.putExtra(Constant.LOOK_COUNT, lookLookTotal);
                    startActivity(intent);
                }
                break;
            case R.id.rl_zhuan_see_see://看点赚
                map.put("click_siple_money", "看看");
                MobclickAgent.onEvent(JianDanZhuan.this, "feed", map);
                if (intent !=null){
                    intent.putExtra("type", 2);
                    intent.putExtra(Constant.TITLE,getString(R.string.look_point));
                    intent.putExtra(Constant.LOOK_COUNT, lookLookTotal);
                    startActivity(intent);
                }

                break;
            case R.id.rl_zhuan_cliner:
                getLogin();
                break;
            case R.id.rl_zhuan_sharp:
                map.put("click_siple_money", "阅读");
                MobclickAgent.onEvent(JianDanZhuan.this, "feed", map);
                if (intent !=null){
                    intent.putExtra("type", 3);
                    intent.putExtra(Constant.TITLE,getString(R.string.read_make_money));
                    startActivity(intent);
                }
                break;
            case R.id.rl_down_maney:
                map.put("click_siple_money", "下载");
                MobclickAgent.onEvent(JianDanZhuan.this, "feed", map);
                Intent intent = new Intent(JianDanZhuan.this, YYXZActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.e(requestCode + "sss" + resultCode);
        if (resultCode == 202) {
            setResult(202);
            finish();
        }
    }


    @OnClick(R.id.rl_video_maney)
    public void onClinkVedio() {
        try {
            map.put("click_siple_money", "视频");
            MobclickAgent.onEvent(JianDanZhuan.this, "feed", map);
            guanGaoUtils.PlayViedo("0a42c9f80cc8a02ac9c8eab0339d46c291182640", JianDanZhuan.this);
        } catch (Exception e) {

        }
    }


    public void getLogin() {
        map.put("click_siple_money", "点赚");
        MobclickAgent.onEvent(JianDanZhuan.this, "feed", map);
        HashMap<String, Object> map = new HashMap<>();
        map.put("earnType", 3);
        map.put("sso", SharedPreferencesUtil.getSSo(JianDanZhuan.this));
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getManery(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<LoginBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LoginBean loginBean) {

                Intent intent = new Intent(JianDanZhuan.this, OpenManeryActivity.class);
                intent.putExtra("type", 3);
                intent.putExtra(Constant.TITLE,getString(R.string.point_make_money));
                startActivity(intent);

            }
        });

    }

}
