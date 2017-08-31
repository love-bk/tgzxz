package com.tianguo.zxz.fragment;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.ApprenticeActivity;
import com.tianguo.zxz.activity.ErWeiMActivity;
import com.tianguo.zxz.activity.MyActivity.Nowactivity;
import com.tianguo.zxz.activity.MyActivity.SoWebActivity;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.InviteIncomeBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import io.reactivex.Observable;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by lx on 2017/5/4.
 */

public class YaoFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_enlightening_icome)
    TextView tvEnlighteningIcome;   //收徒收入
    @BindView(R.id.tv_disciple_num)
    TextView tvDiscipleNum;         //徒弟个数
    @BindView(R.id.tv_dd_num)
    TextView tvDdNum;               //徒孙个数
    @BindView(R.id.tv_yaoqing_num)
    TextView tvYaoqingNum;
    @BindView(R.id.tv_highest)
    TextView tvHighest;
    @BindView(R.id.tv_subtitle1)
    TextView tvSubtitle1;
    @BindView(R.id.tv_subtitle2)
    TextView tvSubtitle2;
    @BindView(R.id.tv_subtitle3)
    TextView tvSubtitle3;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    private HashMap<String, String> map;
    private Intent intent;
    private String colorStr = "#FD0098";
    private Intent intentH5;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        intent = new Intent(main, ApprenticeActivity.class);
        intentH5 = new Intent(main, Nowactivity.class);
        //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍   ,0.5表示一半
        changeTextStyle(tvDiscipleNum, "徒弟");
        changeTextStyle(tvEnlighteningIcome, "收徒收入");
        changeTextStyle(tvDdNum, "徒孙");
        view.findViewById(R.id.iv_share_wechat).setOnClickListener(this);
        view.findViewById(R.id.iv_share_qq).setOnClickListener(this);
        view.findViewById(R.id.iv_share_qzone).setOnClickListener(this);
        view.findViewById(R.id.iv_share_sinaweibo).setOnClickListener(this);
        view.findViewById(R.id.tv_yaoqing_image).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                intentH5.putExtra(Constant.URL,Constant.INVITE_URL);
                startActivity(intentH5);
            }
        });
        view.findViewById(R.id.tv_not_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main, SoWebActivity.class);
                intent.putExtra("url", Constant.INVITE_TUTORIAL_URL);
                startActivity(intent);
            }
        });
        tvYaoqingNum.setText(SharedPreferencesUtil.getID(main) + "");
        map = new HashMap<>();
