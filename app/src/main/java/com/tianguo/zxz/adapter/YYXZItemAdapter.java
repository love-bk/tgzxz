package com.tianguo.zxz.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.R;
import com.tianguo.zxz.bean.YYListBean;
import com.tianguo.zxz.uctils.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by andy on 2017/6/26.
 */
public class YYXZItemAdapter extends RecyclerView.Adapter<YYXZItemAdapter.ViewHolder> {

    private  Context context;
    private List<YYListBean> list;
    private OnItemClickListener onItemClickListener;
    public YYXZItemAdapter(List<YYListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.yyxz_item, null);
        return new ViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvContent.setText(list.get(position).getTi());
        LogUtils.e(list.get(position).getTi()+"ssssss");
        holder.centent2.setText(list.get(position).getB());
        SpannableString ss = new SpannableString("总奖励  " + +list.get(position).getMo());
        ForegroundColorSpan fsp = new ForegroundColorSpan(Color.GREEN);
        ss.setSpan(fsp, 3, ss.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (list.get(position).getStatus()==1){
            if (list.get(position).getNum()!=0){

                holder.tvMoney.setText("剩余"+list.get(position).getNum()+"个任务\n"+ss+"万金币");
            }else {
                holder.tvMoney.setText("今天任务已抢完"+"\n"+ss+"万金币");
            }
        }else {
            holder.tvMoney.setText(ss+"万金币");
        }

        holder.tv_num.setText(list.get(position).getDownload_num()+"万人下载"+"             "+list.get(position).getSi()+"M");
        Glide.with(context)
                .load(list.get(position).getIc())
                .into(holder.ivYyIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v,list.get(position),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_yy_icon)
        ImageView ivYyIcon;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_content2)
        TextView centent2;
        @BindView(R.id.tv_num)
        TextView tv_num;
        private OnItemClickListener mOnItemClickListener;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
    public interface OnItemClickListener{
        void onItemClick(View view, YYListBean yyListBean, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;

    }
}




