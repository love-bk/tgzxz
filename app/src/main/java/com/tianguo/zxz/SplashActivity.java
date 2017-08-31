package com.tianguo.zxz;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.tianguo.zxz.activity.newsLoginActivity;
import com.tianguo.zxz.adapter.MyNewbieGuideAdapter;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.BIndBean;
import com.tianguo.zxz.manager.AdViewBannerManager;
import com.tianguo.zxz.manager.AdViewNativeManager;
import com.tianguo.zxz.net.OkHttpPostAsyncTask;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.ChannelUtil;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.PermissionsUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.tianguo.zxz.uctils.writeApk;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.tianguo.zxz.InitConfiguration.UpdateMode.EVERYTIME;


public class SplashActivity extends BaseActivity implements SplashADListener, MyNewbieGuideAdapter.NewbieCallback {


    private SplashAD splashAD;
    private ViewGroup container;
    private TextView skipView;
    private ImageView splashHolder;
    private static final String SKIP_TEXT = "点击跳过 %d";
    public boolean canJump = false;
    private Intent main;
    private HashMap<String, String> map;
    private String s;


    //新手引导
    //存放小圆点指示图片的数组
    @BindView(R.id.iv_dot1)
    ImageView ivDot1;
    @BindView(R.id.iv_dot2)
    ImageView ivDot2;
    @BindView(R.id.iv_dot3)
    ImageView ivDot3;
    @BindView(R.id.vp_newbie_guide)
    ViewPager vpNewbieGuide;
    @BindView(R.id.rl_newbie_guide)
    RelativeLayout rlNewbieGuide;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        try {

            if (Build.VERSION.SDK_INT >= 23) {
                if (PermissionsUtils.hasPermission(this, Manifest.permission.READ_PHONE_STATE)) {
                    @SuppressLint("WrongConstant") TelephonyManager TelephonyMgr;
                    TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    String szImei = TelephonyMgr.getDeviceId();
                    SharedPreferencesUtil.saveolyID(this, szImei);
                } else {
                    PermissionsUtils.requestPermission(this, 0, Manifest.permission.READ_PHONE_STATE);
                }
            }

            //获取后台配置
            InitConfiguration initConfiguration = new InitConfiguration.Builder(
                    this).setUpdateMode(EVERYTIME)
                    .setBannerCloseble(InitConfiguration.BannerSwitcher.CANCLOSED)
                    .setRunMode(InitConfiguration.RunMode.TEST)
                    .build();
            //横幅 配置      
            AdViewBannerManager.getInstance(this).init(initConfiguration,
                    new String[]{"SDK20170914090420rr4pmvotgihhrps"});
            //原生 配置
            AdViewNativeManager.getInstance(this).init(initConfiguration, new String[]{"SDK20170914090420rr4pmvotgihhrps"});
            map = new HashMap<>();
            main = new Intent(this, MainActivity.class);
            Intent intent = getIntent();
            Uri uri = intent.getData();
            if (uri != null) {
                String dataString = intent.getDataString();
                if (!TextUtils.isEmpty(dataString)) {
                    String[] split = dataString.split("[:]");
                    String tepy = split[0];
                    String data = split[1];
                    getLogin(tepy, data);
                }
            }
            //获取开关数据
            getNavList();
            /**
             * 压缩包
             */
            File file = new File(getPackageResourcePath());
            String s = writeApk.readApk(file);
//        String s =    "bind:103928202cb962ac59075b964b07152d234b70";
            if (!TextUtils.isEmpty(s)) {
                String[] split = s.split("[:]");
                String teyp = split[0];
                String data = split[1];
                LogUtils.e(teyp + data);
                getLogin(teyp, data);
            }
            container = (ViewGroup) this.findViewById(R.id.splash_container);
            skipView = (TextView) findViewById(R.id.skip_view);
            splashHolder = (ImageView) findViewById(R.id.ll_index_screen);
            if (SharedPreferencesUtil.getNewbieGuide(this)) {
                //新手引导
                initNewbieGuide();
                rlNewbieGuide.setVisibility(View.VISIBLE);
            } else {
                if (splashHolder.getTag() != null) {
                    String data = SharedPreferencesUtil.getSwitch(SplashActivity.this, splashHolder.getTag().toString());
                    if (!TextUtils.isEmpty(data)) {
                        fetchSplashAD(this, container, skipView, "1106101702", "7020326240532416", this, 0);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                skipLogin();
                            }
                        }, 2000);
                    }
                }
            }
        } catch (Exception e) {
            skipLogin();
            LogUtils.e(e.toString());
        }

    }

    /**
     * 跳转到登陆界面或主界面
     */
    private void skipLogin(){
        if (SharedPreferencesUtil.getSSo(SplashActivity.this).isEmpty()) {
            startActivity(new Intent(SplashActivity.this, newsLoginActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }
    //初始化新手引导
    private void initNewbieGuide() {
        ivDot1.setEnabled(false);
        MyNewbieGuideAdapter adapter = new MyNewbieGuideAdapter(this);
        adapter.setNewbieCallback(this);
        vpNewbieGuide.setAdapter(adapter);
        vpNewbieGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ivDot1.setEnabled(false);
                        ivDot2.setEnabled(true);
                        ivDot3.setEnabled(true);
                        break;
                    case 1:
                        ivDot1.setEnabled(true);
                        ivDot2.setEnabled(false);
                        ivDot3.setEnabled(true);
                        break;
                    case 2:
                        ivDot1.setEnabled(true);
                        ivDot2.setEnabled(true);
                        ivDot3.setEnabled(false);
                        SharedPreferencesUtil.saveNewbieGuide(SplashActivity.this,false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取开关数据
     */
    public void getNavList() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("channel", ChannelUtil.getChannelName(this));
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        OkHttpPostAsyncTask okHttpPostAsyncTask = new OkHttpPostAsyncTask(this,new OkHttpPostAsyncTask.AsyncCallBack() {
            @Override
            public void loadSuccess(String result) {
            }

            @Override
            public void loadFail() {

            }
        });
        okHttpPostAsyncTask.execute(map);
    }

    private void saveSwitch(String key, JSONObject dataJsonProject) {
        try {
            if (!dataJsonProject.isNull(key)) {
                JSONObject novelJsonObject = dataJsonProject.getJSONObject(key);
                if (novelJsonObject != null) {
                    SharedPreferencesUtil.saveSwitch(this, key, novelJsonObject.toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {

    }

    public void getLogin(String type, String data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("data", data);
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getBind(map);
        logins.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BIndBean>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(BIndBean loginBean) {
                LogUtils.e("");
                if (loginBean.getCode() == 0) {
                    SharedPreferencesUtil.saveSSO(SplashActivity.this, loginBean.getData().getSso());
                    main.putExtra("isBangding", true);
                } else {
                    ToastUtil.showMessage(loginBean.getMsg());
                }
                startActivity(main);

            }

            @Override
            public void onError(Throwable throwable) {
                LogUtils.e(throwable.toString());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity      展示广告的activity
     * @param adContainer   展示广告的大容器
     * @param skipContainer 自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * @param appId         应用ID
     * @param posId         广告位ID
     * @param adListener    广告状态监听器
     * @param fetchDelay    拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        splashAD = new SplashAD(activity, adContainer, skipContainer, appId, posId, adListener, fetchDelay);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onADPresent() {
        Log.i("AD_DEMO", "SplashADPresent");
        splashHolder.setVisibility(View.GONE); // 广告展示后一定要把预设的开屏图片隐藏起来
    }

    @Override
    public void onADClicked() {
        map.put("click_kaiping", "广点通");
        MobclickAgent.onEvent(SplashActivity.this, "ad", map);

        Log.i("AD_DEMO", "SplashADClicked");
    }

    /**
     * 倒计时回调，返回广告还将被展示的剩余时间。
     * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
     *
     * @param millisUntilFinished 剩余毫秒数
     */
    @Override
    public void onADTick(long millisUntilFinished) {
        Log.i("AD_DEMO", "SplashADTick " + millisUntilFinished + "ms");
        skipView.setText(String.format(SKIP_TEXT, Math.round(millisUntilFinished / 1000f)));
    }

    @Override
    public void onADDismissed() {
        Log.i("AD_DEMO", "SplashADDismissed");
        next();
    }

    @Override
    public void onNoAD(int errorCode) {
        LogUtils.e("AD_DEMOLoadSplashADFail, eCode=" + errorCode);
        /** 如果加载广告失败，则直接跳转 */
        try {
            BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
            LogUtils.e(blueadapter.getAddress().toString());
        } catch (Exception e) {
            startActivity(new Intent(this, SplashActivity.class));
            return;
        }
        if (SharedPreferencesUtil.getSSo(SplashActivity.this).isEmpty()) {
            startActivity(new Intent(SplashActivity.this, newsLoginActivity.class));
        } else {
            this.startActivity(main);
        }
        this.finish();
    }

    /**
     * 设置一个变量来控制当前开屏页面是否可以跳转，当开屏广告为普链类广告时，点击会打开一个广告落地页，此时开发者还不能打开自己的App主页。当从广告落地页返回以后，
     * 才可以跳转到开发者自己的App主页；当开屏广告是App类广告时只会下载App。
     */
    private void next() {
//        try {
//            BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
//            LogUtils.e(blueadapter.getAddress().toString());
//        } catch (Exception e) {
//            startActivity(new Intent(this, SplashActivity.class));
//            return;
//        }
        if (canJump) {
            if (SharedPreferencesUtil.getSSo(SplashActivity.this).isEmpty()) {
                startActivity(new Intent(SplashActivity.this, newsLoginActivity.class));
            } else {
                this.startActivity(main);
            }
            this.finish();
        } else {
            canJump = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (canJump) {
            next();
        }
        canJump = true;
    }

    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onNewbieCallback() {
        //新手引导点击跳转到登陆界面
        startActivity(new Intent(SplashActivity.this, newsLoginActivity.class));
        finish();
    }
}
