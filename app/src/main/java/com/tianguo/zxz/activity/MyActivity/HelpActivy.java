package com.tianguo.zxz.activity.MyActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.MYBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.PhoneCodeUtiles;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/4/19.
 */

public class HelpActivy extends BaseActivity {

    @BindView(R.id.tv_help_phone)
    EditText tvHelpPhone;
    @BindView(R.id.tv_help_maile)
    EditText tvHelpMaile;
    @BindView(R.id.tv_help_cent)
    EditText tvHelpCent;
    @BindView(R.id.vt_help_commid)
    Button vtHelpCommid;
    @BindView(R.id.tv_back)
    TextView tvBack;
    private String mtyb;
    private String mtype;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_help;
    }

    @Override
    protected void initView() {
        tvBack.setText(R.string.advice_feedback);
        PhoneCodeUtiles.PhoneNum(tvHelpPhone);
        getPhoneInfo();
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.vt_help_commid)
    public void onViewClicked() {
        if (TextUtils.isEmpty(tvHelpPhone.getText().toString().trim())) {
            ToastUtil.showMessage("请填写手机号");
            return;
        } else if (tvHelpPhone.getText().toString().trim().length() != 11) {
            ToastUtil.showMessage("请填写正确手机号");
            return;
        } else if (TextUtils.isEmpty(tvHelpCent.toString().trim())) {
            ToastUtil.showMessage(" 请填写内容");

        }
        getTrue();

    }

    public void getTrue() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", tvHelpPhone.getText().toString());
        map.put("type", 1);
        map.put("model", mtyb + mtype);
        map.put("version", UpdateAppUtil.getAPPLocalVersion(this));
        map.put("content", tvHelpCent.getText().toString().trim());
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        if (tvHelpMaile.getText().toString().trim() != null || !tvHelpMaile.getText().toString().isEmpty()) {
            map.put("mail", tvHelpMaile.getText().toString().trim());
        }
        Observable logins = RetroFactory.getInstance().getHanKui(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<MYBean>(this, pd) {
            @Override
            protected void onHandleSuccess(MYBean loginBean) {
                finish();
                ToastUtil.showMessage("提交成功");
            }
        });

    }


    @OnClick(R.id.iv_back)
    public void onClicked() {
        finish();
    }

    public void getPhoneInfo() {
        @SuppressLint("WrongConstant") TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        // 手机品牌  
        mtyb = Build.BRAND;
        // 手机型号  
        mtype = Build.MODEL;
        String imei = tm.getDeviceId();
        String imsi = tm.getSubscriberId();
        String numer = tm.getLine1Number();// 手机号码  
        String serviceName = tm.getSimOperatorName();// 运营商  
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
