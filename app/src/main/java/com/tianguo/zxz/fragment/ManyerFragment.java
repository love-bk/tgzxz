package com.tianguo.zxz.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.VpMainThreeAdpaterd;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.fragment.threefragment.MyStudentFrgament;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lx on 2017/5/17.
 */

public class ManyerFragment extends BaseFragment {
    ViewPager vpMainThree;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;
    private TabLayout tbMainThree;
    private VpMainThreeAdpaterd vpMainThreeAdpaterd;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tbMainThree = (TabLayout) view.findViewById(R.id.tb_main_three);
        vpMainThree = (ViewPager) view.findViewById(R.id.vp_main_three);
        YaoFragment yaoFragment = new YaoFragment();
        GongLueFragment gongLueFragment = new GongLueFragment();
        MyStudentFrgament myStudentFrgament = new MyStudentFrgament();
        list_title = new ArrayList<>();
        list_fragment = new ArrayList<>();
        list_title.add("邀请收徒");
        list_title.add("邀请排行");
        list_title.add("收徒攻略");
        list_fragment.add(yaoFragment);
        list_fragment.add(myStudentFrgament);
        list_fragment.add(gongLueFragment);
        vpMainThreeAdpaterd = new VpMainThreeAdpaterd(main.getSupportFragmentManager(), list_fragment, list_title);
        //设置TabLayout的模式
        vpMainThree.setAdapter(vpMainThreeAdpaterd);
        tbMainThree.setupWithViewPager(vpMainThree);
        tbMainThree.setTabMode(TabLayout.MODE_FIXED);
//        tbMainThree.getTabAt(1).select();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three_main;
    }

}
