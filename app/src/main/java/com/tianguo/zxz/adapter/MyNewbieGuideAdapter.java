package com.tianguo.zxz.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tianguo.zxz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/8/17.
 */

public class MyNewbieGuideAdapter extends PagerAdapter {
    private List<ImageView> list;
    int imgs[]={R.mipmap.n1,R.mipmap.n2,R.mipmap.n3};
    private NewbieCallback newbieCallback;
    public MyNewbieGuideAdapter(Context context) {
        initData(context);
    }

    private void initData(Context context) {
        list=new ArrayList<>();
        //将imgs转成ImageView对象,然后放在list中
        for (int i = 0; i < imgs.length; i++) {
            //得到ImageView对象
            ImageView iv=new ImageView(context);
            iv.setImageResource(imgs[i]);
            list.add(iv);
        }

    }

    public void setNewbieCallback(NewbieCallback newbieCallback) {
        this.newbieCallback = newbieCallback;
    }

    public interface NewbieCallback{
        void onNewbieCallback();
    }
    //得到数据的总条数
    @Override
    public int getCount() {
        return list == null?0:list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //将指定view对象存储到视图组中
        container.addView(list.get(position));
        if (position == 2){
            list.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (newbieCallback!=null){
                        newbieCallback.onNewbieCallback();
                    }
                }
            });
        }
        return list.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0==arg1;
    }

}
