package com.tianguo.zxz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tianguo.zxz.Flat;
import com.tianguo.zxz.fragment.froutfragment.yyxzfragment.YYXZFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2017/6/26.
 */
public class YYXZViewPagerAdapter extends FragmentPagerAdapter{

    private  List<YYXZFragment> fragments = new ArrayList<>();

    public YYXZViewPagerAdapter(FragmentManager fm, List<YYXZFragment> fragmentList) {
        super(fm);
        this.fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Flat.YYXZTabTitle[position];
    }
}
