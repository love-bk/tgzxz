package com.tianguo.zxz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 2017/6/27.
 */
public class YYXZDetailItemAdapter extends RecyclerView.Adapter<YYXZDetailItemAdapter.ViewHolder> {


    private  Context context;
    private List<String> list;

    public YYXZDetailItemAdapter(List<String> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.yyxz_detail_item, null);
        return new ViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(context)
                    .load(list.get(position))
                    .into(holder.ivDetail);
    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_detail)
        ImageView ivDetail;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}

