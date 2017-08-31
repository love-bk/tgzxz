package com.tianguo.zxz.activity.MyActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
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
import com.tianguo.zxz.bean.MyGGbean;
import com.tianguo.zxz.uctils.AdvancedCountdownTimer;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.DownGGUtils;
import com.tianguo.zxz.uctils.DowonGgDilaog;
import com.tianguo.zxz.uctils.GuanGaoUtils;
import com.tianguo.zxz.uctils.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lx on 2017/7/5.
 */

public class IntegralActivity extends BaseActivity {
    @BindView(R.id.iv_icone_gg)
    ImageView ivIconeGg;
    @BindView(R.id.tv_teile_gg)
    TextView tvTeileGg;
    @BindView(R.id.iv_cent_gg)
    TextView ivCentGg;
    @BindView(R.id.gv_cent_gg)
    ImageView gvCentGg;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.in_my_gg)
    LinearLayout inMygg;
    private List<MyGGbean.Cpa2Bean> myGG;
    private boolean isStop = false;
    private int sis = 0;
    private DowonGgDilaog dilaog;
    private AdvancedCountdownTimer advancedCountdownTimer;
    long time = 10000;
    private Map<String, String> map = new HashMap<>();
    private String skip_mark;
    private MyApplictation application;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_integral;
    }

    @Override
    protected void onDestroy() {
        try{


            application.removeActivity(this);
        }catch (Exception e){

        }
        super.onDestroy();

    }
    @Override
    protected void initView() {
        tvBack.setText(R.string.download_make_money);
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        skip_mark = getIntent().getStringExtra(Constant.SKIP_MARK);
        GuanGaoUtils guanGaoUtils = new GuanGaoUtils(false, this);
        guanGaoUtils.getMyGG(new GuanGaoUtils.OnMyGGListner() {
            @Override
            public void onmylistner(List<MyGGbean.Cpa2Bean> cp2) {
                myGG = cp2;
                Flat.list = cp2;
                MyGG();
            }

            @Override
            public void onMyggcp1(List<MyGGbean.Cpa1Bean> cp1) {

            }

            @Override
            public void onMyggcp3(List<MyGGbean.Cpa3Bean> cpa3) {

            }

            @Override
            public void onMyggcp4(List<MyGGbean.Cpa4Bean> cpa4) {

            }
        });

    }


    @Override
    protected void initData() {

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
        if (myGG != null && myGG.size() > 0) {
            if (sis >= myGG.size()) {
                sis = 0;
            }
            final int point = sis++;
            LogUtils.e(point + "wwwwwww");
            inMygg.setVisibility(View.VISIBLE);
            tvTeileGg.setText(myGG.get(point).getA());
            ivCentGg.setText(myGG.get(point).getN());
            Glide.with(IntegralActivity.this).load(myGG.get(point).getP()).placeholder(R.mipmap.img_bg).centerCrop().into(gvCentGg);
            Glide.with(IntegralActivity.this).load(myGG.get(point).getC()).placeholder(R.mipmap.img_bg).centerCrop().into(ivIconeGg);
            inMygg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(myGG.get(point).getU())) {
                        if (dilaog == null) {
                            dilaog = new DowonGgDilaog(IntegralActivity.this, R.style.dialog, new DowonGgDilaog.OnDownLoadListner() {
                                @Override
                                public void istrue() {
                                    Intent service = new Intent(IntegralActivity.this, DownGGUtils.class);
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
                        Intent intent = new Intent(IntegralActivity.this, GGWed.class);
                        intent.putExtra("url", myGG.get(point).getU());
                        startActivity(intent);
                    }

                }
            });
        } else {
            inMygg.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.rl_youmi_maney, R.id.rl_zhongyi_maney, R.id.rl_dianru_maney,
            R.id.rl_diancai_maney, R.id.rl_duomeng_maney, R.id.rl_dianle_maney, R.id.rl_qumi_maney
            ,R.id.rl_datouniao_maney
    })
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, StarManeyActivity.class);
        intent.putExtra("type", 4);
        switch (view.getId()) {
            case R.id.rl_youmi_maney://有米
                intent.putExtra(Constant.TITLE, Constant.YOUMI);
                break;
            case R.id.rl_diancai_maney://点财
                intent.putExtra(Constant.TITLE, Constant.DIANCAI);
                break;
            case R.id.rl_qumi_maney://趣米
                intent.putExtra(Constant.TITLE, Constant.QUMI);
                break;
            case R.id.rl_zhongyi_maney://中亿
                intent.putExtra(Constant.TITLE, Constant.ZHOGNYI);
                break;
            case R.id.rl_dianru_maney:
                break;
            case R.id.rl_dianle_maney:
                intent.putExtra(Constant.TITLE, Constant.DIANLE);
//                startActivity(new Intent(IntegralActivity.this,DianleSDkExampleActivity.class));
                break;
            case R.id.rl_duomeng_maney:
                break;
            case R.id.rl_datouniao_maney:
                intent.putExtra(Constant.TITLE, Constant.DATOUNIAO);
                break;

        }
        if (intent != null) {
            startActivity(intent);
        }
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



    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(skip_mark)){
            switch (skip_mark){
                case Constant.TASK_DOWNLOAD:
                    setResult(3,new Intent());
                    finish();
                    break;
                default:
                    super.onBackPressed();
            }
        }else {
            super.onBackPressed();
        }
    }
}
