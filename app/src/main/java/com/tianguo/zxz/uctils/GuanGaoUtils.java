package com.tianguo.zxz.uctils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dianjoy.video.DianViewAdManager;
import com.dianjoy.video.ScreenOrientationTpye;
import com.dianjoy.video.VideoAdListener;
import com.dianjoy.video.VideoAdPlayListener;
import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.AdKeys;
import com.iflytek.voiceads.IFLYAdListener;
import com.iflytek.voiceads.IFLYAdSize;
import com.iflytek.voiceads.IFLYBannerAd;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.MyGGbean;
import com.tianguo.zxz.interfaces.AdViewBannerListener;
import com.tianguo.zxz.manager.AdViewBannerManager;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by lx on 2017/6/9.
 */

public class GuanGaoUtils {
    boolean isgg = false;
    onFhithListner fhithListner;
    private String mtype;
    private String mtyb;
    private String imei;
    private Intent intent;
    private final HashMap<String, String> map;
    OnisShowGGListner listner;
    public  interface  OnisShowGGListner{
        void  shouGG();
    }

    BaseActivity main;
    public GuanGaoUtils(boolean isgg, BaseActivity main) {
        this.isgg = isgg;
        map = new HashMap<>();
        this.main = main;

    }

    String LOG_TAG = "ggviewdioe";

