package com.tianguo.zxz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.LoginBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.EditTextUtil;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.MD5;
import com.tianguo.zxz.uctils.PhoneCodeUtiles;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/7/20.
 */

public class newsLoginActivity extends BaseActivity {
    @BindView(R.id.ed_lgin_phone)
    EditText edLginPhone;
    @BindView(R.id.iv_lging_dele)
    ImageView ivLgingDele;
    @BindView(R.id.ed_lgin_sms)
    EditText edLginSms;
    @BindView(R.id.tv_lgin_send_sms)
    TextView tvLginSendSms;
    @BindView(R.id.tv_ed_password)
    EditText tvEdPassword;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_news_info)
    Button btNewsInfo;
    @BindView(R.id.tv_old_info)
    TextView tvOldInfo;
    @BindView(R.id.tv_news_passwork)
    TextView tvNewsPasswork;
    @BindView(R.id.iv_share_wechat)
    ImageView ivShareWechat;
    @BindView(R.id.iv_share_qq)
    ImageView ivShareQq;
    @BindView(R.id.three_login)
    LinearLayout threeLogin;
    @BindView(R.id.xian2)
    View xian1;
    @BindView(R.id.rl_threed_login)
    RelativeLayout rlthree;
    String platform;

    //新增的
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.tv_login_register)
//    TextView tvLoginRegister;
    private String openId;
    private String nickname;
    private String userIcon;
    private Intent intent;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_login;
    }

    @Override
    protected void initView() {
        //新增的
//        tvTitle.setText("登陆"+getString(R.string.app_name));
        tvEdPassword.setVisibility(View.VISIBLE);
        btNewsInfo.setVisibility(View.VISIBLE);
        xian1.setVisibility(View.VISIBLE);
        rlthree.setVisibility(View.VISIBLE);
        threeLogin.setVisibility(View.VISIBLE);
        EditTextUtil editTextUtil = new EditTextUtil();
        editTextUtil.set(tvEdPassword, "请输入字母或数字");
        tvEdPassword.setHint("请输入密码");
        tvOldInfo.setVisibility(View.VISIBLE);
        tvNewsPasswork.setVisibility(View.VISIBLE);
        PhoneCodeUtiles.PhoneNum(edLginSms, edLginPhone);
        LogUtils.e("newsLoginActivity进来了", "gjj");
    }

    @Override
    protected void initData() {
        intent = new Intent(newsLoginActivity.this, NewsInfoActivty.class);
    }

    public void getLoginThree() {
        setLoadingFlag(false);
        String token = "tianguoZXZ";
        String signStr = token + platform + openId;
        MD5 m = new MD5();
        String sign = m.getMD5ofStr(signStr);
        HashMap<String, Object> map = new HashMap<>();
        map.put("platform", platform);
        map.put("openid", openId);
        map.put("sign", sign);
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        if (!TextUtils.isEmpty(nickname)) {
            map.put("nick", nickname);
        }
        if (!TextUtils.isEmpty(userIcon)) {
            map.put("head", userIcon);
        }
        LogUtils.e(sign.toString());
        Observable logins = RetroFactory.getInstance().getLogingThree(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<LoginBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LoginBean loginBean) {
                SharedPreferencesUtil.saveID(newsLoginActivity.this, loginBean.getUser().getU());
                SharedPreferencesUtil.saveSSO(newsLoginActivity.this, loginBean.getSso());
                if (loginBean != null) {
                    saveIsNew(loginBean.getNewbie());
                }
                if (loginBean.getNewbie() == 1) {
                    Intent intent = new Intent(newsLoginActivity.this, PhoneYanActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(newsLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onHandleError(int code, String message) {
                ToastUtil.showMessage(code + "" + message + ""
                );

            }
        });
    }

    /**
     * 第三方登录成功后的回调
     *
     * @param weibo
     */
    public void LogingThree(final Platform weibo) {
        String accessToken = weibo.getDb().getToken(); // 获取授权token
        weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                // 获取用户在此平台的ID
                openId = weibo.getDb().getUserId();
                // 获取用户昵称
                nickname = weibo.getDb().getUserName();
                userIcon = weibo.getDb().getUserIcon();
                getLoginThree();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtils.e(throwable.toString() + "wwwwww");
                LogUtils.e(platform.toString() + "wwwwww");

            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtils.e(i + "wwwwssssww");
                LogUtils.e(platform.toString() + "wwwwssssww");
            }
        }); // 设置分享事件回调
        weibo.authorize();//单独授权
        weibo.showUser(null);//授权并获取用户信息
    }

    @OnClick({R.id.iv_lging_dele, R.id.tv_lgin_send_sms, R.id.bt_login, R.id.bt_news_info,
            R.id.tv_old_info, R.id.tv_news_passwork, R.id.iv_share_wechat, R.id.iv_share_qq,
            })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_lging_dele:
                edLginPhone.setText("");
                edLginPhone.setHint("请输入手机号");
                break;
            case R.id.tv_lgin_send_sms:
                break;
            case R.id.bt_login:

                if (TextUtils.isEmpty(edLginPhone.getText().toString().trim())) {
                    ToastUtil.showMessage("请填写手机号");
                    return;
                } else if (edLginPhone.getText().toString().trim().length() != 11) {
                    ToastUtil.showMessage("请填写正确手机号");
                    return;
                }
                if (TextUtils.isEmpty(tvEdPassword.getText().toString().trim())) {
                    ToastUtil.showMessage("填写密码");
                    return;
                }
                getLogin();
                break;
            case R.id.bt_news_info:
                intent.putExtra("news", true);
                startActivityForResult(intent, 102);
                finish();
                break;
            case R.id.tv_old_info:
                startActivityForResult(new Intent(newsLoginActivity.this, LoginActivity.class), 101);
                finish();
                break;
            case R.id.tv_news_passwork:
                intent.putExtra("news", false);
                startActivityForResult(intent, 102);
                finish();
                break;
            case R.id.iv_share_wechat:
                platform = "wx";
                Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
                LogingThree(weixin);
                break;
            case R.id.iv_share_qq:
                platform = "qq";
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                LogingThree(qq);
                break;
//            case R.id.tv_login_register:
//                intent.putExtra("news", true);
//                startActivityForResult(intent, 102);
//                finish();
//                break;
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                ToastUtil.showMessage("再按一次退出程序");
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void saveIsNew(int newbie) {
        LogUtils.e("是否是新手" + newbie, "gjj");
        if (newbie == 1) {
            SharedPreferencesUtil.saveIsNew(newsLoginActivity.this, true);
        }
    }

    public void getLogin() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", edLginPhone.getText().toString());
        map.put("pwd", tvEdPassword.getText().toString().trim());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getNewsLogin(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<LoginBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LoginBean loginBean) {
                SharedPreferencesUtil.saveID(newsLoginActivity.this, loginBean.getUser().getU());
                SharedPreferencesUtil.saveSSO(newsLoginActivity.this, loginBean.getSso());
                if (loginBean != null) {
                    saveIsNew(loginBean.getNewbie());
                }
                if (loginBean.getNewbie() == 1) {
                    Intent intent = new Intent(newsLoginActivity.this, PhoneLoginYaoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(newsLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        });

    }



}
