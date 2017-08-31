package com.tianguo.zxz.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.SoWebActivity;
import com.tianguo.zxz.adapter.VpMainThreeAdpaterd;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.dao.SQlishiUtils;
import com.tianguo.zxz.fragment.onefragment.GaoXiaoFragment;
import com.tianguo.zxz.fragment.onefragment.NotTimeFragment;
import com.tianguo.zxz.fragment.onefragment.ReMenFragment;
import com.tianguo.zxz.uctils.GetAwardUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tianguo.zxz.Flat.ShenMaUrl;

/**
 * Created by lx on 2017/5/2.
 */

public class ShareActivity extends BaseActivity {
    @BindView(R.id.ed_news_so)
    EditText edNewsSo;
    @BindView(R.id.tv_news_ture)
    TextView tvNewsTure;
    @BindView(R.id.ll_news_three)
    LinearLayout llNewsThree;
    HashMap<String, String> map = new HashMap<>();
    @BindView(R.id.tb_so_teile)
    TabLayout tbSoTeile;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_gift)
    ImageView ivGift;
    private VpMainThreeAdpaterd vpMainThreeAdpaterd;
    private List<Fragment> list_fragment;
    private List<String> list_title;
    private ReMenFragment onefragment;
    private GaoXiaoFragment twofrgamnet;
    private NotTimeFragment threefragment;
    private ViewPager vpSoTeile;
    private Intent intent;
    private SQlishiUtils sq;
    private Dialog dialog;
private  int reSou ;
    private TextView promptTv;
    private TextView tvTitle;
    private MyApplictation application;

    @OnClick({R.id.tv_news_ture,R.id.iv_back,R.id.iv_gift})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_gift:
                if (dialog == null) {
                    dialog = new Dialog(ShareActivity.this, R.style.dialog);
                    View dialogLayout = View.inflate(this, R.layout.sign_prompt_box, null);
                    dialogLayout.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    promptTv = (TextView) dialogLayout.findViewById(R.id.tv_content);
                    tvTitle = (TextView) dialogLayout.findViewById(R.id.tv_sign);
                    promptTv.setText("每个奖励时段，使用搜索看搜索内容，\n有100%几率获取惊喜礼包");
                    tvTitle.setText("红包提示");
                    dialog.setContentView(dialogLayout);
                }

                dialog.show();
                break;
            case R.id.tv_news_ture:
                if (edNewsSo.getText().toString().isEmpty()) {
                    ToastUtil.showMessage("请输入搜索内容");
                } else {
                    if (!sq.hasData(edNewsSo.getText().toString().trim())) {
                        sq.insertData(edNewsSo.getText().toString().trim());
                        sOlishi.addsolistner();
                    }
                    if (SharedPreferencesUtil.getReSou(ShareActivity.this) != 10) {
                        reSou++;
                        Random random = new Random();
                        SharedPreferencesUtil.saveReSou(ShareActivity.this, reSou);
                        int i = random.nextInt(5);
                        if (i == 3 || SharedPreferencesUtil.getSouSuo(ShareActivity.this) == 5) {
                            GetAwardUtils.getAward(2, System.currentTimeMillis() + "", ShareActivity.this, new GetAwardUtils.GetAwardListner() {
                                @Override
                                public void getAward(double m) {
                                    reSou=0;
                                    SharedPreferencesUtil.saveSouSuo(ShareActivity.this, 0);
                                }
                                @Override
                                public void nullAward() {
                                    SharedPreferencesUtil.saveSouSuo(ShareActivity.this, 10);

                                }
                            });
                        }


                    }
                    map.put("click_search", "搜索后");
                    MobclickAgent.onEvent(ShareActivity.this, "search", map);
                    intent.putExtra("url",ShenMaUrl+edNewsSo.getText().toString());
                    edNewsSo.setText("");
                    startActivity(intent);
                }
                break;
        }

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main_so;
    }

    @Override
    protected void onDestroy() {
        try{


            application.removeActivity(this);
        }catch (Exception e){

        }
        super.onDestroy();
    }

    @Override
    protected void initView() {
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        reSou = SharedPreferencesUtil.getSouSuo(this);
        sq = new SQlishiUtils(this);
        intent = new Intent(this, SoWebActivity.class);
        vpSoTeile = (ViewPager) findViewById(R.id.vp_so_teile);
        list_fragment = new ArrayList<>();
        list_title = new ArrayList<>();
        onefragment = new ReMenFragment();
        twofrgamnet = new GaoXiaoFragment();
        threefragment = new NotTimeFragment();
        list_fragment.add(onefragment);
        list_fragment.add(threefragment);
        list_fragment.add(twofrgamnet);
        list_title.add("热门搜索");
        list_title.add("最常访问");
        list_title.add("美女直播");
        vpMainThreeAdpaterd = new VpMainThreeAdpaterd(getSupportFragmentManager(), list_fragment, list_title);
        //设置TabLayout的模式
        vpSoTeile.setAdapter(vpMainThreeAdpaterd);
        tbSoTeile.setupWithViewPager(vpSoTeile);
        tbSoTeile.setTabMode(TabLayout.MODE_FIXED);
        edNewsSo.setOnKeyListener(new View.OnKeyListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    if (!sq.hasData(edNewsSo.getText().toString().trim())) {
                        sq.insertData(edNewsSo.getText().toString().trim());
                        sOlishi.addsolistner();
                    }
                    if (edNewsSo.getText().toString().isEmpty()) {
                        ToastUtil.showMessage("请输入搜索内容");
                    } else {
                        map.put("click_search", "搜索后");
                        MobclickAgent.onEvent(ShareActivity.this, "search", map);
                        intent.putExtra("url",ShenMaUrl+edNewsSo.getText().toString());
                        edNewsSo.setText("");
                        startActivity(intent);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        if (twofrgamnet.isBack()){
            finish();
        }
    }
    OnAddSOlishi sOlishi;
    public  interface  OnAddSOlishi{
        void  addsolistner();
    }
    public  void  setOnAddSoListenr(OnAddSOlishi soListenr){
        this.sOlishi=soListenr;
    }
}
