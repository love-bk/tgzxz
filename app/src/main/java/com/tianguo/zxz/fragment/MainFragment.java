package com.tianguo.zxz.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.db.ta.sdk.NonStandardTm;
import com.db.ta.sdk.NsTmListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.ChannelActivity;
import com.tianguo.zxz.activity.GGWed;
import com.tianguo.zxz.activity.MyActivity.SoWebActivity;
import com.tianguo.zxz.activity.ShareActivity;
import com.tianguo.zxz.adapter.MainTeileAdapter;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.NavListBean;
import com.tianguo.zxz.bean.TagBean;
import com.tianguo.zxz.bean.TuiKaBean;
import com.tianguo.zxz.bean.VersionInfo;
import com.tianguo.zxz.fragment.homefragment.NewsListFragment;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.HongShowUtils;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by lx on 2017/4/18.
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_novel)
    TextView tvNovel;
    @BindView(R.id.tv_live)
    TextView tvLive;
    @BindView(R.id.tv_get_money)
    TextView tvGetMoney;
    @BindView(R.id.tv_local)
    TextView tvLocal;
    @BindView(R.id.tv_special_offer)
    TextView tvSpecialOffer;
    @BindView(R.id.tv_nav)
    TextView tvNav;
    @BindView(R.id.tv_snatch)
    TextView tvSnatch;
    @BindView(R.id.ll_news_onew)
    LinearLayout llNewsOnew;
    @BindView(R.id.tv_tuika)
    TextView tvtuiKa;
    @BindView(R.id.ll_banner)
    LinearLayout llBanner;
    @BindView(R.id.rl_main_home)
    RelativeLayout home;
    @BindView(R.id.iv_tuika)
    ImageView ivTuika;
    @BindView(R.id.ll_tuika)
    LinearLayout llTuiKa;
    private NonStandardTm mNonStandardTm;
    private int count = 1;
    private List<TagBean> otherChannel;
    private PopupWindow popupWindow;
    private SearchFragment searchFragment;

    public LinearLayout getLlNewsOnew() {
        return llNewsOnew;
    }

    public LinearLayout getLlBanner() {
        return llBanner;
    }

    public RelativeLayout getHome() {
        return home;
    }

    private Thread threads;
    private NotificationManager nm;
    int mMin = 0;
    int mHour = 0;
    private long ld;
    private Date dates;
    int num;
    @BindView(R.id.tv_main_teile_main)
    RelativeLayout tvMainTeileMain;
    @BindView(R.id.tv_main_teile_so)
    TextView tvMainTeileSo;
    @BindView(R.id.iv_hong_bao)
    ImageView ivHongBao;
    @BindView(R.id.tv_hong_num)
    TextView tvHongNum;
    @BindView(R.id.rl_hongbao_num)
    RelativeLayout rlHongbaoNum;
    @BindView(R.id.tv_main_teili_time)
    TextView tvMainTeiliTime;
    @BindView(R.id.tv_main_teili_time_fen)
    TextView tvMainTeiliTimeFen;
    @BindView(R.id.main_teile)
    RelativeLayout teile;
    @BindView(R.id.rl_hongbao_time)
    RelativeLayout relativeLayout;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.iv_tian_hong)
    ImageView iv_tian_hong;
    @BindView(R.id.iv_plus)
    TextView ivPlus;
    private int[] imgResId = {R.mipmap.search, R.mipmap.novel, R.mipmap.live, R.mipmap.get_the_money,
            R.mipmap.local, R.mipmap.special_offer, R.mipmap.nav, R.mipmap.snatch, R.mipmap.get_the_money};
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @SuppressLint("WrongConstant")
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            try {
                if (mMin == 0 && mHour == 0) {
                    threads = null;
                    PendingIntent pendingIntent3 = PendingIntent.getActivity(main, 0,
                            new Intent(main, MainActivity.class), 0);
                    // 1.获取NotificationManager对象  
                    nm = (NotificationManager) main.getSystemService(NOTIFICATION_SERVICE);
                    // 2.初始化Notification对象  
                    Notification builder = new Notification.Builder(main)
                            .setSmallIcon(R.mipmap.zhuanlogo)
                            .setTicker(main.getResources().getString(R.string.app_name))
                            .setContentTitle("您有一个红包可以领取")
                            .setContentText("红包已刷新，点我立刻领取！")
                            .setContentIntent(pendingIntent3)
                            .setNumber(1).build();
                    builder.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。  
                    nm.notify(1, builder);//：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提
                    if (null != notifyListner) {
                        notifyListner.ontifylistner();
                    }
                }
                if (msg.what == 1) {
                    if (mHour < 0) {
                        mHour = 0;
                    }
                    if (mMin>0)
                        mMin--;
                    if (mMin <=0) {
                        if (mHour>0)
                            mHour--;
                        mMin = 59;
                    }
                    if (mHour < 10) {
                        tvMainTeiliTime.setText("0" + mHour + "");
                    } else {
                        tvMainTeiliTime.setText(mHour + "");
                    }
                    if (mMin < 10) {
                        tvMainTeiliTimeFen.setText("0" + mMin + "");
                    } else {
                        tvMainTeiliTimeFen.setText(mMin + "");
                    }


                }
            } catch (Exception E) {
            }
        }
    };


    private Message m;
    @BindView(R.id.rcl_main_teile)
    RecyclerView raceMainTails;
    //    String[] arr = new String[]{"推荐", "娱乐", "星座", "体育", "搞笑", "情感", "社会"};
    private List<TagBean> arr = new ArrayList<>();
    private MainTeileAdapter mainTeileAdapter;
    private NewsListFragment fragment = null;
    private FragmentTransaction fragmentTransaction;
    private HashMap<String, String> map;
    private MainActivity main;
    OnNotifyListner notifyListner;
    private Thread thread = null;
    private String type = " ";

    public void setOnNotifiyListner(OnNotifyListner notifyListner) {
        this.notifyListner = notifyListner;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnNotifyListner {
        void ontifylistner();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main = (MainActivity) getActivity();
        IntentFilter paramBundle = new IntentFilter();
        paramBundle.addAction("com.action.HONG_BAO");
        main.registerReceiver(receiver, paramBundle);
    }

    private int num1;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            num1 = intent.getIntExtra("num", 0);
            if (context.getPackageName().equals(intent.getPackage())) {
                if (intent.getAction().equals("com.action.HONG_BAO")) {
                    LogUtils.e(intent.getBooleanExtra("isDON", false) + "sssss");
                    if (intent.getBooleanExtra("isDON", false)) {
                        tvHongNum.setText(main.getNUM() - 1 + "");
                        main.setNUM(main.getNUM() - 1);
                    } else {
                        tvHongNum.setText(intent.getIntExtra("num", 0) + "");
                        setTilem(intent.getLongExtra("tick", 0));
                        setTilems();
                    }
                }

            }
        }
    };

    @SuppressLint("WrongConstant")
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        try {
            map = new HashMap<>();
            main = (MainActivity) getActivity();
            initNavWidget();//初始化首页导航控件的显示
            setTilems();
            iv_tian_hong.setTag(R.id.red_packet_tag, getString(R.string.red_packet_tag));
            if (iv_tian_hong.getTag(R.id.red_packet_tag) != null) {
                String data = SharedPreferencesUtil.getSwitch(main, iv_tian_hong.getTag(R.id.red_packet_tag).toString());
                if (!TextUtils.isEmpty(data)) {
                    iv_tian_hong.setVisibility(View.VISIBLE);
                    HongShowUtils.show(main, iv_tian_hong, raceMainTails);
                }
            }

            mNonStandardTm = new NonStandardTm(main);
            mNonStandardTm.loadAd(2574);
            mNonStandardTm.setAdListener(new NsTmListener() {
                @Override
                public void onReceiveAd(String s) {
                    mNonStandardTm.adExposed();
                    Gson gson = new Gson();
                    final TuiKaBean bean = gson.fromJson(s, TuiKaBean.class);
                    if (bean != null) {
                        LogUtils.e("dizhi" + bean.getImg_url());
                        Glide.with(main)
                                .load(bean.getImg_url())
                                .centerCrop()
                                .into(new GlideDrawableImageViewTarget(ivTuika) {
                                    @SuppressLint("WrongConstant")
                                    @Override
                                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                        super.onResourceReady(drawable, anim);
                                        if (count != 1) {
                                            llBanner.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                        tvtuiKa.setText(bean.getAd_title());
                        llTuiKa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {


                                    mNonStandardTm.adClicked();
                                    Intent intent = new Intent(main, GGWed.class);
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

            main.setLoadingFlag(false);
            map = new HashMap<>();
            getGengxin();
            LinearLayoutManager linearLayout = new LinearLayoutManager(main);
            linearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
            raceMainTails.setLayoutManager(linearLayout);
            mainTeileAdapter = new MainTeileAdapter(main, arr);
            getNavTags();//获取首页新闻导航
            main.setOnHomeShowListner(new MainActivity.HomeIsSHowListner() {
                @Override
                public void OnHomeIsHowListner() {
                    try {
                        fragmentTransaction = main.getSupportFragmentManager().beginTransaction();
                        fragment = new NewsListFragment();
                        if (arr.size() > 0) {
                            fragment.setType(arr.get(0));
                            mainTeileAdapter.setSelectedType(arr.get(0).getType());
                        }
                        fragment.setMainfragment(MainFragment.this);
                        fragmentTransaction.replace(R.id.vp_main_news, fragment).commitAllowingStateLoss();
                    } catch (Exception e) {

                    }
                }
            });
            raceMainTails.setAdapter(mainTeileAdapter);

            //首页导航的点击事件
            mainTeileAdapter.setOnClinItmeListner(new MainTeileAdapter.OnClineItme() {
                @Override
                public void OnClineItmeListner(int poing, TagBean tagBean) {
                    clickNav(poing, tagBean);

                }
            });


            if (!SharedPreferencesUtil.getIsNew(main)) {
                newBieGuide();
            }

        } catch (Exception e) {

        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }


    public void newBieGuide() {
        //初始化首页新手引导的弹窗
        if (main != null) {
            if (SharedPreferencesUtil.getInstall(main)) {
                SharedPreferencesUtil.saveInstall(main, false);
                initPopupWindow();
            }
        }
    }

    /**
     * 首页引导
     */
    private void initPopupWindow() {
        View view = View.inflate(main, R.layout.home_page_guide, null);
        if (popupWindow == null) {
            popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        }
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvContent.setText("每个小时都有新的奖励哦,\n到时记得回来看看");
        tvContent.setBackgroundResource(R.mipmap.popup1);
        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        backgroundAlpha(0.5f);
        popupWindow.showAsDropDown(tvMainTeiliTime, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                if (fragment != null) {
                    fragment.showPopupWindow();
                }
            }
        });

    }

    //当新闻分类数据没有请求成功时的默认设置
    private void initNewsType() {
        if (arr != null && arr.size() == 0) {
            arr.add(new TagBean("推荐", 7));
            arr.add(new TagBean("娱乐", 2));
            arr.add(new TagBean("星座", 4));
            arr.add(new TagBean("体育", 5));
            arr.add(new TagBean("搞笑", 3));
            arr.add(new TagBean("情感", 6));
            arr.add(new TagBean("社会", 8));
        }
    }


        //点击新闻分类tag调用的
        private void clickNav(int selectedP, TagBean tagBean) {
            if (arr != null && selectedP < arr.size()) {
                isShow(false);
                String text = arr.get(selectedP).getText();
                mainTeileAdapter.setSelectedType(tagBean.getType());
                raceMainTails.smoothScrollToPosition(selectedP);
                type = text;
                fragmentTransaction = main.getSupportFragmentManager().beginTransaction();
                map.put("type", text);
                if (tagBean.getType() == 0) {
                    searchFragment = new SearchFragment();
                    searchFragment.setMainfragment(MainFragment.this);
                    fragmentTransaction.replace(R.id.vp_main_news, searchFragment).commitAllowingStateLoss();
                    searchFragment.setOnScolistenr(new SearchFragment.OnScolistenr() {
                        @Override
                        public void onSoclistner(boolean isSoc) {
                            isShow(isSoc);
                        }
                    });
                }else {
                    if (!TextUtils.isEmpty(tagBean.getUrl())){
                        Intent intentUrl = new Intent(main, SoWebActivity.class);
                        String url = tagBean.getUrl().toString();
                        if (!TextUtils.isEmpty(url)) {
                            url = url.replace("{sso}", SharedPreferencesUtil.getSSo(main));
                            url = url.replace("{ver}", UpdateAppUtil.getAPPLocalVersion(main));
                            url = url.replace("{dev}", SharedPreferencesUtil.getOnlyID(main));
                            url = url.replace("{chn}", SharedPreferencesUtil.getSSo(main));
                        }
                        intentUrl.putExtra("url", url);
                        intentUrl.putExtra(Constant.TITLE,tagBean.getText().toString());
                        if ("小说".equals(tagBean.getText()+""))
                            intentUrl.putExtra(Constant.NAV, Constant.NAV_NOVEL);
                        startActivity(intentUrl);
                    }else {
                        fragment = new NewsListFragment();
                        fragment.setType(tagBean);
                        fragment.setMainfragment(MainFragment.this);
                        fragmentTransaction.replace(R.id.vp_main_news, fragment).commitAllowingStateLoss();
                        fragment.setOnScolistenr(new NewsListFragment.OnScolistenr() {
                            @Override
                            public void onSoclistner(boolean isSoc) {
                                isShow(isSoc);
                            }
                        });
                    }
                }


                MobclickAgent.onEvent(main, "click_news", map);
            }
        }

    //加载新闻分类tag数据
    private void getNavTags() {
        String newUserTags = SharedPreferencesUtil.getNewUserType(main);
        if (!TextUtils.isEmpty(newUserTags)) {
            Gson gson = new Gson();
            List<TagBean> userType = gson.fromJson(newUserTags,
                    new TypeToken<List<TagBean>>() {
                    }.getType());
            if (userType != null && userType.size() > 0) {
                if (arr != null)
                    arr.clear();
                arr.addAll(userType);
            } else {
                initNewsType();
            }
        } else {
            initNewsType();
        }
        loadNewListPage(arr);
    }

    @SuppressLint("WrongConstant")
    private void loadNewListPage(List<TagBean> userType) {
        if (userType != null && userType.size() > 0) {
            mainTeileAdapter.setSelectedType(userType.get(0).getType());
            if (mainTeileAdapter != null)
                mainTeileAdapter.notifyDataSetChanged();
            ivPlus.setVisibility(View.VISIBLE);
            //加载新闻list页面
            fragmentTransaction = main.getSupportFragmentManager().beginTransaction();
            fragment = new NewsListFragment();
            fragment.setType(userType.get(0));
            fragment.setMainfragment(MainFragment.this);
            fragment.setOnScolistenr(new NewsListFragment.OnScolistenr() {
                @Override
                public void onSoclistner(boolean isSoc) {
                    isShow(isSoc);
                }
            });
            fragmentTransaction.replace(R.id.vp_main_news, fragment).commit();
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        main.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = main.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        main.getWindow().setAttributes(lp);
    }

    /**
     * 初始化首页导航开关控件
     */
    @SuppressLint("WrongConstant")
    private void initNavWidget() {
        for (int i = 0; i < llBanner.getChildCount(); i++) {
            View rlView = llBanner.getChildAt(i);
            if (!TextUtils.isEmpty(rlView.getTag() + "")) {
                String data = SharedPreferencesUtil.getSwitch(main, rlView.getTag().toString());
                if (data != null) {
                    Gson gson = new Gson();
                    NavListBean navListBean = gson.fromJson(data, NavListBean.class);
                    if (navListBean != null) {
                        rlView.setVisibility(View.VISIBLE);
                        String url = navListBean.getD();
                        if (!TextUtils.isEmpty(url)) {
                            url = url.replace("{sso}", SharedPreferencesUtil.getSSo(main));
                            url = url.replace("{ver}", UpdateAppUtil.getAPPLocalVersion(main));
                            url = url.replace("{dev}", SharedPreferencesUtil.getOnlyID(main));
                            url = url.replace("{chn}", SharedPreferencesUtil.getSSo(main));
                        }
                        rlView.setTag(url);
                        if (rlView instanceof LinearLayout) {
                            for (int j = 0; j < ((LinearLayout) rlView).getChildCount(); j++) {
                                View childView = ((LinearLayout) rlView).getChildAt(j);
                                if (childView instanceof ImageView) {
                                    if (i < imgResId.length) {
                                        Glide.with(main)
                                                .load(navListBean.getU())
                                                .centerCrop()
                                                .placeholder(imgResId[i])
                                                .into(((ImageView) childView));
                                    }
                                    continue;
                                }
                                if (childView instanceof TextView) {
                                    ((TextView) childView).setText(navListBean.getN());
                                }
                            }
                        }
                        count++;
                    }
                }
            }
            if (count > 5)
                break;
        }

    }

    private void setTilems() {
        //转换为标准时间对象
        if (thread == null) {
            thread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            m = new Message();
                            m.what = 1;
                            handler.sendMessage(m);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            };
            thread.start();
        }
    }

    public void setHongbaoIsShow(int num) {
        this.num = num;
        tvHongNum.setText(num + "");


    }

    @Override
    public void onDestroy() {
        if (mNonStandardTm != null) {
            mNonStandardTm.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    /**
     * 获取友盟渠道名
     *
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
                @SuppressLint("WrongConstant") ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
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
        try {
            final HashMap<String, Object> map = new HashMap<>();
            map.put("sso", SharedPreferencesUtil.getSSo(main));
            if (getChannelName(main) != null) {
                map.put("channel", getChannelName(main));
            }
            map.put("devid", SharedPreferencesUtil.getOnlyID(main));
            map.put("v", UpdateAppUtil.getAPPLocalVersion(main));
            Observable observable = RetroFactory.getInstance().getGengxin(map);
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
                        return;
                    }
                    UpdateAppUtil.updateApp(main, bean);

                }

                @Override
                public void onHandleError(int code, String message) {
                    super.onHandleError(code, message);
                }
            });
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    public void setTilem(final long time) {
        if (threads == null) {
            threads = new Thread() {
                @Override
                public void run() {
                    URL url = null;//取得资
                    try {
                        url = new URL("http://www.baidu.com");
                        URLConnection uc;//生成连接对象
                        uc = url.openConnection();
                        uc.connect(); //发出连接F
                        //取得网站日期时间
                        ld = uc.getDate();
                        handler.sendEmptyMessage(0);
                        long newstime = time - ld;
                        dates = new Date(newstime);
                        mHour = dates.getMinutes();
                        mMin = dates.getSeconds();
                        if (!SharedPreferencesUtil.getTimes(main).equals(dates.getYear() + "" + dates.getMonth() + "" + dates.getDate())) {
                            LogUtils.e("wwwwwwwwwwwww" + SharedPreferencesUtil.getTimes(main));
                            SharedPreferencesUtil.saveTimes(main, dates.getYear() + "" + dates.getMonth() + "" + dates.getDate());
                            SharedPreferencesUtil.saveReSou(main, 0);
                            SharedPreferencesUtil.saveFeed(main, 0);
                            SharedPreferencesUtil.saveFeeder(main, 0);
                            SharedPreferencesUtil.saveSouSuo(main, 0);
                        }
                        LogUtils.e(dates.getYear() + "ss" + dates.getMonth() + "sss" + dates.getDate() + "ss");
                    } catch (Exception e) {
                        startActivity(new Intent(main, MainActivity.class));
                        LogUtils.e("sssss" + e.toString());
                    }
                }
            };
            threads.start();
        }
    }

    @SuppressLint("WrongConstant")
    public void isShow(boolean show) {
        if (show) {
            tvMainTeileSo.setVisibility(View.VISIBLE);
            tvMainTeileMain.setVisibility(View.GONE);
        } else {
            tvMainTeileSo.setVisibility(View.GONE);
            tvMainTeileMain.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.rl_hongbao_num, R.id.iv_plus, R.id.rl_hongbao_time})
    public void onViewCled(View v) {
        switch (v.getId()) {
            case R.id.rl_hongbao_num:
//                ToastUtil.showMessage("您还有" + num + "个红包没有领取");
                break;
            case R.id.iv_plus:
                String newOtherTags = SharedPreferencesUtil.getNewOtherType(main);
                if (!TextUtils.isEmpty(newOtherTags)) {
                    Gson gson = new Gson();
                    otherChannel = gson.fromJson(newOtherTags,
                            new TypeToken<List<TagBean>>() {
                            }.getType());
                }
                Intent intent = new Intent(getActivity(), ChannelActivity.class);
                intent.putExtra(Constant.CHANNEL, (Serializable) arr);
                intent.putExtra(Constant.OTHER_CHANNEL, (Serializable) otherChannel);
                if (mainTeileAdapter != null) {
                    intent.putExtra(Constant.SELECTED_POSITION, mainTeileAdapter.getSelectedType());
                }
                getActivity().startActivityForResult(intent, 1);
                break;
            case R.id.rl_hongbao_time:
                ToastUtil.showMessage("距离下个红包刷新还有" + mHour + "分" + mMin + "秒");
                //TODO 弹出闹钟窗口
        }
    }


    @OnClick({R.id.ll_local, R.id.ll_search, R.id.ll_novel,
            R.id.ll_live, R.id.ll_get_money, R.id.ll_special_offer,
            R.id.ll_nav,
            R.id.ll_snatch})
    public void onViewClicked(View view) {
        Intent intent = new Intent(main, SoWebActivity.class);
        switch (view.getId()) {
            case R.id.ll_special_offer:
                intent.putExtra(Constant.NAV, Constant.NAV_TEMAI);
                break;
            case R.id.ll_novel:
                intent.putExtra(Constant.NAV, Constant.NAV_NOVEL);
                break;
            case R.id.ll_live:
                intent.putExtra(Constant.NAV, Constant.NAV_LIVE);
                break;

        }
        String url = (String) view.getTag();
        intent.putExtra("url", url);
        if (view instanceof LinearLayout) {
            for (int i = 0; i < ((LinearLayout) view).getChildCount(); i++) {
                View childAtView = ((LinearLayout) view).getChildAt(i);
                if (childAtView instanceof TextView) {
                    intent.putExtra(Constant.TITLE, ((TextView) childAtView).getText().toString());
                    break;
                }
            }
        }
        startActivity(intent);
    }

    @OnClick(R.id.tv_main_teile_so)
    public void onViewClicked() {
        try {
            map.put("click_search", "搜索中");
            MobclickAgent.onEvent(main, "search", map);
            Intent intent = new Intent(main, ShareActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 205) {
            if (data != null) {
                //获取从频道界面ChannelActivity返回的数据
                List<TagBean> channel = (List<TagBean>) data.getSerializableExtra(Constant.CHANNEL);
                int isSelectedP = data.getIntExtra(Constant.IS_SELECTEDP, -1);
                if (channel != null && channel.size() > 0) {
                    if (arr != null)
                        arr.clear();
                    arr.addAll(channel);
                    if (mainTeileAdapter != null) {
                        if (isSelectedP == -1) {
                            //被选中的被删除掉时，需要进行处理
                            boolean flag = true;
                            int selectedP = 0;
                            //之前被选中的分类是否被移除的逻辑
                            for (int i = 0; i < arr.size(); i++) {
                                if (arr.get(i).getType() == mainTeileAdapter.getSelectedType()) {
                                    selectedP = i;
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                //如果之前被选中的分类删除了，我们默认显示第一个
                                clickNav(0, arr.get(0));
                            } else {
                                mainTeileAdapter.notifyDataSetChanged();
                                //如果被选中的没有被删除我们要保证该分类是可见的
                                if (raceMainTails != null) {
                                    raceMainTails.smoothScrollToPosition(selectedP);
                                }
                            }
                        } else {
                            //点击“我的频道”里面的分类是会调用这个方法，显示被点击分类的内容
                            clickNav(isSelectedP, arr.get(isSelectedP));
                        }

                    }
                }
            }
        }
    }

}

