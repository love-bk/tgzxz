package com.tianguo.zxz.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.GetMonerAdapter;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.MyGGbean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/6/16.
 */

public class GetMoneyActivity extends BaseActivity {
    @BindView(R.id.tv_back)
    TextView tvTitle;
    @BindView(R.id.lv_yaoqing_list)
    ListView lvYaoqingList;
@BindView(R.id.tv_my_tishi)
    TextView tvMytishi;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_getmoney;
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.withdrawal_record);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("devid",SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getMoneyList(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<MyGGbean>(this,pd) {
            @SuppressLint("WrongConstant")
            @Override
            protected void onHandleSuccess(final MyGGbean bean) {
                if (bean.getArray()!=null&&bean.getArray().size()>0){
                    lvYaoqingList.setVisibility(View.VISIBLE);
                    tvMytishi.setVisibility(View.GONE);
                    lvYaoqingList.setAdapter(new GetMonerAdapter(GetMoneyActivity.this,bean.getArray()));
                }else {
                    tvMytishi.setVisibility(View.VISIBLE);
                    lvYaoqingList.setVisibility(View.GONE);


                }

            }
            @SuppressLint("WrongConstant")
            @Override
            public void onHandleError(int code, String message) {
            }
        });

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
