package com.tianguo.zxz.fragment.froutfragment.yyxzfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.tianguo.zxz.Flat;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.YYDetailActivity;
import com.tianguo.zxz.adapter.YYXZItemAdapter;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.YYListBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.DownListnerUtils;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class YYXZFragment extends BaseFragment implements YYXZItemAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.yyxz_recyclerView)
    RecyclerView yyxzRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<YYListBean> list = new ArrayList<>();
    private YYXZItemAdapter yyxzItemAdapter;
    private int flag = -1;

    public static YYXZFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(Flat.YYXZ_FLAG, position);
        YYXZFragment fragment = new YYXZFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && list.size() == 0 && yyxzItemAdapter != null) {//可见的时候加载数据
            getYYList();
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        yyxzItemAdapter = new YYXZItemAdapter(list, main);
        yyxzItemAdapter.setOnItemClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(main, LinearLayout.VERTICAL, false);
        yyxzRecyclerView.setLayoutManager(linearLayoutManager);
        yyxzRecyclerView.setAdapter(yyxzItemAdapter);
        flag = getArguments().getInt(Flat.YYXZ_FLAG);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.tabIndicatorColor, R.color.tabIndicatorColor, R.color.tabIndicatorColor);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yyxz;
    }

    @Override
    public void onDestroy() {
        if (null != list || list.size() != 0) {
            list.clear();
            list = null;
        }
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, YYListBean yyListBean, int position) {
        Intent intent = new Intent(main, YYDetailActivity.class);
        LogUtils.e(yyListBean.getI()+"sssssss");
        intent.putExtra("isdownload",yyListBean.getIs_download());
        intent.putExtra("id",yyListBean.getI());
        intent.putExtra("packname",yyListBean.getP());
        intent.putExtra("status",yyListBean.getStatus());
        intent.putExtra("position",position);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 204) {
            if (data != null) {
                int itme = data.getIntExtra("itme", -1);
                if (itme != -1) {
                    if (list != null && yyxzItemAdapter != null) {
                        list.remove(itme);
                        yyxzItemAdapter.notifyDataSetChanged();

                    }

                }
            }
        }
    }

    //下拉刷新加载数据
    @Override
    public void onRefresh() {
        getYYList();
    }

    public void getYYList() {
        if (null != list) {
            list.clear();
        }
        main.setLoadingFlag(false);
        LogUtils.e("sssssss");
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", flag + 1);//1是应用,2是游戏
        map.put("devid", SharedPreferencesUtil.getOnlyID(main));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(main));
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        Observable observable = RetroFactory.getInstance().getYYList(map);
        observable.compose(main.composeFunction).subscribe(new BaseObserver<YYListBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(final YYListBean newsListBean) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                }
                if (null == newsListBean) {
                    return;
                }
                LogUtils.e(newsListBean.getP());
                if (!DownListnerUtils.checkApkExist(main, newsListBean.getP())&&newsListBean.getIs_download()!=0) {
                    list.add(newsListBean);
                }else if(!DownListnerUtils.checkApkExist(main, newsListBean.getP())&&newsListBean.getIs_download()==0){
                    list.add(newsListBean);
                }else if (DownListnerUtils.checkApkExist(main, newsListBean.getP())&&newsListBean.getIs_download()!=0){
                    list.add(newsListBean);

                }
                    yyxzItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onHandleError(int code, String message) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {

                }
            }
        });
    }

}
