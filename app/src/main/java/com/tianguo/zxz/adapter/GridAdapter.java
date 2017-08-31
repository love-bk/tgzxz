package com.tianguo.zxz.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.SoWebActivity;
import com.tianguo.zxz.bean.SoDaoBean.SearchListBean;

import java.util.List;

/**
 * Created by lx on 2017/5/19.
 */

public class GridAdapter extends BaseAdapter {
    Activity main;
    List<SearchListBean> searchList;
    public GridAdapter(Activity main, List<SearchListBean> searchList) {
        this.main=main;
        this.searchList=searchList;
    }

    @Override
    public int getCount() {
        return searchList==null?0:searchList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
convertView = View.inflate(main,R.layout.gc_nottime_itme,null);
        ImageView iv_01 = (ImageView) convertView.findViewById(R.id.iv_1);
        TextView tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
        Glide.with(main).load(searchList.get(position).getP()).placeholder(R.mipmap.img_bg).centerCrop().into(iv_01);
        tv_1.setText(searchList.get(position).getN());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main, SoWebActivity.class);
                intent.putExtra("url",searchList.get(position).getU());
                main.startActivity(intent);
            }
        });
        return convertView;
    }
}
