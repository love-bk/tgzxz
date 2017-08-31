package com.tianguo.zxz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.R;
import com.tianguo.zxz.bean.DiscipleBean;
import com.tianguo.zxz.view.GlideCircleTransform;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/19.
 */

public class ApprenticeAdapter  extends RecyclerView.Adapter<ApprenticeAdapter.MyViewHolder> {
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private List<DiscipleBean.FollowBean> datas;
    private View mFooterView;
    private boolean isFooter = true;
    private Context context;
    public ApprenticeAdapter(List<DiscipleBean.FollowBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new MyViewHolder(mFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disciple, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            holder.tvUserName.setText(datas.get(position).getFollower()+"");
            Glide.with(context).load(datas.get(position).getHead()).
                    placeholder(R.mipmap.zhuanlogo).centerCrop().
                    transform(new GlideCircleTransform(context)).into(holder.ivImg);
            holder.tvMoney.setText("+"+ ((double) datas.get(position).getMoney())/100.0+"元");
            holder.tvTime.setText(getFormatTime(datas.get(position).getTime()));
            return;
        } else{
            return;
        }
    }

    private String getFormatTime(long time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date d1=new Date(time);
        return format.format(d1);
    }

    /**
     * 将分转换为元
     *
     * @param value
     * @return
     */
    public String getMoney(int value) {
        return BigDecimal.valueOf(value).divide(new BigDecimal(100)).toString();
    }
    public void addDatas(List<DiscipleBean.FollowBean> record) {
        if (datas!=null)
            datas.clear();
        datas.addAll(record);
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_username)
        TextView tvUserName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.iv_img)
        ImageView ivImg;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView == mFooterView)
                return;
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }



    public List<DiscipleBean.FollowBean> getDatas() {
        return datas;
    }


}