package com.tianguo.zxz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.bean.IncomeDetailBean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 2017/7/11.
 */
public class IncomeDetailAdapter extends RecyclerView.Adapter<IncomeDetailAdapter.MyViewHolder> {
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private  int flag = -1;
    private List<IncomeDetailBean.RecordBean> datas;
    private View mFooterView;
    private boolean isFooter = true;
    public IncomeDetailAdapter(List<IncomeDetailBean.RecordBean> datas,int flag) {
        this.datas = datas;
        this.flag = flag;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new MyViewHolder(mFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_income_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
            return datas == null ? 0 : datas.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            holder.tvSource.setText(datas.get(position).getT());
            if (datas.get(position).getM() < 0){
                if (flag == 1){
                    holder.tvIncomeMoney.setText(datas.get(position).getM()+"金币");
                }else {
                    holder.tvIncomeMoney.setText(getMoney(datas.get(position).getM())+"元");
                }
                holder.tvIncomeMoney.setTextColor(android.graphics.Color.parseColor("#1296db"));
            }else {
                if (flag == 1){
                    holder.tvIncomeMoney.setText("+"+datas.get(position).getM()+"金币");
                    holder.tvIncomeMoney.setTextColor(android.graphics.Color.parseColor("#e93a3a"));
                }else {
                    holder.tvIncomeMoney.setText("+"+getMoney(datas.get(position).getM())+"元");
                    holder.tvIncomeMoney.setTextColor(android.graphics.Color.parseColor("#fbab73"));
                }
            }
            holder.tvTime.setText(getFormatTime(datas.get(position).getD()));
            return;
        } else{
            return;
        }
    }

    private String getFormatTime(long time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    public void addDatas(List<IncomeDetailBean.RecordBean> record) {
        datas.addAll(record);
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_income_money)
        TextView tvIncomeMoney;

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



    public List<IncomeDetailBean.RecordBean> getDatas() {
        return datas;
    }


}
