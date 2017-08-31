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
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.LoginBean;
import com.tianguo.zxz.bean.MYBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
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
 * Created by lx on 2017/4/27.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.ll_new_password)
    LinearLayout llnews;
    @BindView(R.id.iv_share_wechat)
    ImageView ivShareWechat;
    @BindView(R.id.iv_share_qq)
    ImageView ivShareQq;
    @BindView(R.id.tv_old_info)
    TextView tvNewsPasswork;
    private ResendCodeTimer timer;
    @BindView(R.id.ed_lgin_phone)
    EditText edLginPhone;
    @BindView(R.id.ed_lgin_sms)
    EditText edLginSms;
    @BindView(R.id.tv_lgin_send_sms)
    TextView tvLginSendSms;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.xian2)
    View xian2;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_login;
    }

    @Override
    protected void initView() {
        xian2.setVisibility(View.VISIBLE);
        llnews.setVisibility(View.VISIBLE);
        tvNewsPasswork.setVisibility(View.VISIBLE);
        tvNewsPasswork.setText("账号密码登录");
        PhoneCodeUtiles.PhoneNum(edLginSms, edLginPhone);
        LogUtils.e("LoginActivity进来了","gjj");

    }

    @Override
    protected void initData() {
        timer = new ResendCodeTimer(this, false);
    }

    @OnClick({R.id.tv_lgin_send_sms, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                getLogin();
                break;
        }
    }

    private void saveIsNew(int newbie) {
        LogUtils.e("是否是新手"+newbie,"gjj");
        if (newbie == 1){
            SharedPreferencesUtil.saveIsNew(LoginActivity.this, true);
        }
    }
    public void getLogin() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", edLginPhone.getText().toString());
        map.put("code", edLginSms.getText().toString().trim());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getLogin(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<LoginBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LoginBean loginBean) {
                SharedPreferencesUtil.saveID(LoginActivity.this, loginBean.getUser().getU());
                SharedPreferencesUtil.saveSSO(LoginActivity.this, loginBean.getSso());
                if (loginBean!=null){
                    saveIsNew(loginBean.getNewbie());
                }
                if (loginBean.getNewbie() == 1) {
                    Intent intent = new Intent(LoginActivity.this, PhoneLoginYaoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    setResult(101);
                    finish();

                }

            }
        });

    }

    public void getSMS() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", edLginPhone.getText().toString());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getSMS(map);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, newsLoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void finish() {
        hideSystemKeyBoard(edLginPhone);
        super.finish();

    }

    @OnClick(R.id.iv_lging_dele)
    public void onViewClicked() {
        edLginPhone.setText("");
        edLginPhone.setHint("请输入手机号");
    }


    @OnClick(R.id.tv_old_info)
    public void onViewClick() {
        Intent intent = new Intent(LoginActivity.this, newsLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
