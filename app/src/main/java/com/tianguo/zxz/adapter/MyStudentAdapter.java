package com.tianguo.zxz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.DiscipleBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lx on 2017/6/20.
 */

public class MyStudentAdapter extends RecyclerView.Adapter<MyStudentAdapter.ViewHolder> {
    BaseActivity main;
    List<DiscipleBean.FollowBean> data;

    public MyStudentAdapter(BaseActivity main, List<DiscipleBean.FollowBean> data) {
        this.main = main;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mystudent_ranking, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvMyRanking.setText(position+4+"");
        holder.tvMyRankingId.setText(data.get(position).getFollower()+"");
        holder.tvMyRankingMoney.setText(data.get(position).getMoney()/100.0+"元");
        holder.tvMyRankingPhone.setText(data.get(position).getUserPhone()+"");

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_my_ranking)
        TextView tvMyRanking;
        @BindView(R.id.tv_my_ranking_id)
        TextView tvMyRankingId;
        @BindView(R.id.tv_my_ranking_phone)
        TextView tvMyRankingPhone;
        @BindView(R.id.tv_my_ranking_money)
        TextView tvMyRankingMoney;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