    /**
     * 视频广告
     */
    public void PlayViedo(final String placementid, Context context) {
        try {
            if (!PermissionsUtils.hasPermission(main, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ToastUtil.showMessage("请开启手机权限否则部分功能将无法使用");
                PermissionsUtils.requestPermission(main,0,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE);
                return;
            }
            /**
             * 加载视频广告
             * @param ctx          上下文
             * @param placementId  广告位id
             */
            DianViewAdManager.getInstance().getVideoAdInstance(context, placementid).loadAd(new VideoAdListener() {
                @Override
                public void onVideoLoading() {
                    Log.i(LOG_TAG, "开始加载");
                }

                @Override
                public void onVideoLoaded() {
                    playViedo(placementid);
                    Log.i(LOG_TAG, "加载完成");

                }

                @Override
                public void onVideoLoadError(int errCode, String errMsg) {
                    Log.i(LOG_TAG, "加载失败：errCode-" + errCode + "  msg-" + errMsg);
                    ToastUtil.showMessage("目前没有广告，尽请期待");
                }
            });

        } catch (Exception e) {

        }
    }

    public void playViedo(String placementid) {
        /**
         * 视频播放
         * @param ctx           上下文
         * @param placementId   广告位Id
         * @param type          横屏或者竖屏播放   ScreenOrientationTpye.PORTRAIT(竖屏播放),ScreenOrientationTpye.LANDSCAPE（横屏播  放）,ScreenOrientationTpye.AUTO(自适应)
         * @param playListener  视频播放回调
         */
        if (!PermissionsUtils.hasPermission(main, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ToastUtil.showMessage("请开启手机权限否则部分功能将无法使用");
            PermissionsUtils.requestPermission(main,0,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE);
            return;
        }
        DianViewAdManager.getInstance().getVideoAdInstance(main, placementid).play(ScreenOrientationTpye.AUTO, new VideoAdPlayListener() {
            @Override
            public void onVideoPlayBegin() {
                Log.i(LOG_TAG, "开始播放");
            }

            @Override
            public void onVideoPlayComplete() {
                Log.i(LOG_TAG, "播放完成");
            }

            @Override
            public void onVideoPlaySkip() {
                Log.i(LOG_TAG, "跳过");
            }

            @Override
            public void onVideoPlayError(int errCode, String errMsg) {
                ToastUtil.showMessage("暂时没有广告，请稍后重试");
                Log.i(LOG_TAG, "播放错误：errCode=" + errCode + "  errMsg=" + errMsg);
            }

            @Override
            public void onVideoPlayAwardSuccess() {
                Log.i(LOG_TAG, "奖励成功");
            }

            @Override
            public void onVideoPlayAwardFail() {
                Log.i(LOG_TAG, "奖励失败");
            }

            @Override
            public void onVideoPlayClose() {
                Log.i(LOG_TAG, "视频关闭");
            }
        });
    }

    /**
     * //介入广告
     */

    public interface OnMyGGListner {
        void onmylistner(List<MyGGbean.Cpa2Bean> cp2);
        void onMyggcp1(List<MyGGbean.Cpa1Bean> cp1);

        void onMyggcp3(List<MyGGbean.Cpa3Bean> cpa3);
        void onMyggcp4(List<MyGGbean.Cpa4Bean> cpa4);
    }

    public void getMyGG(final OnMyGGListner lisner) {
        if (!PermissionsUtils.hasPermission(main, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ToastUtil.showMessage("请开启手机权限否则部分功能将无法使用");
            PermissionsUtils.requestPermission(main,0,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE);
            return;
        }
        main.setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        map.put("devid", SharedPreferencesUtil.getOnlyID(main));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(main));
        Observable logins = RetroFactory.getInstance().getMyGG(map);
        logins.compose(main.composeFunction).subscribe(new BaseObserver<MyGGbean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(final MyGGbean bean) {
                if (bean!= null) {
                    if (null != lisner) {
                        lisner.onmylistner(bean.getCpa2());
                        lisner.onMyggcp1(bean.getCpa1());
                        lisner.onMyggcp3(bean.getCpa3());
                        lisner.onMyggcp4(bean.getCpa4());
                    }
                }
            }
            @SuppressLint("WrongConstant")
            @Override
            public void onHandleError(int code, String message) {
            }
        });
    }

    public void setFhithListner(onFhithListner fhithListner) {
        this.fhithListner = fhithListner;
    }

    public interface onFhithListner {
        void onfhioth();

        void onCLine();

        void onShow();
    }




    //adview
    public void AdViewBanner(String key, final LinearLayout layout) {
        if (!PermissionsUtils.hasPermission(main, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ToastUtil.showMessage("请开启手机权限否则部分功能将无法使用");
            PermissionsUtils.requestPermission(main,0,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE);
            return;
        }
        View view = AdViewBannerManager.getInstance(main).getAdViewLayout(main,
                key);
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
        AdViewBannerManager.getInstance(main).requestAd(main, key, new AdViewBannerListener() {
            @Override
            public void onAdClick(String s) {
                map.put("click_banner", "详情页_adview_横幅");
                MobclickAgent.onEvent(main, "banner", map);
                if (fhithListner!=null){
                    fhithListner.onCLine();
                }
            }

            @Override
            public void onAdDisplay(String s) {

            }

            @Override
            public void onAdClose(String s) {
                if (null != layout)
                    layout.removeView(layout.findViewWithTag(s));
            }

            @Override
            public void onAdFailed(String s) {

            }

            @Override
            public void onAdReady(String s) {
                if (listner!=null){
                    listner.shouGG();

                }
            }
        });
        view.setTag(key);
        layout.addView(view);
        layout.invalidate();
    }
    public void  setOnShowListner(OnisShowGGListner listner){
        this.listner=listner;
    }

    //讯飞
    public void xunfeiBannerAd(final Context context, String adUnitId, LinearLayout xunfeibanner) {
        try {
            if (!PermissionsUtils.hasPermission(main, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ToastUtil.showMessage("请开启手机权限否则部分功能将无法使用");
                PermissionsUtils.requestPermission(main,0,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE);
                return ;
            }
            final HashMap<String, String> map = new HashMap<>();
            final IFLYBannerAd bannerAd = IFLYBannerAd.createBannerAd(context, adUnitId);
            bannerAd.setAdSize(IFLYAdSize.BANNER);
            //设置广告尺寸
            bannerAd.setParameter(AdKeys.DEBUG_MODE, "true");
            //设置为调试模式
            bannerAd.setParameter(AdKeys.DOWNLOAD_ALERT, "true");
            //下载广告前，弹窗提示 //请求广告，添加监听器
            xunfeibanner.removeAllViews();
            xunfeibanner.addView(bannerAd);
            IFLYAdListener mAdListener = new IFLYAdListener() {
                @Override
                public void onConfirm() {

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onAdReceive() {
                    if (listner!=null){
                        listner.shouGG();

                    }
                    //成功接收广告，调用广告展示接口。注意：该接口在回调中才能生效
                    bannerAd.showAd();
                }

                @Override
                public void onAdFailed(AdError error) {
                    LogUtils.e("dddddddd" + error.getErrorDescription() + "ss" + error.getErrorCode());
                    // 广告请求失败 //error.getErrorCode():错误码，
                    error.getErrorDescription();
                }

                @Override
                public void onAdClick() {
                    map.put("click_banner", "讯飞横幅");
                    if (fhithListner != null && isgg == true) {
                        fhithListner.onCLine();
                    }
                    LogUtils.e("diansiwo2");

                    MobclickAgent.onEvent(context, "banner", map);
                    // 广告被点击
                }

                @Override
                public void onAdClose() {
//                         广告被关闭
                    if (fhithListner != null && isgg == true) {
                        fhithListner.onfhioth();

                    }
                }

                @Override
                public void onAdExposure() {
                    // 广告曝光

                }

            };
            bannerAd.loadAd(mAdListener);
        } catch (Exception e) {
        }
    }
}
