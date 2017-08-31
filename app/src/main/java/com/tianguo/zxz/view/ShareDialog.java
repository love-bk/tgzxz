package com.tianguo.zxz.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.R;
import com.tianguo.zxz.interfaces.AdViewNativeListener;
import com.tianguo.zxz.manager.AdViewNativeManager;
import com.tianguo.zxz.natives.NativeAdInfo;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by admin on 2017/8/26.
 */

public class ShareDialog extends PopupWindow implements View.OnClickListener {
    private  ImageView photo;
    private  RelativeLayout pix;
    private  TextView content;
    private  ImageView bigim;
    private Context context;
    private final HashMap<String, String> map;
    private String url;
    private int webid;
    private ArrayList feedList = new ArrayList();
    private int i;

    public ShareDialog(final Activity context) {
        map = new HashMap<>();
        this.context = context;

        if (context != null) {
            View inflate = View.inflate(context, R.layout.dialog_share_layout, null);
            setContentView(inflate);
            inflate.findViewById(R.id.iv_share_wechat).setOnClickListener(this);
            inflate.findViewById(R.id.iv_share_qq).setOnClickListener(this);
            inflate.findViewById(R.id.iv_share_qzone).setOnClickListener(this);
            inflate.findViewById(R.id.iv_share_sinaweibo).setOnClickListener(this);
            bigim = (ImageView) inflate.findViewById(R.id.im_bit);
            content = (TextView) inflate.findViewById(R.id.content);
            pix = (RelativeLayout) inflate.findViewById(R.id.icon_and_blurb);
            photo = (ImageView) inflate.findViewById(R.id.photo);
            inflate.findViewById(R.id.tv_cancle_share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setTouchable(true);
            this.setOutsideTouchable(false);
            this.getContentView().setFocusableInTouchMode(true);
            this.getContentView().setFocusable(true);
            this.setAnimationStyle(R.style.take_photo_anim);
            getfeed();
        }
    }

    String icon;
    String title;

    /**
     * @param view  展示在那个view智商
     * @param webid webid判断是否传入新闻id
     * @param icon  传入icon
     * @param title 传入新闻内容
     */
    @SuppressLint("WrongConstant")
    public void isShow(View view, int webid, String icon, String title) {
        try {

            showFeed(i);
            i++;
            this.webid = webid;
            this.icon = icon;
            this.title = title;
            if (webid != 0) {
                this.title = title;
                url = "http://news.tianh5.cn/news/detail.html?id=" + webid;
            }
            this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        try {


            switch (v.getId()) {
                case R.id.iv_share_wechat:
                    map.put("type", "微信");
                    Wechat.ShareParams wexin = new Wechat.ShareParams();
                    wexin.setShareType(Platform.SHARE_WEBPAGE);
                    wexin.title = context.getResources().getString(R.string.app_name);
                    wexin.text = title;
                    wexin.setComment("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(context) + "红包会更多！");
                    if (webid == 0) {
                        url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363789133793";
                        wexin.imageUrl = context.getResources().getString(R.string.shareimage_toutiao);
                    } else {
                        wexin.imageUrl = icon;
                    }

                    wexin.url = url;
                    Platform Weixin = ShareSDK.getPlatform(Wechat.NAME);
                    Weixin.setPlatformActionListener(new PlatformActionListener() {
                        public void onError(Platform arg0, int arg1, Throwable arg2) {
                            //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                            LogUtils.e(arg0 + "" + arg1 + "" + arg2 + "wwwww");
                        }

                        public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                            LogUtils.e(arg0 + "" + arg1 + "" + arg2 + "w2wwww");

                            //分享成功的回调
                        }

                        public void onCancel(Platform arg0, int arg1) {
                            LogUtils.e(arg0 + "" + arg1 + "" + "www3ww");
                            //取消分享的回调
                        }
                    });
                    Weixin.share(wexin);
                    break;
                case R.id.iv_share_qq:
                    map.put("type", "QQ");
                    QQ.ShareParams qq = new QQ.ShareParams();
                    qq.setTitle(context.getResources().getString(R.string.app_name));
                    if (webid == 0) {
                        url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881185054";
                        qq.setImageUrl(context.getResources().getString(R.string.shareimage_toutiao));
                    } else {
                        qq.setImageUrl(icon);
                    }
                    qq.setTitleUrl(url); // 标题的超链接
                    qq.setText(title);
                    qq.setComment("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(context) + "红包会更多！\n");
                    Platform QQ = ShareSDK.getPlatform(cn.sharesdk.tencent.qq.QQ.NAME);
                    QQ.setPlatformActionListener(new PlatformActionListener() {
                        public void onError(Platform arg0, int arg1, Throwable arg2) {
                            //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                        }

                        public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                            //分享成功的回调
                        }

                        public void onCancel(Platform arg0, int arg1) {
                            //取消分享的回调
                        }
                    });
                    QQ.share(qq);
                    break;
                case R.id.iv_share_qzone:
                    map.put("type", "QQ空间");
                    QZone.ShareParams sp = new QZone.ShareParams();
                    sp.setTitle(context.getResources().getString(R.string.app_name));
                    sp.setComment("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(context) + "红包会更多");
                    if (webid == 0) {
                        url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881484428";
                        sp.setImageUrl(context.getResources().getString(R.string.shareimage_toutiao));
                    } else {
                        sp.setImageUrl(icon);
                    }
                    sp.setTitleUrl(url); // 标题的超链接
                    sp.setSiteUrl(url);
                    sp.setText(title);
                    Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                    qzone.setPlatformActionListener(new PlatformActionListener() {
                        public void onError(Platform arg0, int arg1, Throwable arg2) {
                            //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                        }

                        public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                            //分享成功的回调
                        }

                        public void onCancel(Platform arg0, int arg1) {
                            //取消分享的回调
                        }
                    });
                    qzone.share(sp);
                    break;
                case R.id.iv_share_sinaweibo:
                    map.put("type", "朋友圈");
                    WechatMoments.ShareParams weibo = new WechatMoments.ShareParams();
                    weibo.setShareType(Platform.SHARE_WEBPAGE);
//                weibo.title=context.getResources().getString(R.string.app_name);
                    weibo.setVenueDescription("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(context) + "红包会更多！");
                    weibo.setComment("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(context) + "红包会更多！");
                    if (webid == 0) {
                        url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881185053";
                        weibo.imageUrl = context.getResources().getString(R.string.shareimage_toutiao);
                    } else {
                        weibo.imageUrl = icon;

                    }
                    weibo.title = title;
                    weibo.url = url;
                    Platform weibos = ShareSDK.getPlatform(WechatMoments.NAME);
                    weibos.setPlatformActionListener(new PlatformActionListener() {
                        @Override
                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                        }

                        @Override
                        public void onError(Platform platform, int i, Throwable throwable) {

                        }

                        @Override
                        public void onCancel(Platform platform, int i) {

                        }
                    }); // 设置分享事件回调
                    weibos.share(weibo);
                    break;
            }
        } catch (Exception e) {

        }
        MobclickAgent.onEvent(context, "click_invite", map);

        dismiss();
    }

    private void showFeed(int i){
        try {
            if (feedList!=null&&feedList.size()>0){
                if (i<feedList.size()){
                    setFeed((NativeAdInfo) feedList.get(i));
                }else {
                    i = 0;
                    getfeed();
                    return;
                }
            }else {
                getfeed();
            }
        } catch (Exception e) {

        }
    }

    private void setFeed(final NativeAdInfo asdbean){
        if (!TextUtils.isEmpty(asdbean.getImageUrl())) {
            Glide.with(context).load(asdbean.getImageUrl()).placeholder(R.mipmap.img_bg).centerCrop().into(bigim);
        }
        Glide.with(context).load(asdbean.getIconUrl()).placeholder(R.mipmap.img_bg).override(100,100).centerCrop().into(photo);
        content.setText(asdbean.getDescription());
        pix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asdbean.onClick(new View(context));
            }
        });
    }
    //feed流广告广告
    public void getfeed() {
        AdViewNativeManager.getInstance(context).requestAd(context, "SDK20170914090420rr4pmvotgihhrps", new AdViewNativeListener() {
            @Override
            public void onAdFailed(String s) {
            }
            @Override
            public void onAdRecieved(String s, ArrayList arrayList) {
                if (arrayList != null && arrayList.size() >= 0) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        feedList.add(arrayList.get(i));
                        NativeAdInfo o = (NativeAdInfo) arrayList.get(i);
                        o.onDisplay(new View(context));
                    }
                }
            }

            @Override
            public void onAdStatusChanged(String s, int i) {
                LogUtils.e(s + "wwwwwwwwww" + i);
            }
        });

    }

}