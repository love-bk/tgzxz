package com.tianguo.zxz.activity;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.LoginBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
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
 * Created by lx on 2017/5/22.
 */

public class PhoneLoginYaoActivity extends BaseActivity {
    @BindView(R.id.iv_yanzheng_tiaoguo)
    TextView ivYanzhengTiaoguo;
    @BindView(R.id.et_yanzheng_yaoqingma)
    EditText etYanzhengYaoqingma;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private MyApplictation application;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_phone_yaoqing;
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
        LogUtils.e("PhoneLoginYaoActivity进来了","gjj");
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        PhoneCodeUtiles.PhoneNum(etYanzhengYaoqingma);
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.tv_login)
    public void onicked() {
        if (etYanzhengYaoqingma.toString().trim().isEmpty()){
            ToastUtil.showMessage("请输入邀请码");
            return;
        }
        getYaoQing();
    }

    public void getYaoQing() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("invitor", etYanzhengYaoqingma.getText().toString().trim());
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("devid",SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getYaoQingnum(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<LoginBean>(this, pd) {
            @Override
            protected void onHandleSuccess(LoginBean loginBean) {
                Intent intent = new Intent(PhoneLoginYaoActivity.this, MainActivity.class);
                intent.putExtra("isBangding",true);
                startActivity(intent);
                ToastUtil.showMessage("提交邀请码成功");
                hideSystemKeyBoard(etYanzhengYaoqingma);
                finish();
            }
        });

    }


    @OnClick(R.id.iv_yanzheng_tiaoguo)
    public void onViewClicked() {
        Intent intent = new Intent(PhoneLoginYaoActivity.this, MainActivity.class);
        startActivity(intent);
if (etYanzhengYaoqingma!=null){
    hideSystemKeyBoard(etYanzhengYaoqingma);
}
        finish();
    }
}
