package com.tianguo.zxz.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.ApprenticeAdapter;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.DiscipleBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApprenticeFragment extends BaseFragment {

    @BindView(R.id.rv_income_detail)
    RecyclerView rvIncomeDetail;
    @BindView(R.id.tv_disciple_num)
    TextView tvDiscipleNum;
    @BindView(R.id.tv_tribute_num)
    TextView tvTributeNum;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_no)
    TextView tvNo;
    private ApprenticeAdapter adapter;
    private List<DiscipleBean.FollowBean> datas = new ArrayList<>();
    private int page = 1;
    private int flag = -1;

    public ApprenticeFragment() {
        // Required empty public constructor
    }

    public static ApprenticeFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(Constant.PAYMENT_DETAIL, position);
        ApprenticeFragment fragment = new ApprenticeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        flag = getArguments().getInt(Constant.PAYMENT_DETAIL);
        adapter = new ApprenticeAdapter(datas, main);
        rvIncomeDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isSlideToBottom(recyclerView)) {
                    getIncomeDetail();
                }
            }
        });
        rvIncomeDetail.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(main);
        rvIncomeDetail.setLayoutManager(linearLayoutManager);
        getIncomeDetail();
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null)
            return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && datas.size() == 0 && adapter != null) {//可见的时候加载数据
            getIncomeDetail();
        }
    }


    @Override
    public void onDestroy() {
        if (null != datas || datas.size() != 0) {
            datas.clear();
            datas = null;
        }

        super.onDestroy();
    }

    private void getIncomeDetail() {
        main.setLoadingFlag(false);
        final HashMap<String, Object> map = new HashMap<>();
        map.put("type", flag);
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        Observable observable = RetroFactory.getInstance().getFollow(map);
        observable.compose(main.composeFunction).subscribe(new BaseObserver<DiscipleBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(DiscipleBean bean) {
                if (bean != null &&bean.getFollow()!=null&&bean.getFollow().size()>0){
                    List<DiscipleBean.FollowBean> follow = bean.getFollow();
                    tvDiscipleNum.setText(follow.size()+"个");
                    long tributeNum = 0;
                    for (DiscipleBean.FollowBean followBean : follow) {
                        tributeNum += followBean.getMoney();
                    }
                    tvTributeNum.setText(((double) tributeNum)/100.0+"元");
                    if (adapter != null)
                        adapter.addDatas(follow);
                    llContent.setVisibility(View.VISIBLE);
                }else {
                    setTvNoText();
                    tvNo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
                setTvNoText();
                tvNo.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setTvNoText() {
        switch (flag){
            case 1:
                tvNo.setText("暂无邀请徒弟");
                break;
            case 2:
                tvNo.setText("暂无徒孙");
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apprentice;
    }

}
