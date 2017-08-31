package com.tianguo.zxz.activity.MyActivity;

import android.view.View;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;

/**
 * Created by lx on 2017/6/16.
 */

public  class NoPassManeryActivity  extends BaseActivity{
    @Override
    protected int getContentViewId() {
        return R.layout.activity_no_pass;
    }

    @Override
    protected void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {

    }
}
