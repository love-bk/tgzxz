package com.tianguo.zxz.activity;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.ApprenticeViewPageAdapter;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.uctils.Constant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ApprenticeActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_apprentice;
    }

    @Override
    protected void initView() {

        List<ApprenticeFragment> fragments = new ArrayList<>();
        ApprenticeFragment discipleFragment = ApprenticeFragment.newInstance(1);
        ApprenticeFragment ddFragment = ApprenticeFragment.newInstance(2);
        fragments.add(discipleFragment);
        fragments.add(ddFragment);
        ApprenticeViewPageAdapter adapter = new ApprenticeViewPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
        switch (getIntent().getIntExtra(Constant.DISCIPLE, 0)) {
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;
        }
        //改变tablayout指示器的宽度
        tablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tablayout,40,40);
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

    /**
     * 设置tablayout下划线
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    private void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        try{
            Class<?> tabLayout = tabs.getClass();
            Field tabStrip = null;
            try {
                tabStrip = tabLayout.getDeclaredField("mTabStrip");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            tabStrip.setAccessible(true);
            LinearLayout llTab = null;
            try {
                llTab = (LinearLayout) tabStrip.get(tabs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        }catch (Exception e){

        }

    }
}
