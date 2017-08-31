package com.tianguo.zxz.uctils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by lx on 2017/5/23.
 */

public class webDiogUtils extends PopupWindow implements View.OnClickListener {
    Context context;
    private final HashMap<String, String> map;
    private String url;
    int webid;
    private  TextView tv_yaoqing;
    private  TextView teile;
    private  TextView cent;

    public webDiogUtils(final Activity context) {
        map = new HashMap<>();
        this.context = context;

        if (context!=null){
        View inflate = View.inflate(context, R.layout.dialog_shared, null);
        setContentView(inflate);
        inflate.findViewById(R.id.iv_share_wechat).setOnClickListener(this);
        inflate.findViewById(R.id.iv_share_qq).setOnClickListener(this);
        inflate.findViewById(R.id.iv_share_qzone).setOnClickListener(this);
        inflate.findViewById(R.id.iv_share_sinaweibo).setOnClickListener(this);
        inflate.findViewById(R.id.tv_dilog_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_yaoqing = (TextView) inflate.findViewById(R.id.tv_yaoqing);
        teile = (TextView) inflate.findViewById(R.id.tv_news_dilog_teile);
        cent = (TextView) inflate.findViewById(R.id.tv_news_dilog_cent);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setTouchable(true);
        this.setOutsideTouchable(false);
//        this.setBackgroundDrawable(new BitmapDrawable());
        this.getContentView().setFocusableInTouchMode(true);
        this.getContentView().setFocusable(true);
        this.setAnimationStyle(R.style.take_photo_anim);
        }
    }

    String icon;
    String title;
    /**
     *
     * @param view  展示在那个view智商
     * @param webid webid判断是否传入新闻id
     * @param icon   传入icon
     * @param title  传入新闻内容
     */
    @SuppressLint("WrongConstant")
    public void isShow(View view, int webid, String icon, String title) {
        try {
            this.webid = webid;
            this.icon = icon;
            this.title = title;
            if (webid != 0) {
                tv_yaoqing.setVisibility(View.VISIBLE);
                this.title = title;
                teile.setText("分享资讯内容得红包");
                cent.setText( "每成功邀请一个徒弟，还能获得2.5元收徒奖励!"+"\n"+" 资讯内容可以通过以下分享方式让更多人看到；"
                );
                url = "http://news.tianh5.cn/news/detail.html?id=" + webid;
            } else {
                this. title = "看资讯拿红包，想领取就点此下载，邀请码"+SharedPreferencesUtil.getID(context);
                teile.setText("邀请收徒得红包");
                cent.setText("邀请好友成功收徒，即可获得2.5元邀请红包奖励，\n" +
                        "\n" +
                        "和更多师徒贡献红包");
                tv_yaoqing.setVisibility(View.GONE);
            }
            this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        }catch (Exception e){

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
        }catch (Exception e){

        }
        MobclickAgent.onEvent(context, "click_invite", map);

        dismiss();
    }
}
