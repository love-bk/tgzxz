package com.tianguo.zxz.uctils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianguo.zxz.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by lx on 2017/8/22.
 */

public class AwardPop extends PopupWindow {
    Activity context;
    TextView tvBack;
    TextView tvNoShowMoney;
    TextView tvAward;
    RelativeLayout rlShowMoney;
    TextView tvGoldShow;
    TextView tvMoneyShow;
    TextView tvMoneyHint;
    TextView tvOlnyGold;
    TextView tvNoMoney;
    TextView tvYesMoney;
    LinearLayout llShowMoney;
    public AwardPop(Activity context,onDismiss ondis) {
        this.ondis=ondis;
        this.context = context;
        View inflate = View.inflate(context, R.layout.pop_hongbao_awrad, null);

         tvBack = (TextView) inflate.findViewById(R.id.tv_back);
        llShowMoney = (LinearLayout) inflate.findViewById(R.id.ll_show_money);
        tvYesMoney = (TextView) inflate.findViewById(R.id.tv_yes_money);
        tvNoMoney = (TextView) inflate.findViewById(R.id.tv_no_money);
        tvOlnyGold = (TextView) inflate.findViewById(R.id.tv_olny_gold);
        tvMoneyHint = (TextView) inflate.findViewById(R.id.tv_money_hint);
        tvMoneyShow = (TextView) inflate.findViewById(R.id.tv_money_show);
        tvGoldShow = (TextView) inflate.findViewById(R.id.tv_gold_show);
        rlShowMoney = (RelativeLayout) inflate.findViewById(R.id.rl_show_money);
        tvAward = (TextView) inflate.findViewById(R.id.tv_award);
        tvNoShowMoney = (TextView) inflate.findViewById(R.id.tv_no_show_money);
        this.setContentView(inflate);
        this.setFocusable(true);
        this.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setTouchable(true);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.getContentView().setFocusableInTouchMode(true);
        this.getContentView().setFocusable(true);
    }

    @SuppressLint("WrongConstant")
    public void show(View hongImagView, int centerHorizontal, int i, int i1, double money, int gold, int arwadcont) {
        String s = "金币  " + "+"+gold;
        String s1 = "可获取红包个数" +"+"+arwadcont;
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
        if (arwadcont!=0){
            SpannableStringBuilder builder2 = new SpannableStringBuilder(s1);
            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(Color.GREEN);
            builder2.setSpan(redSpan2, 7, s1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvMoneyShow.setText(builder2);
        }else {
            tvMoneyShow.setVisibility(View.GONE);
        }
        if (money == 0) {
            SpannableStringBuilder builder = new SpannableStringBuilder(s);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.GREEN);
            builder.setSpan(redSpan, 2, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvGoldShow.setText(builder);
            tvNoShowMoney.setVisibility(View.VISIBLE);
            tvOlnyGold.setVisibility(View.VISIBLE);
            rlShowMoney.setVisibility(View.GONE);
            tvMoneyHint.setVisibility(View.GONE);
            llShowMoney.setVisibility(View.GONE);
            tvOlnyGold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ondis!=null) {
                        ondis.gold();
                    }
                }
            });
        } else {
            SpannableStringBuilder builder = new SpannableStringBuilder(s);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.GREEN);
            builder.setSpan(redSpan, 2, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvGoldShow.setText(builder);
            tvAward.setText(money/100+"");
            tvNoShowMoney.setVisibility(View.GONE);
            tvOlnyGold.setVisibility(View.GONE);
            rlShowMoney.setVisibility(View.VISIBLE);
            tvMoneyHint.setVisibility(View.VISIBLE);
            llShowMoney.setVisibility(View.VISIBLE);
            tvYesMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Wechat.ShareParams wexin = new Wechat.ShareParams();
                        wexin.setShareType(Platform.SHARE_WEBPAGE);
                        wexin.title = context.getResources().getString(R.string.app_name);
                        wexin.setComment("看新闻拿大红包，想领取点此进入，填我邀请码" + SharedPreferencesUtil.getID(context) + "红包会更多！");
                        wexin.url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1365875620906";
                            wexin.imageUrl = context.getResources().getString(R.string.shareimage_toutiao);
                        Platform Weixin = ShareSDK.getPlatform(Wechat.NAME);
                        Weixin.setPlatformActionListener(new PlatformActionListener() {
                            public void onError(Platform arg0, int arg1, Throwable arg2) {
                                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                                LogUtils.e(arg0 + "" + arg1 + "" + arg2 + "wwwww");
                            }

                            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                                LogUtils.e(arg0 + "" + arg1 + "" + arg2 + "w2wwwwwwwwww");
                                if (ondis!=null){
                                    ondis.money();

                                }
                                //分享成功的回调
                            }

                            public void onCancel(Platform arg0, int arg1) {
                                LogUtils.e(arg0 + "" + arg1 + "" + "www3ww");
                                //取消分享的回调
                            }
                        });
                        Weixin.share(wexin);

                }
            });
            tvNoMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ondis!=null) {
                        ondis.gold();
                    }
                }
            });
        }
        backgroundAlpha(0.3f);
        this.showAtLocation(hongImagView, centerHorizontal, i, i1);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }

    @Override
    public void dismiss() {
        backgroundAlpha(1f);

        super.dismiss();
    }

    onDismiss ondis;
 public    interface  onDismiss{
        void  money();
        void gold();
    }
}
