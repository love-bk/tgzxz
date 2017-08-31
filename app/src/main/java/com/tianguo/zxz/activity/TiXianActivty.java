package com.tianguo.zxz.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.MYBean;
import com.tianguo.zxz.bean.VersionInfo;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
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
 * Created by lx on 2017/4/28.
 */

public class TiXianActivty extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_tixian_name)
    EditText tvTixianName;
    @BindView(R.id.tv_tixian_zhifubao)
    EditText tvTixianZhifubao;
    @BindView(R.id.vt_help_commid)
    Button vtHelpCommid;
    @BindView(R.id.tv_back)
    TextView tvBack;

    private int money1;
    private Dialog dialog;
    private ResendCodeTimer timer;
    private EditText etBangdingPhone;
    private EditText etBangdingYanzheng;
    private Button btBangding;
    private TextView tvBangdingYanzheng;
    private Dialog dialog2;
    private MyApplictation application;
    private boolean withdrawal = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tixian;
    }

    @Override
    protected void onDestroy() {
        try {


            application.removeActivity(this);
        } catch (Exception e) {

        }
        super.onDestroy();
    }

    @Override
    protected void initView() {
        tvBack.setText(R.string.alipay_withdrawal);
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        timer = new ResendCodeTimer(this, false);
        money1 = getIntent().getIntExtra("money", 0);
        vtHelpCommid.setText("申请提现" + money1 + "元");
        View inflate = View.inflate(this, R.layout.dialog_bangding_phone, null);
        etBangdingPhone = (EditText) inflate.findViewById(R.id.et_bangding_phone);
        etBangdingYanzheng = (EditText) inflate.findViewById(R.id.et_bangding_yanzheng);
        btBangding = (Button) inflate.findViewById(R.id.bt_bangding);
        tvBangdingYanzheng = (TextView) inflate.findViewById(R.id.tv_bangding_yanzheng);
        btBangding.setOnClickListener(this);
        tvBangdingYanzheng.setOnClickListener(this);
        PhoneCodeUtiles.PhoneNum(etBangdingPhone, etBangdingYanzheng, tvTixianZhifubao);
        dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(inflate);
        View inflates = View.inflate(this, R.layout.dialog_tixian_chenggong, null);
        inflates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog2 != null && !dialog2.isShowing()) {
                    dialog2.dismiss();
                }
                jump();
            }
        });
        dialog2 = new Dialog(this, R.style.dialog);
        dialog2.setContentView(inflates);

    }

    @Override
    protected void initData() {
    }

    @Override
    public void onBackPressed() {
        jump();
    }

    private void jump() {
        if (Constant.TASK.equals(getIntent().getStringExtra(Constant.SKIP_MARK))) {
            LogUtils.e("进来了", "gjj");
            Intent data = new Intent(TiXianActivty.this, RenWuActivity.class);
            data.putExtra(Constant.WITHDRAWAL, withdrawal);
            startActivity(data);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.iv_back, R.id.vt_help_commid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.vt_help_commid:
                if (tvTixianName.getText().toString().trim().isEmpty() || tvTixianZhifubao.getText().toString().trim().isEmpty()
                        ) {
                    ToastUtil.showMessage("请填写完整信息");
                    return;
                }
                getMoney();
                break;
        }
    }

    public void getMoney() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("name", tvTixianName.getText().toString());
        map.put("account", tvTixianZhifubao.getText().toString());
        map.put("num", money1);
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        Observable tixian = RetroFactory.getInstance().getTIXIAN(map);
        tixian.compose(composeFunction).subscribe(new BaseObserver<VersionInfo>(this, pd) {
            @Override
            protected void onHandleSuccess(VersionInfo o) {
                if (o.getNeedBindPhone() == 1) {
                    if (dialog != null && !dialog.isShowing()) {
                        dialog.show();
                    }
                    return;
                }
                if (dialog2 != null && !dialog2.isShowing()) {
                    //提现成功
                    withdrawal = true;
                    dialog2.show();
                }

            }

        });
    }

    public void getSMS() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", etBangdingPhone.getText().toString());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
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

    public void getBangding() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", etBangdingPhone.getText().toString());
        map.put("code", etBangdingYanzheng.getText().toString());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        Observable logins = RetroFactory.getInstance().getBangd(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<MYBean>(this, pd) {
            @Override
            protected void onHandleSuccess(MYBean loginBean) {
                ToastUtil.showMessage("绑定成功");
                if (dialog != null && !dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bangding_yanzheng:
                if (etBangdingPhone.getText().toString().trim() != null) {
                    getSMS();
                } else {
                    ToastUtil.showMessage("请输入手机号");
                }
                break;
            case R.id.bt_bangding:
                if (TextUtils.isEmpty(etBangdingPhone.getText().toString()) || TextUtils.isEmpty(etBangdingYanzheng.getText().toString())) {
                    ToastUtil.showMessage("请输入完整信息");
                    return;
                }
                getBangding();
                break;
        }
    }

}
