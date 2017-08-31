package com.tianguo.zxz.fragment.homefragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.AdKeys;
import com.iflytek.voiceads.IFLYNativeAd;
import com.iflytek.voiceads.IFLYNativeListener;
import com.iflytek.voiceads.NativeADDataRef;
import com.tianguo.zxz.Flat;
import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.NewsListAdpeter;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.MyGGbean;
import com.tianguo.zxz.bean.NewsListBean;
import com.tianguo.zxz.bean.TagBean;
import com.tianguo.zxz.fragment.MainFragment;
import com.tianguo.zxz.interfaces.AdViewNativeListener;
import com.tianguo.zxz.manager.AdViewNativeManager;
import com.tianguo.zxz.natives.NativeAdInfo;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.GuanGaoUtils;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.tianguo.zxz.uctils.webDiogUtils;
import com.tianguo.zxz.view.RefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import io.reactivex.Observable;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by lx on 2017/4/19.
 */

public class NewsListFragment extends BaseFragment implements IFLYNativeListener {
    int position = 7;
    // 记录某position是否请求过广告，防止重复请求
    public SparseBooleanArray requested = new SparseBooleanArray();
    // 利用queue记录发送的广告请求
    // loadAd()中enqueue，onADLoaded()和onAdFailed()中dequeue并处理
    public Queue<IFLYAd> iflyAds = new LinkedList<IFLYAd>();
    private IFLYNativeAd nativeAd;
    private String imei;
    private String mtype;
    private String mtyb;
    private String serviceName;
    // 利用queue记录发送的广告请求
    // loadAd()中enqueue，onADLoaded()和onAdFailed()中dequeue并处理
    @BindView(R.id.lv_newslist)
    ListView lvNewslist;
    @BindView(R.id.sw_news_list)
    RefreshLayout swNewsList;
    private NewsListAdpeter newsListAdpeter;
    OnHongListnet hongListnet;
    private MainActivity main;
    public int botton = 0;
    public int isbotton = -1;
    MainFragment fragment;
    List<Object> data;
    List<Object> asd;
    private View head;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;
    private float lastX = 0;
    private float lastY = 0;
    private HashMap<String, String> map = new HashMap<>();
    private View rlBanner;
    private PopupWindow popupWindow;

