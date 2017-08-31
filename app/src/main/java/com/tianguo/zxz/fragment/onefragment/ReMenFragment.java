package com.tianguo.zxz.fragment.onefragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.SoWebActivity;
import com.tianguo.zxz.activity.ShareActivity;
import com.tianguo.zxz.adapter.HotWordAdapter;
import com.tianguo.zxz.adapter.SQlistAdpater;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.ReCiBean;
import com.tianguo.zxz.dao.SQlishiUtils;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

import static com.tianguo.zxz.Flat.ShenMaUrl;

/**
 * Created by lx on 2017/5/19.
 */

public class ReMenFragment extends BaseFragment {
    @BindView(R.id.gv_news_lishi)
    GridView gridView;
    @BindView(R.id.gv_news_so)
    GridView gvNewsSo;
    @BindView(R.id.tv_clner)
    TextView clenar;
    @BindView(R.id.tv_news_so_re)
    TextView tvNewsSoRe;
    private HashMap<String, String> map;
    List<ReCiBean.ListBean> data = new ArrayList<>();
    private SQlishiUtils sp;
    private SQlistAdpater simpleCursorAdapter;
//    private ShareActivity main;
    private Intent intent;
    private HotWordAdapter hotWordAdapter;
    private int clickedPostion = -1;
    private boolean isHot;
    private String title;
    private SQlishiUtils sq;
    private ShareActivity shareActivity;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        if (getActivity() instanceof ShareActivity){
            shareActivity = (ShareActivity) getActivity();
        }
        sq = new SQlishiUtils(main);
        /**
         * 热词搜索
         */
        intent = new Intent(main, SoWebActivity.class);
        map = new HashMap<>();
        hotWordAdapter = new HotWordAdapter(main, data);
        gvNewsSo.setAdapter(hotWordAdapter);
        hotWordAdapter.setOnSoListner(new HotWordAdapter.OnSoListner() {
            @Override
            public void onsoListner(String s,int position) {
                clickedPostion = position;
                title = s;
                isHot = true;
                if (!sq.hasData(s.trim())) {
                    sq.insertData(s.trim());
                }
                intent.putExtra("url", ShenMaUrl + s);
                main.startActivity(intent);
            }
        });
        /**
         * 历史搜索
         */
        sp = new SQlishiUtils(main);
        simpleCursorAdapter = sp.queryData("");
        gridView.setAdapter(simpleCursorAdapter);
        if (shareActivity!=null){
            shareActivity.setOnAddSoListenr(new ShareActivity.OnAddSOlishi() {
                @Override
                public void addsolistner() {
                    simpleCursorAdapter = sp.queryData("");
                    gridView.setAdapter(simpleCursorAdapter);

                }
            });
        }
        clenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.deleteData();
                simpleCursorAdapter = sp.queryData("");
                gridView.setAdapter(simpleCursorAdapter);
            }
        });
        getWords();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isHot){
            if (hotWordAdapter.getData().size() <= 16){
                hotWordAdapter.getData().remove(clickedPostion);
                getWords();
            }else {
                hotWordAdapter.refreshView(clickedPostion);
            }
        }
        if (simpleCursorAdapter != null&&sp!=null){
            simpleCursorAdapter = sp.queryData("");
            gridView.setAdapter(simpleCursorAdapter);
        }

        isHot = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        data.clear();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (map != null) {
                map.put("click_search", "热门搜索");
                MobclickAgent.onEvent(main, "search", map);
            }
            if (null != sp) {
                simpleCursorAdapter = sp.queryData("");
                gridView.setAdapter(simpleCursorAdapter);
            }
            if (hotWordAdapter != null){
                getWords();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void getWords() {
        main.setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        Observable words = RetroFactory.getInstance().getWords(map);
        words.compose(main.composeFunction).subscribe(new BaseObserver<ReCiBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(ReCiBean bean) {
                if (bean != null){
                    LogUtils.e("热词的数据出来了："+bean.getList().toString());
                    hotWordAdapter.addAll(bean.getList());
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
        return R.layout.fragment_so_reci;
    }


    OnScolistenr scolistenr;

    public void setOnScolistenr(OnScolistenr scolistenr) {
        this.scolistenr = scolistenr;
    }

    public interface OnScolistenr {
        void onSoclistner(boolean isSoc);
    }
}
