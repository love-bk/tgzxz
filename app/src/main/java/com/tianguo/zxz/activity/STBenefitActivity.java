package com.tianguo.zxz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.Nowactivity;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.uctils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class STBenefitActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_subtitle1)
    TextView tvSubtitle1;
    @BindView(R.id.tv_subtitle2)
    TextView tvSubtitle2;
    @BindView(R.id.tv_subtitle3)
    TextView tvSubtitle3;
    @BindView(R.id.title)
    TextView title;
    private String colorStr = "#FD0098";
    private Intent intentH5;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_stbenefit;
    }

    @Override
    protected void initView() {
        title.setText("\u3000\u3000"+title.getText().toString());
        tvBack.setText("收徒的好处和玩法");
        initTextColor(tvSubtitle1, "得", "的", "+");
        initTextColor(tvSubtitle2, "献", "，1个", null);
        initTextColor(tvSubtitle3, "励", "，", null);
        intentH5 = new Intent(this, Nowactivity.class);
    }

    @Override
    protected void initData() {

    }

    private void initTextColor(TextView textView, String startStr, String endStr, String middleStr) {
        String title1 = textView.getText().toString();
        SpannableString ss = new SpannableString(title1);
        if (middleStr != null) {
            if (title1 != null && title1.contains(startStr) && title1.contains(endStr) && title1.contains(middleStr)) {
                int start = title1.indexOf(startStr) + 1;
                int middle = title1.indexOf(middleStr);
                int end = title1.indexOf(endStr);
                //设置字体前景色
                ss.setSpan(new ForegroundColorSpan(Color.parseColor(colorStr)), start, middle, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(Color.parseColor(colorStr)), middle + 1, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {
            if (title1 != null && title1.contains(startStr) && title1.contains(endStr)) {
                int start = title1.indexOf(startStr) + 1;
                int end = title1.indexOf(endStr);
                //设置字体前景色
                ss.setSpan(new ForegroundColorSpan(Color.parseColor(colorStr)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(ss);

    }


    @OnClick({R.id.tv_look_rule1, R.id.tv_look_rule2, R.id.tv_look_rule3, R.id.tv_look_rule4})
    public void onViewClicked(View view) {
        intentH5.putExtra(Constant.ST_TYPE, Constant.SRMX);
        switch (view.getId()) {
            case R.id.tv_look_rule1:
            case R.id.tv_look_rule2:
                intentH5.putExtra(Constant.URL,Constant.INVITE_URL);
                startActivity(intentH5);
                break;
            case R.id.tv_look_rule3:
                //暑期活动的跳转
                intentH5.putExtra(Constant.URL,Constant.ACTIVITY_URL);
                startActivity(intentH5);
                break;
            case R.id.tv_look_rule4:
                Intent intentInvite = new Intent(STBenefitActivity.this, MainActivity.class);
                intentInvite.putExtra("type", 2);
                startActivity(intentInvite);
                finish();
                break;
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
