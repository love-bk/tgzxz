package com.tianguo.zxz.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
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
 * Created by lx on 2017/5/8.
 */

public class PhoneYanActivity extends BaseActivity {
    @BindView(R.id.iv_yanzheng_tiaoguo)
    TextView ivYanzhengTiaoguo;
    @BindView(R.id.et_bangding_phone)
    EditText etBangdingPhone;
    @BindView(R.id.et_bangding_yanzheng)
    EditText etBangdingYanzheng;
    @BindView(R.id.tv_bangding_yanzheng)
    TextView tvBangdingYanzheng;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_yanzheng_yaoqingma)
    EditText etYanzhengYaoqingma;
    private ResendCodeTimer timer;
    private Dialog dialog;
    private MyApplictation application;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_phone_yanzheng;
    }

    @Override
    protected void onDestroy() {
        try{


            application.removeActivity(this);
        }catch (Exception e){

        }
        super.onDestroy();
    }

    @Override
    protected void initView() {
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        dialog = new Dialog(this, R.style.dialog);
        View inflate = View.inflate(this, R.layout.dialog_yanzheng_phone, null);
        inflate.findViewById(R.id.tv_back_diogl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(PhoneYanActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        inflate.findViewById(R.id.tv_dism_diove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setContentView(inflate);

        timer = new ResendCodeTimer(this, false);
        etBangdingPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        etBangdingYanzheng.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        etYanzhengYaoqingma.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etYanzhengYaoqingma.setHint("");
                } else {
                    etYanzhengYaoqingma.setHint("邀请码（可不填）");
                }
            }
        });
        PhoneCodeUtiles.PhoneNum(etYanzhengYaoqingma,etBangdingYanzheng,etYanzhengYaoqingma);

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_yanzheng_tiaoguo, R.id.tv_bangding_yanzheng, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_yanzheng_tiaoguo:
                dialog.show();
                break;
            case R.id.tv_bangding_yanzheng:
                if (TextUtils.isEmpty(etBangdingPhone.getText().toString().trim())) {
                    ToastUtil.showMessage("请填写手机号");
                    return;
                } else if (etBangdingPhone.getText().toString().trim().length() != 11) {
                    ToastUtil.showMessage("请填写正确手机号");
                    return;
                }
                getSMS();
                break;
            case R.id.tv_login:
                if (TextUtils.isEmpty(etBangdingYanzheng.getText().toString().trim())) {
                    ToastUtil.showMessage("请输入验证码");
                    return;
                } else if (TextUtils.isEmpty(etBangdingPhone.getText().toString().trim())) {
                    ToastUtil.showMessage("请填写手机号");
                    return;
                } else if (etBangdingPhone.getText().toString().trim().length() != 11) {
                    ToastUtil.showMessage("请填写正确手机号");
                    return;
                }
                getTrue();
                break;
        }
    }

    public void getSMS() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", etBangdingPhone.getText().toString());
        map.put("devid",SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getSMS(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<MYBean>(this, pd) {
            @Override
            protected void onHandleSuccess(MYBean loginBean) {
                ToastUtil.showMessage("验证码已发送至您的手机请注意查收");
                timer.setTv(tvBangdingYanzheng);
                timer.cancel();
                timer.start();
            }
        });

    }

    public void getTrue() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("phone", etBangdingPhone.getText().toString());
        map.put("code", etBangdingYanzheng.getText().toString().trim());
        if (!etYanzhengYaoqingma.getText().toString().trim().isEmpty()) {
            map.put("invitor", etYanzhengYaoqingma.getText().toString());
        }
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("devid",SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        LogUtils.e(etBangdingPhone.getText().toString() + "ee" + etBangdingYanzheng.getText().toString().trim() +
                etYanzhengYaoqingma.getText().toString() + SharedPreferencesUtil.getSSo(this));
        Observable logins = RetroFactory.getInstance().getYanzheng(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<MYBean>(this, pd) {
            @Override
            protected void onHandleSuccess(MYBean loginBean) {
                if (loginBean.getInvitor()>0){
                    Intent intent = new Intent(PhoneYanActivity.this, MainActivity.class);
                    intent.putExtra("isBangding",true);
                    startActivity(intent);
                }else {
                Intent intent = new Intent(PhoneYanActivity.this, MainActivity.class);
                startActivity(intent);
                }
                finish();
            }
        });

    }
}
