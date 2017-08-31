package com.tianguo.zxz.activity.MyActivity;

import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class VerificationCodeActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tvBack;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_verification_code;
    }

    @Override
    protected void initView() {
        tvBack.setText("找回密码");
    }

    @Override
    protected void initData() {

    }



    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
