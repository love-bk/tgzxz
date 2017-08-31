package com.tianguo.zxz.activity.MyActivity;

import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.HelpAdpater;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.HelpListsBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/5/16.
 */

public class HelpAndFanActivity extends BaseActivity {
    @BindView(R.id.lv_help_list)
    ListView lvHelpList;
    @BindView(R.id.tv_back)
    TextView tvTitle;
    @BindView(R.id.tv_help_fankui)
    TextView tvHelpFankui;
    private HelpAdpater helpAdpater;
HashMap<String,String> map = new HashMap<>();
    @Override
    protected int getContentViewId() {
        return R.layout.activity_helap_fan;
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.help);
        getTrue();

    }

    @Override
    protected void initData() {

    }

    public void getTrue() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("devid",SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getHelpList(map);

        logins.compose(composeFunction).subscribe(new BaseObserver<HelpListsBean>(this, pd) {
            @Override
            protected void onHandleSuccess(HelpListsBean loginBean) {
                helpAdpater = new HelpAdpater(HelpAndFanActivity.this, loginBean.getHelpList());
                lvHelpList.setAdapter(helpAdpater);
            }
        });

    }

    @OnClick(R.id.iv_back)
    public void onViewClcked() {
        finish();
    }


    @OnClick(R.id.tv_help_fankui)
    public void onViewClicked() {
        map.put("tab","反馈");
        MobclickAgent.onEvent(HelpAndFanActivity.this, "click_Mywallet", map);
        startActivity(new Intent(HelpAndFanActivity.this,HelpActivy.class));
    }
}
