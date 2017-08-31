package com.tianguo.zxz.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.IncomeDetailActivity;
import com.tianguo.zxz.activity.MyActivity.CreditActivity;
import com.tianguo.zxz.activity.MyActivity.HelpAndFanActivity;
import com.tianguo.zxz.activity.MyActivity.HelpCentnetActivity;
import com.tianguo.zxz.activity.MyActivity.IntegralActivity;
import com.tianguo.zxz.activity.MyActivity.JianDanZhuan;
import com.tianguo.zxz.activity.MyActivity.SettingActivity;
import com.tianguo.zxz.activity.MyActivity.SoWebActivity;
import com.tianguo.zxz.activity.RenWuActivity;
import com.tianguo.zxz.activity.ZhiFuBaoActivity;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.JiangLiBean;
import com.tianguo.zxz.bean.MYBean;
import com.tianguo.zxz.bean.NavListBean;
import com.tianguo.zxz.bean.VersionInfo;
import com.tianguo.zxz.bean.YYListBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.tianguo.zxz.view.GlideCircleTransform;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/4/18.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.tv_my_help)
    TextView tvMyHelp;
    @BindView(R.id.tv_my_gengxin)
    TextView tvMyGengxin;
    @BindView(R.id.tv_van_viosn)
    TextView tvVison;
    @BindView(R.id.ll_news_two)
    LinearLayout llNewsTwo;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    @BindView(R.id.tv_my_lingqu)
    TextView getTvMylingqu;
    @BindView(R.id.tv_my_yue)
    TextView tvMyYue;
    @BindView(R.id.tv_my_tixian)
    LinearLayout tvMyTixian;
    @BindView(R.id.tv_gold)
    TextView tvGold;
    @BindView(R.id.tv_tianguo_id)
    TextView tvTianguoId;
    @BindView(R.id.iv_my_head)
    ImageView ivMyHead;
    @BindView(R.id.tv_undone_task)
    TextView tvUndoneTask;
    @BindView(R.id.tv_help)
    TextView tvHelp;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.ll_help)
    LinearLayout llHelp;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;

    private int gold;
    private BannerView bv;
    private ViewGroup bannerContainer;
    int i;
    private long firstTime;
    HashMap<String, String> map = new HashMap<>();
    private int num;
    private Object award;
    private int doneTaskNum;//未做的任务
    private int wd;
    private int scoreGold;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        try {
            initSwitch();
            getYaolingnum();
            bannerContainer = (ViewGroup) view.findViewById(R.id.bannerContainer);
            tvMyYue = (TextView) view.findViewById(R.id.tv_my_yue);
            tvVison.setText("当前版本：" + UpdateAppUtil.getAPPLocalVersion(main));
            tvTianguoId.setText("甜果ID：" + SharedPreferencesUtil.getID(main));
            tvVison.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i == 5) {
                        ToastUtil.showMessage(getChannelName(main) + UpdateAppUtil.getAPPLocalVersion(main));
                        i = 0;
                    }
                    if (System.currentTimeMillis() - firstTime > 2000) {
                        firstTime = System.currentTimeMillis();
                    } else {
                        i++;
                    }
                }
            });
            getLogin();
            initBanner();
            initSwitch();

        } catch (Exception e) {
        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        i = 0;
        getLogin();
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        getLogin();
        super.onResume();
    }

    private void initBanner() {
        final HashMap<String, String> map = new HashMap<>();
        bv = new BannerView(main, ADSize.BANNER, "1106101702", "9080520284395857");
        bv.setRefresh(30);
        bv.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(int arg0) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }

            @Override
            public void onADClicked() {
                map.put("type", "钱包横幅");
                MobclickAgent.onEvent(main, "click_banner", map);

            }
        });
        /* 发起广告请求，收到广告数据后会展示数据   */
        bv.loadAD();
        bannerContainer.addView(bv);
    }

    public void getLogin() {
        try {
            if (null == main) {
                return;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("sso", SharedPreferencesUtil.getSSo(main));
            Observable logins = RetroFactory.getInstance().getME(map);
            logins.compose(main.composeFunction).subscribe(new BaseObserver<MYBean>(main, main.pd) {
                @Override
                protected void onHandleSuccess(MYBean bean) {
                    if (bean != null) {
                        if (bean.getUser() != null) {
                            gold = bean.getUser().getGold();
                            wd = bean.getUser().getWd();
                            scoreGold = bean.getUser().getScore();
                            double monet = gold;
                            tvMyYue.setText("零钱：" + monet / 100 + "");
                            tvGold.setText("金币：" + scoreGold + "");
                            Glide.with(main).load(bean.getUser().getHead()).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).transform(new GlideCircleTransform(main)).into(ivMyHead);
                        }
                        tvUndoneTask.setText(bean.getNum() + "个任务待做");
                    }

                }

            });
        } catch (Exception e) {
        }
    }

    @SuppressLint("WrongConstant")
    private void initSwitch() {
        for (int i = 0; i < llMenu.getChildCount(); i++) {
            View tagView = llMenu.getChildAt(i);
            if (tagView.getTag() != null) {
                String data = SharedPreferencesUtil.getSwitch(main, tagView.getTag().toString());
                if (!TextUtils.isEmpty(data)) {
                    Gson gson = new Gson();
                    NavListBean navListBean = gson.fromJson(data, NavListBean.class);
                    if (navListBean != null) {
                        tagView.setVisibility(View.VISIBLE);
                        tagView.setTag(navListBean.getD());
                    }
                }
            }
        }
        llMenu.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_my;
    }

    @OnClick(R.id.tv_my_help)
    public void onClicked() {
        startActivity(new Intent(main, HelpAndFanActivity.class));
    }

    @OnClick(R.id.tv_my_gengxin)
    public void onViewClicked() {

        getGengxin();
    }


    /**
     * 获取友盟渠道名
     *
     * @param ctx 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Activity ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                @SuppressLint("WrongConstant") ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        //此处这样写的目的是为了在debug模式下也能获取到渠道号，如果用getString的话只能在Release下获取到。
                        channelName = applicationInfo.metaData.get("UMENG_CHANNEL") + "";
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    public void getGengxin() {
        final HashMap<String, Object> maps = new HashMap<>();
        maps.put("sso", SharedPreferencesUtil.getSSo(main));
        if (getChannelName(main) != null) {
            maps.put("channel", getChannelName(main));
        }
        Observable observable = RetroFactory.getInstance().getGengxin(maps);
        observable.compose(main.composeFunction).subscribe(new BaseObserver<VersionInfo>(main, main.pd) {
            @Override
            protected void onHandleSuccess(VersionInfo bean) {
                String[] updata = bean.getVer().split("[.]");
                String[] bendi = UpdateAppUtil.getAPPLocalVersion(main).split("[.]");
                StringBuffer one = new StringBuffer();
                StringBuffer two = new StringBuffer();
                for (int i = 0; i < bendi.length; i++) {
                    one.append(updata[i]);
                    two.append(bendi[i]);
                }
                String bendis = two.toString().substring(0, 4);
                LogUtils.e(one.toString() + "" + two.toString().substring(0, 4));
                if (Integer.parseInt(one.toString()) <= Integer.parseInt(bendis)) {
                    ToastUtil.showMessage("已经是最新版本");
                    return;
                }
                map.put("tab", "检查更新");
                MobclickAgent.onEvent(main, "click_Mywallet", map);
                UpdateAppUtil.updateApp(main, bean);
            }

            @Override
            public void onHandleError(int code, String message) {
                ToastUtil.showMessage("已经是最新版本");
            }
        });
    }

    public void getYaolingnum() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        map.put("devid", SharedPreferencesUtil.getOnlyID(main));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(main));
        Observable logins = RetroFactory.getInstance().getYaoNum(map);
        logins.compose(main.composeFunction).subscribe(new BaseObserver<YYListBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(YYListBean loginBean) {
                num = loginBean.getNum();
                if (loginBean.getNum() != 0) {
                    getTvMylingqu.setText("您还有（" + loginBean.getNum() + "）个奖励没有领取");
                } else {
                    getTvMylingqu.setText(" ");
                }
            }
        });

    }

    /**
     * 邀请奖励
     */
    public void getKeNum() {
        try {
            if (num == 0) {
                ToastUtil.showMessage("目前没有奖励可以领取");
                return;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("devid", SharedPreferencesUtil.getOnlyID(main));
            map.put("v", UpdateAppUtil.getAPPLocalVersion(main));
            map.put("sso", SharedPreferencesUtil.getSSo(main));
            Observable logins = RetroFactory.getInstance().getJiangLI(map);
            logins.compose(main.composeFunction).subscribe(new BaseObserver<JiangLiBean>(main, main.pd) {
                @Override
                protected void onHandleSuccess(JiangLiBean loginBean) {
                    double award = loginBean.getAward();
                    ToastUtil.showMessage("您领取了" + loginBean.getFollowerId() + "贡献的" + award / 100 + "元");
                    if (loginBean.getNum() > 0) {
                        getTvMylingqu.setText("还有（" + loginBean.getNum() + "）个奖励没有领取");
                    } else {
                        getTvMylingqu.setText(" ");
                        num = 0;
                    }
                }
            });

        } catch (Exception e) {

        }
    }

    int is = 1;

    @OnClick(R.id.tv_my_gome)
    public void onget() {
        map.put("tab", "游戏应用");
//        getAward();
        MobclickAgent.onEvent(main, "click_Mywallet", map);
//        Intent intent = new Intent(main, YYXZActivity.class);
        Intent intent = new Intent(main, IntegralActivity.class);
        intent.putExtra(Constant.TITLE,getString(R.string.download_make_money));
        startActivity(intent);
    }

    @OnClick({R.id.ll_my_gemo})
    public void onGemo(View view) {
        map.put("tab", "游戏中心");
        MobclickAgent.onEvent(main, "click_Mywallet", map);
        Intent intent = new Intent(main, SoWebActivity.class);
//        "http://u.360.cn/?s=qch_np_lm_265699"
        intent.putExtra("url", view.getTag() + "");
        intent.putExtra(Constant.TITLE, "游戏中心");
        startActivity(intent);
    }

    @OnClick(R.id.tv_my_jiangli)
    public void onJiangli() {
        getKeNum();
        map.put("tab", "领取奖励");
        MobclickAgent.onEvent(main, "click_Mywallet", map);

    }

    @OnClick({R.id.tv_renwu, R.id.ll_tv_task})
    public void onRenwu() {
        map.put("tab", "任务管理");
        MobclickAgent.onEvent(main, "click_Mywallet", map);
        Intent intent = new Intent(main, RenWuActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.tv_my_jiandanzhuan)
    public void onClinZhuan() {
        map.put("tab", getString(R.string.easy_make_money));
        MobclickAgent.onEvent(main, "click_Mywallet", map);
        Intent intent = new Intent(main, JianDanZhuan.class);
        intent.putExtra(Constant.TITLE,getString(R.string.easy_make_money));
        startActivityForResult(intent, 202);
    }

    @OnClick(R.id.tv_my_duiba)
    public void onViewDui() {
        map.put("tab", "兑换商城");
        MobclickAgent.onEvent(main, "click_Mywallet", map);
        startActivity(new Intent(main, CreditActivity.class));
    }

    @OnClick(R.id.tv_my_tixian)
    public void onViewed() {
        map.put("tab", "支付宝");
        MobclickAgent.onEvent(main, "click_Mywallet", map);
        Intent intent = new Intent(main, ZhiFuBaoActivity.class);
        intent.putExtra("gold", gold);
        intent.putExtra("wd", wd);
        startActivity(intent);

    }



    @OnClick(R.id.tv_detail)
    public void onViewDe() {
        map.put("tab", "收支明细");
        MobclickAgent.onEvent(main, "click_MyIncomeDetail", map);
        Intent intent3 = new Intent(main, IncomeDetailActivity.class);
        double monet = gold;
        intent3.putExtra("balance",monet/100+"");
        intent3.putExtra("gold",scoreGold+"");
        main.startActivity(intent3);
    }


    @OnClick({R.id.tv_help, R.id.tv_setting,R.id.ll_help,R.id.ll_setting,R.id.tv_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_help:
            case R.id.tv_help:
                startActivity(new Intent(main, HelpAndFanActivity.class));
                break;
            case R.id.ll_setting:
            case R.id.tv_setting:
                startActivity(new Intent(main, SettingActivity.class));
                break;
            case R.id.tv_about:
                Intent intent = new Intent(main, HelpCentnetActivity.class);
                intent.putExtra("id",253825);
                intent.putExtra("centent","【其他问题】");
                intent.putExtra("isHelp", true);
                intent.putExtra(Constant.TYPE,Constant.ABOUTUS);
                main.startActivity(intent);
                break;
        }
    }
}
