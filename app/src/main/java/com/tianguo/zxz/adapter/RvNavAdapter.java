package com.tianguo.zxz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.bean.MakeMoneyBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/17.
 */

public class RvNavAdapter extends BaseAdapter {

    private LayoutInflater inflate;
    private List<MakeMoneyBean> beanList;
    private Context context;
    private GvClickListener gvClickListener;

    public RvNavAdapter(List<MakeMoneyBean> beanList, Context context) {
        this.beanList = beanList;
        this.context = context;
        inflate = LayoutInflater.from(context);
    }

    public interface GvClickListener {
        void onGvClick(MakeMoneyBean bean);
    }


    public void setGvClickListener(GvClickListener gvClickListener) {
        this.gvClickListener = gvClickListener;
    }

    @Override
    public int getCount() {
        return beanList != null ? beanList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = this.inflate.inflate(R.layout.item_mgv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        Glide.with(context).load(beanList.get(position).getImgUrl()).centerCrop().into(viewHolder.ivImg);
        viewHolder.ivImg.setImageResource(beanList.get(position).getImgResId());
        viewHolder.tvTitle.setText(beanList.get(position).getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gvClickListener != null)
                    gvClickListener.onGvClick(beanList.get(position));
            }
        });
        return convertView;
    }

    public interface RvListClickListener {
        void onRvListClick();
    }


    public class ViewHolder {

        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_itemview)
        LinearLayout itemView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
