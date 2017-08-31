package com.tianguo.zxz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tianguo.zxz.Flat;
import com.tianguo.zxz.activity.ApprenticeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/8/19.
 */

public class ApprenticeViewPageAdapter extends FragmentPagerAdapter{

        private List<ApprenticeFragment> fragments = new ArrayList<>();

        public ApprenticeViewPageAdapter(FragmentManager fm, List<ApprenticeFragment> fragmentList) {
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
            return Flat.ApprenticeTabTitle[position];
        }
}
