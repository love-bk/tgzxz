package com.tianguo.zxz.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.IncomeDetailAdapter;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.IncomeDetailBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
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
public class PaymentDetailFragment extends BaseFragment {

    @BindView(R.id.rv_income_detail)
    RecyclerView rvIncomeDetail;
    private IncomeDetailAdapter incomeDetailAdapter;
    private List<IncomeDetailBean.RecordBean> datas = new ArrayList<>();
    private int page = 1;
    private int flag = -1;

    public PaymentDetailFragment() {
        // Required empty public constructor
    }

    public static PaymentDetailFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(Constant.PAYMENT_DETAIL, position);
        PaymentDetailFragment fragment = new PaymentDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        flag = getArguments().getInt(Constant.PAYMENT_DETAIL);
        incomeDetailAdapter = new IncomeDetailAdapter(datas,flag);
        rvIncomeDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isSlideToBottom(recyclerView)){
                    getIncomeDetail();
                }
            }
        });
        rvIncomeDetail.setAdapter(incomeDetailAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(main);
        rvIncomeDetail.setLayoutManager(linearLayoutManager);
        if (flag == 1){
            getIncomeDetail();
        }
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
        if (isVisibleToUser && datas.size() == 0&&incomeDetailAdapter!=null) {//可见的时候加载数据
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
        map.put("p", page);
        map.put("n", 10);
        map.put("t", flag);
        map.put("v", UpdateAppUtil.getAPPLocalVersion(main));
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        Observable observable = RetroFactory.getInstance().getIncomeDetail(map);
        observable.compose(main.composeFunction).subscribe(new BaseObserver<IncomeDetailBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(IncomeDetailBean bean) {
                if (bean != null && bean.getRecord() != null && bean.getRecord().size() > 0) {
                    page++;
                    incomeDetailAdapter.addDatas(bean.getRecord());
                }
            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment_detail;
    }

}
