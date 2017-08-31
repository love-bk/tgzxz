package com.tianguo.zxz.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.GongLueAdapt;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.FanKuiBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/5/17.
 */

public class GongLueFragment extends BaseFragment {
    @BindView(R.id.rc_gonglue_list)
    RecyclerView rcGonglueList;
    private GongLueAdapt gongLueAdapt;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(main);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcGonglueList.setLayoutManager(layoutManager);
        getGongLue();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three_student;
    }
    public void getGongLue() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("devid",SharedPreferencesUtil.getOnlyID(main));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(main));
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        Observable logins = RetroFactory.getInstance().getFanKuiList(map);
        logins.compose(main.composeFunction).subscribe(new BaseObserver<FanKuiBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(FanKuiBean loginBean) {
                gongLueAdapt = new GongLueAdapt(loginBean.getGuideList(),main);
                rcGonglueList.setAdapter(gongLueAdapt);
            }
        });

    }


}
