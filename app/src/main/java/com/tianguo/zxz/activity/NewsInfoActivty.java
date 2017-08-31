package com.tianguo.zxz.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.SoWebActivity;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.LoginBean;
import com.tianguo.zxz.bean.MYBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.EditTextUtil;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.PhoneCodeUtiles;
import com.tianguo.zxz.uctils.ResendCodeTimer;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/7/20.
 */

public class NewsInfoActivty extends BaseActivity {
    @BindView(R.id.ed_lgin_phone)
    EditText edLginPhone;
    @BindView(R.id.iv_lging_dele)
    ImageView ivLgingDele;
    @BindView(R.id.ed_lgin_sms)
    EditText edLginSms;
    @BindView(R.id.tv_lgin_send_sms)
    TextView tvLginSendSms;
    @BindView(R.id.xian1)
    View xian1;
    @BindView(R.id.xian2)
    View xian2;
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
    @BindView(R.id.ll_new_password)
    LinearLayout llnews;
    private ResendCodeTimer timer;
    private Observable logins;


//    //新增的
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.tv_login_register)
//    TextView tvLoginRegister;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_login;
    }

    @Override
    protected void initView() {
        LogUtils.e("NewsInfoActivty进来了","gjj");
        if (getIntent().getBooleanExtra("news", true)) {
            btLogin.setText("注册并领取红包");
//            tvYes.setMovementMethod(LinkMovementMethod.getInstance());
            tvYes.setVisibility(View.VISIBLE);

            //新增的
//            tvTitle.setText("注册"+getString(R.string.app_name));
//            tvLoginRegister.setText("登陆");
        } else {
            tvEdPassword.setHint("新密码（6-20位数字或字母）");
            btLogin.setText("确定");

        }
        tvOldInfo.setVisibility(View.VISIBLE);
        tvOldInfo.setText("返回登录");
        EditTextUtil editTextUtil = new EditTextUtil();
        editTextUtil.set(tvEdPassword, "请输入字母或数字");
        xian1.setVisibility(View.VISIBLE);
        xian2.setVisibility(View.VISIBLE);
        tvEdPassword.setVisibility(View.VISIBLE);
        llnews.setVisibility(View.VISIBLE);
        PhoneCodeUtiles.PhoneNum(edLginSms, edLginPhone);

    }

    public void getSMS() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", edLginPhone.getText().toString());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        if (getIntent().getBooleanExtra("news", true)){
            logins = RetroFactory.getInstance().getNewSMS(map);
        }else {
            logins = RetroFactory.getInstance().getSMS(map);

        }
        logins.compose(composeFunction).subscribe(new BaseObserver<MYBean>(this, pd) {
            @Override
            protected void onHandleSuccess(MYBean loginBean) {
                ToastUtil.showMessage("验证码已发送至您的手机请注意查收");
                timer.setTv(tvLginSendSms);
                timer.cancel();
                timer.start();
            }
        });

    }

    public void getNewsInfo() {
        HashMap<String, Object> map = new HashMap<>();
        LogUtils.e("sssssssss"+tvEdPassword.getText().toString());
        map.put("phone", edLginPhone.getText().toString());
        map.put("code", edLginSms.getText().toString().trim());
        map.put("pwd", tvEdPassword.getText().toString().trim());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getNewsInfo(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<LoginBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LoginBean loginBean) {
                SharedPreferencesUtil.saveID(NewsInfoActivty.this, loginBean.getUser().getU());
                SharedPreferencesUtil.saveSSO(NewsInfoActivty.this, loginBean.getSso());
                if (loginBean!=null){
                    saveIsNew(loginBean.getNewbie());
                }
                if (loginBean.getNewbie() == 1) {
                    Intent intent = new Intent(NewsInfoActivty.this, PhoneLoginYaoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(NewsInfoActivty.this, MainActivity.class);
                    startActivity(intent);
                    setResult(101);
                    finish();
                }

            }
        });

    }

    private void saveIsNew(int newbie) {
        LogUtils.e("是否是新手"+newbie,"gjj");
        if (newbie == 1){
            SharedPreferencesUtil.saveIsNew(NewsInfoActivty.this, true);
        }
    }

    public void getNesgetInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", edLginPhone.getText().toString());
        map.put("code", edLginSms.getText().toString().trim());
        map.put("pwd", tvEdPassword.getText().toString().trim());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getNewPassWordInfo(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<LoginBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LoginBean loginBean) {
                SharedPreferencesUtil.saveID(NewsInfoActivty.this, loginBean.getUser().getU());
                SharedPreferencesUtil.saveSSO(NewsInfoActivty.this, loginBean.getSso());
                if (loginBean!=null){
                    saveIsNew(loginBean.getNewbie());
                }
                if (loginBean.getNewbie() == 1) {
                    Intent intent = new Intent(NewsInfoActivty.this, PhoneLoginYaoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(NewsInfoActivty.this, MainActivity.class);
                    startActivity(intent);
                    setResult(101);
                    finish();

                }

            }
        });

    }

    @Override
    protected void initData() {
        timer = new ResendCodeTimer(this, false);

    }


    @OnClick({R.id.iv_lging_dele, R.id.tv_lgin_send_sms, R.id.bt_login,R.id.tv_old_info,R.id.tv_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_lging_dele:
                edLginPhone.setText("");
                edLginPhone.setHint("请输入手机号");
                break;
            case R.id.tv_lgin_send_sms:
                if (TextUtils.isEmpty(edLginPhone.getText().toString().trim())) {
                    ToastUtil.showMessage("请填写手机号");
                    return;
                } else if (edLginPhone.getText().toString().trim().length() != 11) {
                    ToastUtil.showMessage("请填写正确手机号");
                    return;
                }
                getSMS();
                break;
            case R.id.bt_login:
                if (TextUtils.isEmpty(edLginSms.getText().toString().trim())) {
                    ToastUtil.showMessage("填写验证码");
                    return;
                }
                if (TextUtils.isEmpty(edLginPhone.getText().toString().trim())) {
                    ToastUtil.showMessage("请填写手机号");
                    return;
                } else if (edLginPhone.getText().toString().trim().length() != 11) {
                    ToastUtil.showMessage("请填写正确手机号");
                    return;
                }
                if (TextUtils.isEmpty(tvEdPassword.getText().toString().trim())) {
                    ToastUtil.showMessage("请填写密码");
                    return;
                }
                if (tvEdPassword.length() < 6) {
                    ToastUtil.showMessage("密码最少6位");
                    return;
                }

                if (getIntent().getBooleanExtra("news", true)) {
                    getNewsInfo();
                } else {
                    getNesgetInfo();
                }
                break;
            case R.id.tv_old_info:
                Intent intent = new Intent(NewsInfoActivty.this, newsLoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_yes:
                Intent protocolIntent = new Intent(NewsInfoActivty.this, SoWebActivity.class);
                protocolIntent.putExtra(Constant.TITLE,getString(R.string.user_protocol));
                protocolIntent.putExtra("url","http://www.tianinfo.cn/tgagreement.html");
                startActivity(protocolIntent);
                break;
//            case R.id.tv_login_register:
//                Intent returnLoginIntent = new Intent(NewsInfoActivty.this, newsLoginActivity.class);
//                startActivity(returnLoginIntent);
//                finish();
//                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(NewsInfoActivty.this, newsLoginActivity.class);
        startActivity(intent);
    }
}
