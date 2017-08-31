package com.tianguo.zxz;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.db.ta.sdk.TaSDK;
import com.dianjoy.video.DianViewAdManager;
import com.dianjoy.video.InterstitialAd;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tianguo.zxz.sdk.NewsSdkInit;
import com.tianguo.zxz.uctils.LogUtils;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by lx on 2017/6/15.
 */

public class SampleApplicationLike extends DefaultApplicationLike {
    private static Application instance;

    private static Context context;
    public SampleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }
    @SuppressLint("WrongConstant")
    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplication();

        // 设置是否开启热更新能力，默认为true
            TaSDK.init(context);



        Beta.enableHotfix = true;
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = false;
        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onPatchReceived(String patchFile) {
                LogUtils.e(patchFile.toString());
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {

            }

            @SuppressLint("WrongConstant")
            @Override
            public void onDownloadSuccess(String msg) {
                LogUtils.e(msg.toString());

            }

            @SuppressLint("WrongConstant")
            @Override
            public void onDownloadFailure(String msg) {
                LogUtils.e(msg.toString());

            }

            @Override
            public void onApplySuccess(String msg) {
                LogUtils.e(msg.toString());
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onApplyFailure(String msg) {
                LogUtils.e(msg.toString());
            }

            @Override
            public void onPatchRollback() {
                LogUtils.e("sssssssss");
            }
        };
        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(getApplication(), true);
        Bugly.init(getApplication(), "7d598c182d", false);
        try {
            DianViewAdManager instance = DianViewAdManager.getInstance();
            instance.init(context,"37f3abca969b37a7","");
            InterstitialAd.debug = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.toString());
        }
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplication());
        ShareSDK.initSDK(getApplication());
        NewsSdkInit.init(getContext(),false);
        newInstance();
    }
    public Application newInstance() {
        if (instance == null) {
            instance =  getApplication();
        }
        return  instance;
    }

    public static Context getContext() {
        return context;
    }

}
