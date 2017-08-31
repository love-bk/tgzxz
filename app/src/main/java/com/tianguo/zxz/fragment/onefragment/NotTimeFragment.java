package com.tianguo.zxz.fragment.onefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.ShareActivity;
import com.tianguo.zxz.adapter.GridAdapter;
import com.tianguo.zxz.adapter.SQlistAdpater;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.SoDaoBean;
import com.tianguo.zxz.bean.SoDaoBean.SearchListBean;
import com.tianguo.zxz.dao.SQlishiUtils;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/5/19.
 */

public class NotTimeFragment extends BaseFragment {
    @BindView(R.id.gv_news_lishi)
    GridView gridView;
    @BindView(R.id.gv_lishi_icon)
    GridView gvlishiicon;
    @BindView(R.id.tv_clner)
    TextView clenar;
    private GridAdapter gridAdapter;
    private List<SearchListBean> searchList;
    private SQlishiUtils sp;
    private SQlistAdpater simpleCursorAdapter;
    private ShareActivity main;
HashMap<String,String> map = new HashMap<>();
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser){
            map.put("click_search","最常访问");
            MobclickAgent.onEvent(main, "search", map);
            if (null!=sp){
                simpleCursorAdapter = sp.queryData("");
                gridView.setAdapter(simpleCursorAdapter);
            }

        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        main = (ShareActivity) getActivity();
        sp = new SQlishiUtils(main);
        simpleCursorAdapter = sp.queryData("");
        gridView.setAdapter(simpleCursorAdapter);
        main.setOnAddSoListenr(new ShareActivity.OnAddSOlishi() {
            @Override
            public void addsolistner() {
                simpleCursorAdapter = sp.queryData("");
                gridView.setAdapter(simpleCursorAdapter);
            }
        });
        getTrue();
        clenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.deleteData();
                simpleCursorAdapter = sp.queryData("");
                gridView.setAdapter(simpleCursorAdapter);
            }
        });
//        vpSoLishi.setOnPageChangeListener(new BannerListener());
//        mLinearLayout.getChildAt(newPosition).setEnabled(true);
    }

    public void getTrue() {
        main.setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        map.put("devid",SharedPreferencesUtil.getOnlyID(main));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(main));
        Observable logins = RetroFactory.getInstance().getSoDao(map);
        logins.compose(main.composeFunction).subscribe(new BaseObserver<SoDaoBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(SoDaoBean loginBean) {
                if ((null != searchList)&&(searchList.size() > 0) ){
                    searchList.clear();
                }
                searchList = loginBean.getSearchList();

                if (null == gridAdapter){
                    gridAdapter = new GridAdapter(main, searchList);
                }
                gvlishiicon.setAdapter(gridAdapter);

            }
        });

    }

    @Override
    public void onResume() {
        LogUtils.e("onResume");

        super.onResume();
    }

    @Override
    public void onDestroy() {
        LogUtils.e("onDestroy");

        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtils.e("hidden");

        super.onHiddenChanged(hidden);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.e("onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        LogUtils.e("onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     *      * 初始化数据
     *      
     * @param searchList
     */
    private void initData(List<SearchListBean> searchList) {
//        mlist = new ArrayList<MyGridView>();
        if(searchList==null){
            return;
        }
//        for (int i = 0; i < ; i++) {
// 设置广告图
//            MyGridView gridView = new MyGridView(main);
//            gridView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//            gridView.setNumColumns(5);
//            gridView.setAdapter(new GridAdapter(main));
//            mlist.add(gridView);
// 设置圆圈点
//            view = new View(main);
//            params = new LayoutParams(10, 10);
//            params.leftMargin = 10;
//            view.setBackgroundResource(R.drawable.selector_banner_dian);
//            view.setLayoutParams(params);
//            view.setEnabled(false);
//            mLinearLayout.addView(view);
//        }
//        mAdapter = new BannerAdapter(mlist);
//        vpSoLishi.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frgament_main_lishi;
    }
//
//    //实现VierPager监听器接口
//    class BannerListener implements ViewPager.OnPageChangeListener {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            mLinearLayout.getChildAt(position).setEnabled(true);
//            mLinearLayout.getChildAt(newPosition).setEnabled(false);
//
//            newPosition = position;
//// 更新标志位
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    }
}
