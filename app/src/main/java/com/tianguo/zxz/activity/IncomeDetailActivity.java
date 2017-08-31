package com.tianguo.zxz.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.PayViewPageAdapter;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.fragment.PaymentDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class IncomeDetailActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tvTitle;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_gold)
    TextView tvGold;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_income_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.wallet_detail);
        tvGold.setText(getIntent().getStringExtra("gold")+"金币");
        tvTotalMoney.setText(getIntent().getStringExtra("balance")+"元现金");
        List<PaymentDetailFragment> fragments = new ArrayList<>();
        PaymentDetailFragment paymentDetailGoldFragment = PaymentDetailFragment.newInstance(1);
        PaymentDetailFragment paymentDetailMoneyFragment = PaymentDetailFragment.newInstance(0);
        fragments.add(paymentDetailGoldFragment);
        fragments.add(paymentDetailMoneyFragment);
        PayViewPageAdapter adapter = new PayViewPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_back,R.id.tv_withdrawal_record,R.id.tv_why})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_withdrawal_record:
                startActivity(new Intent(IncomeDetailActivity.this, GetMoneyActivity.class));
                break;
            case R.id.tv_why:
                startActivity(new Intent(IncomeDetailActivity.this, STBenefitActivity.class));
                break;
        }
    }

}
