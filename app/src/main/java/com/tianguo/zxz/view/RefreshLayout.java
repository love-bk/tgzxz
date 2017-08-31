package com.tianguo.zxz.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.R;
import com.tianguo.zxz.uctils.AdvancedCountdownTimer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能.
 *
 * @author mrsimple
 */
public class RefreshLayout extends SwipeRefreshLayout implements
        OnScrollListener {
    public int isShow;
    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;
    /**
     * listview实例
     */
    private ListView mListView;

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

    /**
     * ListView的加载中footer
     */
    private View mListViewFooter;
    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    /**
     * @param context
     */
    Context context;
    public RefreshLayout(Context context) {
        this(context, null);

    }

    @SuppressLint("InflateParams")
    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mListViewFooter = LayoutInflater.from(context).inflate(
                R.layout.listview_footer, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mListView == null) {
            getListView();
        }
        try {
            super.onLayout(changed, left, top, right, bottom);
        }catch (Exception e){
            super.onLayout(changed, 0, 0, 1000, 1123);
        }
    }

    /**
     * 获取ListView对象
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                mListView.setOnScrollListener(this);
                Log.d(VIEW_LOG_TAG, "### 找到listview");
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (thchListhe!=null){
            thchListhe.Onthch(event);
        }
        try {
            final int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // 按下
                    mYDown = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 移动
                    mLastY = (int) event.getRawY();

                    break;

                case MotionEvent.ACTION_UP:

                    if (canLoad()) {
                        loadData();
                    }
                    break;
                default:
                    break;
            }

            return super.dispatchTouchEvent(event);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {

        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView
                    .getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {

        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }

    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        try {
        isLoading = loading;
        if (isLoading) {
            try {


            mListView.addFooterView(mListViewFooter);
            AdvancedCountdownTimer advancedCountdownTimer = new AdvancedCountdownTimer(5 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished, int percent) {
                }

                @Override
                public void onFinish() {
                    setLoading(false);
                }
            };
            advancedCountdownTimer.start();
            }catch (Exception e){

            }
        } else {
            try {
            if (mListView.getFooterViewsCount() > 0) {
                mListView.removeFooterView(mListViewFooter);
                mYDown = 0;
                mLastY = 0;
            }
            }catch (Exception e){

            }
        }
        }catch (Exception e){

        }
    }

    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
if (scolistenr!=null){
    scolistenr.onScoListnerLoding(scrollState);
}
    }

    OnScolistner scolistenr;

    public void setContext(MainActivity context) {
        this.context = context;
    }

    public interface OnScolistner {
        void onSoclistner(boolean isShow);
        void onScoListnerLoding(int scrollState);
    }

    public void setOnScoListner(OnScolistner scolistenr) {
        this.scolistenr = scolistenr;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // 滚动时到了最底部也可以加载更多
        if (isShowItmeListner != null) {
            isShowItmeListner.isShowListner(firstVisibleItem, visibleItemCount);
        }
        if (firstVisibleItem == 0) {
            if (scolistenr != null) {
                scolistenr.onSoclistner(false);
            }
        } else {
            if (scolistenr != null) {
                scolistenr.onSoclistner(true);
            }

        }
        if (canLoad()) {
            loadData();
        }
    }

    /**
     * 设置刷新
     */
    public static void setRefreshing(SwipeRefreshLayout refreshLayout,
                                     boolean refreshing, boolean notify) {
        Class<? extends SwipeRefreshLayout> refreshLayoutClass = refreshLayout
                .getClass();
        if (refreshLayoutClass != null) {

            try {
                Method setRefreshing = refreshLayoutClass.getDeclaredMethod(
                        "setRefreshing", boolean.class, boolean.class);
                setRefreshing.setAccessible(true);
                setRefreshing.invoke(refreshLayout, refreshing, notify);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    OnThchListner thchListhe;
    public  void  setOnThchListher(OnThchListner thchListher){
        this.thchListhe=thchListher;

    }
    public  interface  OnThchListner{
        boolean Onthch(MotionEvent ont);
    }

    /**
     * 加载更多的监听器
     *
     * @author mrsimple
     */
    public  interface OnLoadListener {
        void onLoad();
    }

    OnIsShowItmeListner isShowItmeListner;

    public void setOnisShowItmeListner(OnIsShowItmeListner listner) {
        this.isShowItmeListner = listner;

    }

    public interface OnIsShowItmeListner {
        void isShowListner(int star, int end);
    }
}