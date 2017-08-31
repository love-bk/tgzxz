package com.tianguo.zxz.uctils;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by lx on 2017/7/7.
 */

public class PopViewUtils  extends PopupWindow{
    BaseActivity main;
    public PopViewUtils(final BaseActivity main) {
        this.main=main;
        View inflate = View.inflate(main, R.layout.pop_hongbap, null);
        this.setContentView(inflate);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setTouchable(true);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.getContentView().setFocusableInTouchMode(true);
        this.getContentView().setFocusable(true);
//        this.setAnimationStyle(R.style.take_hongbao_anim);
        inflate.findViewById(R.id.tv_sen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wechat.ShareParams wexin = new Wechat.ShareParams();
                wexin.setShareType(Platform.SHARE_WEBPAGE);
                wexin.title =main.getResources().getString(R.string.app_name);
                wexin.text = "看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(main) + "红包会更多！";
                wexin.setComment("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(main) + "红包会更多！");
                wexin.imageUrl = main.getResources().getString(R.string.shareimage_toutiao);
                wexin.url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363789133793";
                Platform Weixin = ShareSDK.getPlatform(Wechat.NAME);
                Weixin.setPlatformActionListener(new PlatformActionListener() {
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                        LogUtils.e(arg0 + "" + arg1 + "" + arg2 + "wwwww");
                        ToastUtil.showMessage("分享失败");
                        PopViewUtils.this.dismiss();

                    }

                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        LogUtils.e(arg0 + "" + arg1 + "" + arg2 + "w2wwww");
                        PopViewUtils.this.dismiss();

                        //分享成功的回调
                    }

                    public void onCancel(Platform arg0, int arg1) {
                        LogUtils.e(arg0 + "" + arg1 + "" + "www3ww");
                        ToastUtil.showMessage("分享失败");
                        //取消分享的回调
                    }
                });
                Weixin.share(wexin);
            }
        });
        inflate.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopViewUtils.this.dismiss();

            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

public  void  show(View hongImagView, int centerHorizontal, int i, int i1){
    backgroundAlpha(0.3f);

        this.showAtLocation(hongImagView,centerHorizontal,i,i1);
}
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = main.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        main.getWindow().setAttributes(lp);
    }
}
