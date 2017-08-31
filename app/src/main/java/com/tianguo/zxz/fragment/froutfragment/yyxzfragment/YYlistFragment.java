package com.tianguo.zxz.fragment.froutfragment.yyxzfragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseFragment;

import butterknife.BindView;
/**
 * Created by lx on 2017/8/22.
 */

public class YYlistFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
@BindView(R.id.vp_yy)
    ViewPager vpyy;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tabLayout.addTab(tabLayout.newTab().setText("可参与"));
        tabLayout.addTab(tabLayout.newTab().setText("进行中"));
        tabLayout.addTab(tabLayout.newTab().setText("已结束"));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.yyxz_list;
    }

}