//        changeTextStyle(tvHighest);

        initTextColor(tvSubtitle1, "得", "的", "+");
        initTextColor(tvSubtitle2, "献", "，1个", null);
        initTextColor(tvSubtitle3, "励", "，", null);
        tvHeadTitle.setText("\u3000\u3000"+tvHeadTitle.getText().toString());
        initTextColor(tvHeadTitle, "。", "，徒弟", null);
        getInviteIncome();
    }

    private void initTextColor(TextView textView, String startStr, String endStr, String middleStr) {
        String title1 = textView.getText().toString();
        SpannableString ss = new SpannableString(title1);
        if (middleStr != null) {
            if (title1 != null && title1.contains(startStr) && title1.contains(endStr) && title1.contains(middleStr)) {
                int start = title1.indexOf(startStr)+1;
                int middle = title1.indexOf(middleStr);
                int end = title1.indexOf(endStr);
                //设置字体前景色
                ss.setSpan(new ForegroundColorSpan(Color.parseColor(colorStr)), start, middle, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(Color.parseColor(colorStr)), middle+1, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {
            if (title1 != null && title1.contains(startStr) && title1.contains(endStr)) {
                int start = title1.indexOf(startStr)+1;
                int end = title1.indexOf(endStr);
                //设置字体前景色
                ss.setSpan(new ForegroundColorSpan(Color.parseColor(colorStr)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(ss);

    }


    private void changeTextStyle(TextView textView) {
        String source = textView.getText().toString();
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new UnderlineSpan(), 0, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
    }

    private void changeTextStyle(TextView textView, String s) {
        String source = textView.getText().toString();
        if (source != null && source.contains(s)) {
            int end = source.indexOf(s);
            SpannableString ss = new SpannableString(source);
            ss.setSpan(new RelativeSizeSpan(1.2f), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置字体前景色
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#fd9866")), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置字体样式: NORMAL正常，BOLD粗体，ITALIC斜体，BOLD_ITALIC粗斜体
            ss.setSpan(new StyleSpan(Typeface.BOLD), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(ss);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.frgament_main_yaoqing;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_share_wechat:
                map.put("feed", "微信");
                Wechat.ShareParams wexin = new Wechat.ShareParams();
                wexin.setShareType(Platform.SHARE_WEBPAGE);
                wexin.title = main.getResources().getString(R.string.app_name);
                wexin.setComment("送你2元现金，想领取点此下载，填写邀请码" + SharedPreferencesUtil.getID(main) + "，红包会更多");
                wexin.text = "一次玩这货瞬间到手2元！这个APP真牛X，记得填写我的邀请码" + SharedPreferencesUtil.getID(main) + "，奖励瞬间到账。";
                wexin.imageUrl = main.getResources().getString(R.string.shareimage_toutiao);
                wexin.url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363789133793";
                Platform Weixin = ShareSDK.getPlatform(Wechat.NAME);
                Weixin.setPlatformActionListener(new PlatformActionListener() {
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                    }

                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        MobclickAgent.onEvent(main, "click_invite", map);

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
                qq.setTitle(main.getResources().getString(R.string.app_name));
                qq.setTitleUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881185054"); // 标题的超链接
                qq.setText("一次玩这货瞬间到手2元！这个APP真牛X，记得填写我的邀请码" + SharedPreferencesUtil.getID(main) + "，奖励瞬间到账。");
                qq.setComment("送你2元现金，想领取点此下载，填写邀请码" + SharedPreferencesUtil.getID(main) + "，红包会更多");

                qq.setImageUrl(main.getResources().getString(R.string.shareimage_toutiao));
                Platform QQ = ShareSDK.getPlatform(cn.sharesdk.tencent.qq.QQ.NAME);
                QQ.setPlatformActionListener(new PlatformActionListener() {
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                    }

                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        //分享成功的回调
                        MobclickAgent.onEvent(main, "click_invite", map);

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
                sp.setTitle(main.getResources().getString(R.string.app_name));
                sp.setTitleUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881484428"); // 标题的超链接
                sp.setText("一次玩这货瞬间到手2元！这个APP真牛X，记得填写我的邀请码" + SharedPreferencesUtil.getID(main) + "，奖励瞬间到账。");
                sp.setImageUrl(main.getResources().getString(R.string.shareimage_toutiao));
                sp.setComment("送你2元现金，想领取点此下载，填写邀请码" + SharedPreferencesUtil.getID(main) + "，红包会更多");

                sp.setSiteUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881484428");
                Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                qzone.setPlatformActionListener(new PlatformActionListener() {
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                    }

                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        //分享成功的回调
                        MobclickAgent.onEvent(main, "click_invite", map);

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
                weibo.title = main.getResources().getString(R.string.app_name);
                weibo.setComment("送你2元现金，想领取点此下载，填写邀请码" + SharedPreferencesUtil.getID(main) + "，红包会更多");
                weibo.text = "看资讯拿红包，想领取就点此下载，邀请码" + SharedPreferencesUtil.getID(main);
                weibo.imageUrl = main.getResources().getString(R.string.shareimage_toutiao);
                weibo.url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881185053";
                Platform weibos = ShareSDK.getPlatform(WechatMoments.NAME);
                weibos.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        MobclickAgent.onEvent(main, "click_invite", map);

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
    }

    @OnClick({R.id.rl_yaoqing_num, R.id.tv_disciple_num, R.id.tv_dd_num, R.id.tv_look_rule1, R.id.tv_look_rule2, R.id.tv_look_rule3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_yaoqing_num:
                @SuppressLint("WrongConstant") ClipboardManager cm = (ClipboardManager) main.getSystemService(CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvYaoqingNum.getText());
                ToastUtil.showMessage("复制成功，可以发给朋友们了。");
                break;
            case R.id.tv_disciple_num:
                intent.putExtra(Constant.DISCIPLE, 0);
                startActivity(intent);
                break;
            case R.id.tv_dd_num:
                intent.putExtra(Constant.DISCIPLE, 1);
                startActivity(intent);
                break;
            case R.id.tv_look_rule1:
            case R.id.tv_look_rule2:
                intentH5.putExtra(Constant.URL,Constant.INVITE_URL);
                startActivity(intentH5);
                break;
            case R.id.tv_look_rule3:
                //暑期活动的跳转
                intentH5.putExtra(Constant.URL,Constant.ACTIVITY_URL);
                startActivity(intentH5);
                break;
        }
    }

    @OnClick(R.id.tv_yaoqing_erweima)
    public void onErweima() {
        startActivity(new Intent(main, ErWeiMActivity.class));
    }



    private void getInviteIncome() {
        main.setLoadingFlag(false);
        final HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        Observable observable = RetroFactory.getInstance().getInviteIncome(map);
        observable.compose(main.composeFunction).subscribe(new BaseObserver<InviteIncomeBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(InviteIncomeBean bean) {
                if (bean != null) {
                    tvDiscipleNum.setText(bean.getFollowNum()+"个\n徒弟");
                    tvDdNum.setText(bean.getSubFollowNum()+"个\n徒孙");
                    tvEnlighteningIcome.setText(""+ ((double) bean.getMoney())/100.00+"元\n收徒收入");
                    changeTextStyle(tvDiscipleNum, "徒弟");
                    changeTextStyle(tvEnlighteningIcome, "收徒收入");
                    changeTextStyle(tvDdNum, "徒孙");
                }
            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
            }
        });
    }

}
