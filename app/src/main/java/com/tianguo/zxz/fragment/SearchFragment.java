package com.tianguo.zxz.fragment;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.SoWebActivity;
import com.tianguo.zxz.activity.ShareActivity;
import com.tianguo.zxz.adapter.HotWordAdapter;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.ReCiBean;
import com.tianguo.zxz.dao.SQlishiUtils;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.view.HeaderGridView;
import com.tianguo.zxz.view.RefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

import static com.tianguo.zxz.Flat.ShenMaUrl;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.gv_news_so)
    HeaderGridView gvNewsSo;
    @BindView(R.id.sw_news_list)
    RefreshLayout swNewsList;
    private HashMap<String, String> map;
    List<ReCiBean.ListBean> data = new ArrayList<>();
    private Intent intent;
    private HotWordAdapter hotWordAdapter;
    private int clickedPostion = -1;
    private boolean isHot;
    private SQlishiUtils sq;
    private boolean mIsAnim = false;
    private float lastX = 0;
    private float lastY = 0;
    private boolean isDown = false;
    private boolean isUp = false;
    private boolean mIsTitleHide = false;
    private MainFragment fragment;
    private boolean isRefresh;
    private View head;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        head = View.inflate(main, R.layout.gridview_header, null);
        head.findViewById(R.id.iv_refresh).setOnClickListener(this);
        head.findViewById(R.id.tv_search).setOnClickListener(this);
        gvNewsSo.addHeaderView(head);
        /**
         * 热词搜索
         */
        intent = new Intent(main, SoWebActivity.class);
        map = new HashMap<>();
        hotWordAdapter = new HotWordAdapter(main, data);
        gvNewsSo.setAdapter(hotWordAdapter);
        hotWordAdapter.setOnSoListner(new HotWordAdapter.OnSoListner() {
            @Override
            public void onsoListner(String s, int position) {
                clickedPostion = position;
                isHot = true;
                intent.putExtra("url", ShenMaUrl + s);
                main.startActivity(intent);
            }
        });
        swNewsList.setOnThchListher(new RefreshLayout.OnThchListner() {
            @Override
            public boolean Onthch(MotionEvent ont) {

                return dispathTouchEvent(ont);
            }
        });
        swNewsList.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swNewsList.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swNewsList.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, main.getResources().getDisplayMetrics()));

        swNewsList.post(new Runnable() {
            @Override
            public void run() {
                swNewsList.setRefreshing(true);
            }
        });
        swNewsList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.e("进来了", "gjj");
                swNewsList.setRefreshing(false);
            }
        });
        getWords();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isHot) {
            LogUtils.e("被点击了"+isHot,"gjj");
            if (hotWordAdapter.getData().size() <= 16) {
                hotWordAdapter.getData().remove(clickedPostion);
                getWords();
            } else {
                hotWordAdapter.refreshView(clickedPostion);
            }
        }
        LogUtils.e("没有被点击了"+isHot,"gjj");
        isHot = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        data.clear();
    }


    private void getWords() {
        main.setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        Observable words = RetroFactory.getInstance().getWords(map);
        words.compose(main.composeFunction).subscribe(new BaseObserver<ReCiBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(ReCiBean bean) {
                if (bean != null) {
                    hotWordAdapter.addAll(bean.getList(),isRefresh);
                }
                swNewsList.setRefreshing(false);
            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
                swNewsList.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }


    OnScolistenr scolistenr;

    public void setOnScolistenr(OnScolistenr scolistenr) {
        this.scolistenr = scolistenr;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                try {
                    map.put("click_search", "搜索中");
                    MobclickAgent.onEvent(main, "search", map);
                    Intent intent = new Intent(main, ShareActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                }
                break;
            case R.id.iv_refresh:
                isRefresh = true;
                getWords();
                break;
        }
    }

    public interface OnScolistenr {
        void onSoclistner(boolean isSoc);
    }

    public void setMainfragment(MainFragment fragment) {
        this.fragment = fragment;
    }

    private boolean dispathTouchEvent(MotionEvent event) {
        if (mIsAnim) {
            return false;
        }
        final int action = event.getAction();

        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                isUp = dX < 8 && dY > 30 && !mIsTitleHide && !down;
                isDown = dX < 8 && dY > 30 && mIsTitleHide && down;
                if (isUp) {
                    View view = this.fragment.getLlNewsOnew();
                    float[] f = new float[2];
                    f[0] = 0.0F;
                    f[1] = -fragment.getLlBanner().getHeight();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.setDuration(200);
                    animator1.start();
                    setMarginTop(1);
                    animator1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            mIsAnim = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    LogUtils.e("aaaaaaaaa");
                } else if (isDown) {
                    LogUtils.e("bbbbbbb");
                    View view = fragment.getLlNewsOnew();
                    float[] f = new float[2];
                    f[0] = -fragment.getLlBanner().getHeight();
                    f[1] = 0F;
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setDuration(200);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.start();
                    animator1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            setMarginTop(0);
                            mIsAnim = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
        }
        return false;

    }

    public void setMarginTop(int i) {
        if (i == 1) {
            final LinearLayout.LayoutParams ls = (LinearLayout.LayoutParams) fragment.getHome().getLayoutParams();
            ls.height = fragment.getHome().getHeight() + fragment.getLlBanner().getHeight();
            fragment.getHome().setLayoutParams(ls);
            fragment.getHome().invalidate();
            LogUtils.e(fragment.getHome().getHeight() + "sssssss");
        } else {
            final LinearLayout.LayoutParams ls = (LinearLayout.LayoutParams) fragment.getHome().getLayoutParams();
            ls.height = LinearLayout.LayoutParams.MATCH_PARENT;
            fragment.getHome().setLayoutParams(ls);
            fragment.getHome().invalidate();
            LogUtils.e(fragment.getHome().getHeight() + "sssssss");
        }


    }
}
