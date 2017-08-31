package com.tianguo.zxz.activity.MyActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.fragment.froutfragment.yyxzfragment.YYlistFragment;
import com.tianguo.zxz.uctils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class YYXZActivity extends BaseActivity {

    @BindView(R.id.fl_yy_frgment)
    FrameLayout flYyFrgment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_yyxz;
    }

    @Override
    protected void initView() {
        @SuppressLint("WrongConstant") TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String SimSerialNumber = tm.getSimSerialNumber();
        if (TextUtils.isEmpty(SimSerialNumber)) {
            ToastUtil.showMessage("手机没有sim卡将无法获取奖励 ");
//            return;
        }
        YYlistFragment yYlistFragment = new YYlistFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_yy_frgment,yYlistFragment).commit();

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_back, R.id.rb_all, R.id.rb_gemo, R.id.rb_app})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.rb_all:
                break;
            case R.id.rb_gemo:
                break;
            case R.id.rb_app:
                break;
        }
    }
}
