package com.tianguo.zxz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.R;
import com.tianguo.zxz.bean.TagBean;

import java.util.List;

/**
 * Created by lx on 2017/4/18.
 */

public class MainTeileAdapter extends RecyclerView.Adapter<MainTeileAdapter.ViewHolder> {
    private int selectedType;
    private List<TagBean> arr;
    private OnClineItme onclinitme;
    private MainActivity main;


    private int num;

    public MainTeileAdapter(MainActivity main, List<TagBean> arr) {
        this.main = main;
        this.arr = arr;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teile, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setNum(int num) {
        this.num = num;
        notifyDataSetChanged();
    }

//    public void addAll(List<TagBean> tagBeanList) {
//        if (arr != null)
//            arr.clear();
//        arr.addAll(tagBeanList);
//        notifyDataSetChanged();
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (arr.get(position).getType() == selectedType) {
            holder.teile.setTextColor(main.getResources().getColor(R.color.codetext));
            holder.teile.setBackground(main.getResources().getDrawable(R.drawable.shape_home_nav_bg));
        } else {
            holder.teile.setTextColor(main.getResources().getColor(R.color.teileisfalse));
            holder.teile.setBackground(main.getResources().getDrawable(R.drawable.shape_home));
        }

        holder.teile.setText(arr.get(position).getText());
        holder.teile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclinitme != null&&arr.get(position).getType() != selectedType) {
                    onclinitme.OnClineItmeListner(position,arr.get(position));
                }
            }
        });
    }

    public void setOnClinItmeListner(OnClineItme onclinitme) {
        this.onclinitme = onclinitme;
    }

    @Override
    public int getItemCount() {
        return arr != null ?arr.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton teile;

        public ViewHolder(View itemView) {
            super(itemView);
            teile = (RadioButton) itemView.findViewById(R.id.tv_main_teile_itme);
        }
    }

    //回调接口设置选中的卡片
    public interface OnClineItme {
        void OnClineItmeListner(int poing, TagBean tagBean);
    }

    //根据卡片设置选中标题头
    public void setSelectedType(int selectedType) {
        this.selectedType = selectedType;
        notifyDataSetChanged();
    }


    public int getSelectedType() {
        return selectedType;
    }
}
