package com.tianguo.zxz.activity.MyActivity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.LoginBean;
import com.tianguo.zxz.bean.MYBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.EditTextUtil;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.PhoneCodeUtiles;
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

public class SetPassWordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_old_password)
    EditText tvOldPassword;
    @BindView(R.id.tv_new_password)
    EditText tvNewPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.ll_ed_password)
    LinearLayout llEdPassword;
    @BindView(R.id.current_line)
    View currentLine;
    @BindView(R.id.et_again_password)
    EditText etAgainPassword;
    @BindView(R.id.tv_back)
    TextView tvBack;
    private int pwd;

    @BindView(R.id.tv_set_password)
    TextView tvSetPassword;
    private Dialog inputPhoneDialog;
    private TextView tvCancle;
    private TextView tvConfirm;
    private EditText etPhone;
    private LinearLayout llPhone;
    private TextView tvPhone;
    private TextView dialogTitle;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setpassword;
    }

    @Override
    protected void initView() {
        tvBack.setText(R.string.change_pd);
        createDialog();
        pwd = getIntent().getIntExtra("pwd", 0);
        if (pwd == 0) {
            currentLine.setVisibility(View.GONE);
            llEdPassword.setVisibility(View.GONE);
            tvSetPassword.setText(R.string.set_pd);
        } else {
            tvSetPassword.setText(R.string.afresh_set_pd);
        }
        EditTextUtil editTextUtil = new EditTextUtil();
        editTextUtil.set(tvNewPassword, getString(R.string.number_or_letter));
        editTextUtil.set(tvOldPassword, getString(R.string.number_or_letter));
        PhoneCodeUtiles.PhoneNum(etPhone);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.bt_login, R.id.btn_forget_pd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                String newPassword = tvNewPassword.getText().toString().trim();
                String againPassword = etAgainPassword.getText().toString().trim();
                if (pwd == 0) {
                    if (newPassword.isEmpty() ||
                            newPassword.length() < 6 ||
                            newPassword.length() > 20) {
                        ToastUtil.showMessage(getString(R.string.input_pd_limit));
                        return;
                    }
                    if (newPassword.equals(againPassword)) {
                        getLogin();
                    } else {
                        ToastUtil.showMessage(getString(R.string.pd_no_equal));
                    }
                } else {
                    if (newPassword.isEmpty() ||
                            newPassword.length() < 6 ||
                            newPassword.length() > 20) {
                        ToastUtil.showMessage(getString(R.string.input_pd_limit));
                        return;
                    }
                    String oldPassword = tvOldPassword.getText().toString().trim();
                    if (oldPassword.isEmpty() ||
                            oldPassword.length() < 6 ||
                            oldPassword.length() > 20) {
                        ToastUtil.showMessage(getString(R.string.input_pd_limit));
                        return;
                    }
                    if (newPassword.equals(againPassword)) {
                        getLogin();
                    } else {
                        ToastUtil.showMessage(getString(R.string.pd_no_equal));
                    }
                }
                break;
            case R.id.btn_forget_pd:
                if (inputPhoneDialog != null && !inputPhoneDialog.isShowing())
                    inputPhoneDialog.show();
                break;
        }

    }

    public void getLogin() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        if (tvOldPassword.getVisibility() == View.VISIBLE) {
            map.put("oldPassword", tvOldPassword.getText().toString());
        }
        LogUtils.e(tvNewPassword.getText().toString().trim());
        map.put("newPassword", tvNewPassword.getText().toString().trim());
        map.put("sso", SharedPreferencesUtil.getSSo(SetPassWordActivity.this));
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getModifyPassWordInfo(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<LoginBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LoginBean loginBean) {
                ToastUtil.showMessage(getString(R.string.change_pd_success));
                setResult(102);
                finish();
            }
        });

    }

    private void createDialog() {
        View inflate = View.inflate(this, R.layout.input_phone_dialog, null);
        inputPhoneDialog = new Dialog(this, R.style.dialog);
        inputPhoneDialog.setContentView(inflate);
        tvCancle = (TextView) inflate.findViewById(R.id.tv_cancle);
        tvConfirm = (TextView) inflate.findViewById(R.id.tv_confirm);
        etPhone = (EditText) inflate.findViewById(R.id.et_phone);
        llPhone = (LinearLayout) inflate.findViewById(R.id.ll_confirm_phone);
        tvPhone = (TextView) inflate.findViewById(R.id.tv_phone);
        dialogTitle = (TextView) inflate.findViewById(R.id.tv_title);
        tvCancle.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle:
                if (inputPhoneDialog != null && inputPhoneDialog.isShowing())
                    inputPhoneDialog.dismiss();
                if (llPhone.getVisibility() == View.VISIBLE) {
                    llPhone.setVisibility(View.GONE);
                    etPhone.setVisibility(View.VISIBLE);
                    dialogTitle.setText(getString(R.string.msg_login));
                }
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                    ToastUtil.showMessage(getString(R.string.input_phone));
                    return;
                } else if (isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtil.showMessage(getString(R.string.input_correct_phone));
                    return;
                } else {
                    if (llPhone.getVisibility() == View.GONE) {
                        dialogTitle.setText(R.string.confirm_p);
                        llPhone.setVisibility(View.VISIBLE);
                        etPhone.setVisibility(View.GONE);
                        tvPhone.setText("+86 " + etPhone.getText().toString().trim());
                    } else {
                        //发送验证码
                        getSMS();
                        Intent intent = new Intent(this, VerificationCodeActivity.class);
                        startActivity(intent);
                        inputPhoneDialog.dismiss();
                    }
                }

                break;
        }
    }

    public void getSMS() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", etPhone.getText().toString());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getSMS(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<MYBean>(this, pd) {
            @Override
            protected void onHandleSuccess(MYBean loginBean) {
                ToastUtil.showMessage("验证码已发送至您的手机请注意查收");
//                timer.setTv(tvLginSendSms);
//                timer.cancel();
//                timer.start();
            }
        });

    }

    /**
     * 判断手机格式是否正确
     *
     * @param mobiles
     * @return 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
    public static boolean isMobileNO(String mobiles) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (inputPhoneDialog != null&&inputPhoneDialog.isShowing())
            inputPhoneDialog.dismiss();
        inputPhoneDialog = null;

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