    public void setMainfragment(MainFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onResume() {
        if (lvNewslist != null && main != null) {
            LogUtils.e("sssssssssss");
            WindowManager wm = main.getWindowManager();
            ViewGroup.LayoutParams layoutParams = lvNewslist.getLayoutParams();
            layoutParams.height = wm.getDefaultDisplay().getHeight();
            layoutParams.width = wm.getDefaultDisplay().getWidth();
            lvNewslist.setLayoutParams(layoutParams);
        }
        super.onResume();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        try {

            main = (MainActivity) getActivity();
            asd = new ArrayList<>();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getNewsList(0);
                }
            }, 200);
            if (data != null && data.size() > 0) {
                data.clear();
            }
            GuanGaoUtils guanGaoUtils = new GuanGaoUtils(false, main);
            guanGaoUtils.getMyGG(new GuanGaoUtils.OnMyGGListner() {
                @Override
                public void onmylistner(List<MyGGbean.Cpa2Bean> list) {
                    Flat.list = list;
                }

                @Override
                public void onMyggcp1(List<MyGGbean.Cpa1Bean> cp1) {
                    Flat.cp1 = cp1;
                    LogUtils.e("数据出来了" + cp1.size(), "gjj");
                }

                @Override
                public void onMyggcp3(List<MyGGbean.Cpa3Bean> cpa3) {
                    Flat.cp3 = cpa3;
                }

                @Override
                public void onMyggcp4(List<MyGGbean.Cpa4Bean> cpa4) {
                    Flat.cp4 = cpa4;
                }
            });
            swNewsList.setOnThchListher(new RefreshLayout.OnThchListner() {
                @Override
                public boolean Onthch(MotionEvent ont) {

                    return dispathTouchEvent(ont);
                }
            });
            @SuppressLint("WrongConstant") TelephonyManager tm = (TelephonyManager) getMain().getSystemService(TELEPHONY_SERVICE);
            // 手机品牌  
            mtyb = android.os.Build.BRAND;
            // 手机型号  
            mtype = android.os.Build.MODEL;
            imei = tm.getDeviceId();
            // 运营商  
            serviceName = tm.getSimOperatorName();
            data = new ArrayList<>();
            initNativeAd();
            final HashMap<String, String> map = new HashMap<>();
            if (fragment != null) {
                fragment.setOnNotifiyListner(new MainFragment.OnNotifyListner() {
                    @Override
                    public void ontifylistner() {
                        getNewsList(2);
                    }
                });
            }
            if (main != null) {
                main.setLogingListenr(new MainActivity.LogingListenr() {
                    @Override
                    public void yesLogingListenr() {
                        getNewsList(0);
                    }
                });
                final webDiogUtils diogUtils = new webDiogUtils(main);
                head = View.inflate(main, R.layout.list_head, null);
                rlBanner = head.findViewById(R.id.rl_banner);
                head.findViewById(R.id.iv_heand_yaoqing).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (diogUtils.isShowing()) {
                            diogUtils.dismiss();
                            return;
                        }
                        map.put("click_invite_banner", "邀请收徒图片");
                        MobclickAgent.onEvent(main, "feed", map);

                        @SuppressLint("WrongConstant") ClipboardManager cm = (ClipboardManager) main.getSystemService(CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(main) + "红包会更多！");
                        diogUtils.isShow(main.getYoaing(), 0, 0 + "", 0 + "");
                        WindowManager.LayoutParams attributes = main.getWindow().getAttributes();
                        //当弹出Popupwindow时，背景变半透明
                        attributes.alpha = 0.6f;
                        main.getWindow().setAttributes(attributes);
                        map.put("type", "邀请收徒banner");
                        MobclickAgent.onEvent(main, "click_invite_banner", map);
                    }
                });

                if (position == 7) {
                    if (rlBanner.getTag() != null) {
                        String data = SharedPreferencesUtil.getSwitch(main, rlBanner.getTag().toString());
                        if (!TextUtils.isEmpty(data)) {
                            lvNewslist.addHeaderView(head);
                        } else {
                            lvNewslist.removeHeaderView(head);
                        }
                    }

                }
                diogUtils.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams attributes = main.getWindow().getAttributes();
                        //当弹出Popupwindow时，背景变半透明
                        attributes.alpha = 1f;
                        main.getWindow().setAttributes(attributes);
                    }
                });
                newsListAdpeter = new NewsListAdpeter(NewsListFragment.this, main, data, asd, position, Flat.cp3);
                lvNewslist.setAdapter(newsListAdpeter);
                swNewsList.setOnisShowItmeListner(new RefreshLayout.OnIsShowItmeListner() {
                    @Override
                    public void isShowListner(int star, int end) {
                        int lastvisibleItem = star + end - 1;
                        // 若firstVisibleItem和lastvisibleItem是广告位置，则检查曝光
                        if (newsListAdpeter.isAdPosition(star))
                            checkExposure(star);
                        if (newsListAdpeter.isAdPosition(lastvisibleItem))
                            checkExposure(lastvisibleItem);
                    }
                });
                swNewsList.setProgressBackgroundColorSchemeResource(android.R.color.white);
                swNewsList.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
                swNewsList.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, main.getResources().getDisplayMetrics()));
                main.setOnIsBackListner(new MainActivity.OnIsBackListner() {
                    @Override
                    public boolean OnBackListner() {
                        if (diogUtils.isShowing()) {
                            diogUtils.dismiss();
                            return true;

                        }
                        return false;
                    }
                });
                swNewsList.post(new Runnable() {
                    @Override
                    public void run() {
                        swNewsList.setRefreshing(true);
                    }
                });
                swNewsList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getNewsList(0);
                    }
                });
                swNewsList.setOnLoadListener(new RefreshLayout.OnLoadListener() {
                    @Override
                    public void onLoad() {
                        if (botton == isbotton) {
                            isbotton = 0;
                            return;
                        }
                        swNewsList.setOnScoListner(new RefreshLayout.OnScolistner() {
                            @Override
                            public void onSoclistner(boolean isShow) {
                                if (isShow) {
                                    if (null != scolistenr) {
                                        scolistenr.onSoclistner(true);

                                    }
                                } else {
                                    if (null != scolistenr) {
                                        scolistenr.onSoclistner(false);
                                    }

                                }
                            }

                            @Override
                            public void onScoListnerLoding(int scrollState) {
                                switch (scrollState) {
                                    //停止滚动
                                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: {
                                        newsListAdpeter.setScoll(false);
                                        newsListAdpeter.notifyDataSetChanged();
                                        LogUtils.e("ssssssssssssssssssssssssssssss");
                                        break;
                                    }
                                    //滚动做出了抛的动作
                                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING: {
                                        //设置为正在滚动
                                        newsListAdpeter.setScoll(true);

                                        break;
                                    }
                                    //正在滚动
                                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: {
                                        newsListAdpeter.setScoll(true);
                                        break;
                                    }
                                }

                            }
                        });
                        getNewsList(1);
                    }
                });
            }


        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_newslist;
    }

    //获取新闻列表
    public void getNewsList(final int i) {
        try {

            if (i == 0 && data != null) {
                data.clear();
            }
            if (i == 0 && asd != null) {
                asd.clear();
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("type", position);
            map.put("num", 10);
            if ((null != data)) {
                if (!data.isEmpty() && (data.size() != 0) && (i == 1) && botton != 0) {
                    LogUtils.e("botton" + botton + "" + isbotton + "wwwwwwww");
                    map.put("id", botton);
                }
            }
            map.put("devid", SharedPreferencesUtil.getOnlyID(main));
            map.put("v", UpdateAppUtil.getAPPLocalVersion(main));
            map.put("sso", SharedPreferencesUtil.getSSo(main));
            Observable observable = RetroFactory.getInstance().getNewsList(map);
            observable.compose(main.composeFunction).subscribe(new BaseObserver<NewsListBean>(main, main.pd) {
                @Override
                protected void onHandleSuccess(final NewsListBean newsListBean) {
                    /**
                     * 请求数据是否空
                     */
                    if (null == newsListBean) {
                        swNewsList.setRefreshing(false);
                        if (i == 1) {
                            swNewsList.setLoading(false);
                        }
                        return;
                    }
                    try {
                        if (i == 1) {
                            swNewsList.setLoading(false);
                            if (isbotton != botton) {
                                isbotton = botton;
                            }
                        }
                    } catch (Exception e) {

                    }
                    botton = newsListBean.getNews().get(newsListBean.getNews().size() - 1).getI();
                    LogUtils.e(botton + "wwwwww" + isbotton);
                    data.addAll(newsListBean.getNews());
                    if (newsListAdpeter != null) {
                        newsListAdpeter.notifyDataSetChanged();

                    }
                    /**
                     * 红包回调接口
                     */
                    if (null != newsListBean.getHb()) {
                        Intent intent = new Intent("com.action.HONG_BAO");
                        intent.putExtra("num", newsListBean.getHb().getNum());
                        intent.putExtra("isDON", false);
                        intent.setPackage(main.getPackageName());
                        intent.putExtra("tick", newsListBean.getHb().getTick());
                        main.setNUM(newsListBean.getHb().getNum());
                        main.sendBroadcast(intent);
                    }
                    if (i == 0) {
                        newsListAdpeter = new NewsListAdpeter(NewsListFragment.this, main, data, asd, position, Flat.cp3);
                        lvNewslist.setAdapter(newsListAdpeter);
                    }
                    try {
                        newsListAdpeter.setOnAdapaternumListe(new NewsListAdpeter.OnAdapaernumLisnte() {
                            @Override
                            public void onAdapaterlistner() {
                                if (newsListBean.getHb().getNum() != 0) {
                                    newsListBean.getHb().setNum(newsListBean.getHb().getNum() - 1);
                                    if (null != hongListnet) {
                                        hongListnet.onHongListner(newsListBean.getHb().getNum(), newsListBean.getHb().getTick());
                                    }
                                }

                            }
                        });
                    } catch (Exception e) {

                    }

                    swNewsList.setRefreshing(false);


                }


                @Override
                public void onHandleError(int code, String message) {
                    LogUtils.e(code + message + "wwwwwww");
                    try {
                        swNewsList.setRefreshing(false);
                        if (i == 1) {
                            swNewsList.setLoading(false);

                        }
                    } catch (Exception e) {
                        swNewsList.setRefreshing(false);
                        if (i == 1) {
                            swNewsList.setLoading(false);
                        }
                    }

                }
            });
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    //souWeb广告
    public void getfeed() {
        AdViewNativeManager.getInstance(main).requestAd(main, "SDK20170914090420rr4pmvotgihhrps", new AdViewNativeListener() {
            @Override
            public void onAdFailed(String s) {

            }

            @Override
            public void onAdRecieved(String s, ArrayList arrayList) {
                if (arrayList != null && arrayList.size() != 0) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        asd.add(arrayList.get(i));
                        NativeAdInfo o = (NativeAdInfo) arrayList.get(i);
                        o.onDisplay(new View(main));
                    }
                }
                if (newsListAdpeter != null) {
                    newsListAdpeter.notifyDataSetChanged();
                }
            }

            @Override
            public void onAdStatusChanged(String s, int i) {
                LogUtils.e(s + "wwwwwwwwww" + i);
            }
        });

    }

    @Override
    public void onDestroy() {
        try {
            if (data != null &&
                    data.size() != 0) {
                data.clear();
                newsListAdpeter = null;

            }
        } catch (Exception e) {

        }
        super.onDestroy();
    }

    OnScolistenr scolistenr;

    public void setOnScolistenr(OnScolistenr scolistenr) {
        this.scolistenr = scolistenr;
    }

    public void setType(TagBean tagBean) {
        if (tagBean != null && !TextUtils.isEmpty(tagBean.getText())) {
            position = tagBean.getType();
            LogUtils.e("position为多少" + position);
            map.put("click_news", tagBean.getText());
        }

        MobclickAgent.onEvent(main, "feed", map);


    }


    public interface OnHongListnet {
        void onHongListner(int p, long time);
    }

    public interface OnScolistenr {
        void onSoclistner(boolean isSoc);
    }


    // 开发者维护讯飞广告类

    // 检查曝光
    public void checkExposure(int lastvisibleItem) {
        if (lastvisibleItem > data.size() - 1 || lastvisibleItem < 0) {
            return;
        }
        if (data.get(lastvisibleItem) instanceof IFLYAd) {
            IFLYAd curAd = (IFLYAd) data.get(lastvisibleItem);
            if (!curAd.isExposured && curAd != null && curAd.adContainer != null) {
                curAd.isExposured = curAd.aditem.onExposured(curAd.adContainer);
            }
        }
    }

    public void initNativeAd() {
        if (nativeAd == null) {
            nativeAd = new IFLYNativeAd(getMain(), "CF59440399A97ECE9414DB752FDA78FE", this);
//			nativeAd.setParameter(AdKeys.DOWNLOAD_ALERT, "true");
        }
        nativeAd.setParameter(AdKeys.DEBUG_MODE, "fales");
    }

    @Override
    public void onADLoaded(List<NativeADDataRef> list) {
        try {
// TODO Auto-generated method stub
            if (list.size() > 0) {
                final IFLYAd iflyAd = iflyAds.remove();
                iflyAd.aditem = list.get(0);
                // 添加
                data.add(iflyAd.position, iflyAd);
                // 更新
                if (newsListAdpeter != null) {
                    newsListAdpeter.notifyDataSetChanged();

                }
                // listview刷新完毕检查一次曝光
                lvNewslist.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                               int oldRight, int oldBottom) {
                        // TODO Auto-generated method stub
                        checkExposure(iflyAd.position);
                    }
                });

            }
        } catch (Exception e) {

        }

    }

    @Override
    public void onAdFailed(AdError adError) {
        // 获取广告失败，remove请求并将已请求标记重新设为false
        if (requested != null) {
            requested.put(iflyAds.remove().position, false);
        }
    }

    @Override
    public void onConfirm() {

    }

    @Override
    public void onCancel() {

    }

    public class IFLYAd {
        public NativeADDataRef aditem;
        boolean isExposured = false;
        int position;
        public View adContainer;

        public IFLYAd(int position) {
            // TODO Auto-generated constructor stub
            this.position = position;
            nativeAd.loadAd(1);
        }
    }

    private boolean isDown = false;
    private boolean isUp = false;

    private boolean dispathTouchEvent(MotionEvent event) {
        if (mIsAnim) {
            return false;
        }
        final int action = event.getAction();

        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                isUp = dX < 8 && dY > 30 && !mIsTitleHide && !down;
                isDown = dX < 8 && dY > 30 && mIsTitleHide && down;
                if (isUp) {
                    View view = this.fragment.getLlNewsOnew();
                    float[] f = new float[2];
                    f[0] = 0.0F;
                    f[1] = -fragment.getLlBanner().getHeight();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.setDuration(200);
                    animator1.start();
                    setMarginTop(1);
                    animator1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            mIsAnim = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    LogUtils.e("aaaaaaaaa");
                } else if (isDown) {
                    LogUtils.e("bbbbbbb");
                    View view = fragment.getLlNewsOnew();
                    float[] f = new float[2];
                    f[0] = -fragment.getLlBanner().getHeight();
                    f[1] = 0F;
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setDuration(200);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.start();
                    animator1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            setMarginTop(0);
                            mIsAnim = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                } else {
                    return false;
                }


                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
        }
        return false;

    }

    public void setMarginTop(int i) {
        if (i == 1) {
            final LinearLayout.LayoutParams ls = (LinearLayout.LayoutParams) fragment.getHome().getLayoutParams();
            ls.height = fragment.getHome().getHeight() + fragment.getLlBanner().getHeight();
            fragment.getHome().setLayoutParams(ls);
            fragment.getHome().invalidate();
            LogUtils.e(fragment.getHome().getHeight() + "sssssss");
        } else {
            final LinearLayout.LayoutParams ls = (LinearLayout.LayoutParams) fragment.getHome().getLayoutParams();
            ls.height = LinearLayout.LayoutParams.MATCH_PARENT;
            fragment.getHome().setLayoutParams(ls);
            fragment.getHome().invalidate();
            LogUtils.e(fragment.getHome().getHeight() + "sssssss");
        }


    }



    /**
     * 首页引导
     */
    public void showPopupWindow() {
        if (main != null){
            View view = View.inflate(main, R.layout.home_page_guide, null);
            if (popupWindow == null) {
                popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, true);
            }
            TextView tvContent = (TextView)view.findViewById(R.id.tv_content);
            tvContent.setText("点击任意新闻，都能获\n得金币奖励");
            tvContent.setBackgroundResource(R.mipmap.popup2);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            backgroundAlpha(0.5f);
            popupWindow.showAsDropDown(head, 0, -100);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
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
}


