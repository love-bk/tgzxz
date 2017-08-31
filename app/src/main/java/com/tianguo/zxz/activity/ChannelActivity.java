package com.tianguo.zxz.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.ChannelAdapter;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.NewsListBean;
import com.tianguo.zxz.bean.TagBean;
import com.tianguo.zxz.listener.ItemDragHelperCallback;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;


public class ChannelActivity extends BaseActivity {

    @BindView(R.id.recy)
    RecyclerView mRecy;
    @BindView(R.id.tv_back)
    TextView tvTitle;
    private List<TagBean> items = new ArrayList<>();
    private List<TagBean> otherItems = new ArrayList<>();
    private List<TagBean> backUpItems = new ArrayList<>();
    private ChannelAdapter adapter;
    private int selectedPosition;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_channel;
    }

    @Override
    protected void initView() {
        init();
    }

    @Override
    protected void initData() {

    }

    private void init() {
        tvTitle.setText(R.string.classify_setting);
        items = (List<TagBean>) getIntent().getSerializableExtra(Constant.CHANNEL);
        backUpItems.addAll(items);
        otherItems = (List<TagBean>) getIntent().getSerializableExtra(Constant.OTHER_CHANNEL);
        selectedPosition = getIntent().getIntExtra(Constant.SELECTED_POSITION,-1);

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecy.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecy);

        adapter = new ChannelAdapter(this, helper, items, otherItems,selectedPosition);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        mRecy.setAdapter(adapter);
        adapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                skipHomePage(items,position);
            }
        });
        adapter.setmCompleteListener(new ChannelAdapter.OnCompleteListener() {
            @Override
            public void onComplete(List<TagBean> list) {

            }
        });
    }

    private void setMyChannel(List<TagBean> list) {
        //加载导航
            try {
                setLoadingFlag(false);

                HashMap<String, Object> map = new HashMap<>();
                map.put("sso", SharedPreferencesUtil.getSSo(this));
                map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
                String type="";
                if (list != null)
                    for (int i = 0; i < list.size(); i++) {
                        type = type+list.get(i).getType()+",";
                    }
                map.put("types",type);
                LogUtils.e("传递的参数"+type);
                Observable observable = RetroFactory.getInstance().setMyChannel(map);
                observable.compose(composeFunction).subscribe(new BaseObserver<NewsListBean>(this, this.pd) {
                    @Override
                    protected void onHandleSuccess(final NewsListBean newsListBean) {
                        List<TagBean> otherType = newsListBean.getOtherType();
                        List<TagBean> userType = newsListBean.getUserType();
                        Gson gson = new Gson();
                        if (newsListBean != null){
                            if (otherType !=null){
                                SharedPreferencesUtil.saveNewOtherType(ChannelActivity.this,gson.toJson(otherType));
                            }
                            if (userType !=null&& userType.size()>0){
                                SharedPreferencesUtil.saveNewUserType(ChannelActivity.this,gson.toJson(userType));
                            }

                        }

                        LogUtils.e("设置成功，其他的"+otherType.toString(),"gjj");
                        LogUtils.e("设置成功，我的"+userType.toString(),"gjj");
                    }

                    @Override
                    public void onHandleError(int code, String message) {
                        LogUtils.e("哈哈哈哈哈哈哈数据请求失败了" + code);
                    }
                });
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        skipHomePage(items,-1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (items != null)
            items.clear();
        if (otherItems != null)
            otherItems.clear();
    }


    private void skipHomePage(List<TagBean> list,int position){
        if (items != null&&items.size()>0){
            if (backUpItems!=null){
                if (backUpItems.size()!=items.size()){
                    //调用接口
                    setMyChannel(items);
                }else {
                    boolean flag = false;
                    for (int i = 0; i < backUpItems.size(); i++) {
                        if (backUpItems.get(i).getType() != items.get(i).getType()){
                            flag = true;
                            break;
                        }
                    }
                    //调用接口
                    if (flag)
                        setMyChannel(items);
                }
            }
            //将数组传递回去
            Intent data = new Intent();
            data.putExtra(Constant.CHANNEL, (Serializable) items);
            data.putExtra(Constant.IS_SELECTEDP,position);
            setResult(205, data);
            finish();
            return;
        }else {
            ToastUtil.showMessage("操作无效，频道列表不能为空");
        }
    }
}
