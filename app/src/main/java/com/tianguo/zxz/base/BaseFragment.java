package com.tianguo.zxz.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * @author WalterWang
 *         Created at 17/3/2 上午9:38
 */
public abstract class BaseFragment extends Fragment {
    protected BaseActivity main;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取fragment布局文件ID
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected BaseActivity getMain() {
        return main;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.main = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                initView(view, savedInstanceState);
            }
        }, 200);

        return view;
    }


}
