package com.tianguo.zxz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.HelpCentnetActivity;

import java.util.List;

import static com.tianguo.zxz.bean.FanKuiBean.GuideListBean;

/**
 * Created by lx on 2017/5/17.
 */

public class GongLueAdapt extends RecyclerView.Adapter<GongLueAdapt.MyHoder> {
    List<GuideListBean> guideList;
    Context context;
    public GongLueAdapt(List<GuideListBean> guideList, Context context) {
        this.guideList=guideList;
        this.context=context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gonglue_itme_student, parent, false);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(MyHoder holder, final int position) {
        holder.tvnum.setText(position+1+".");
        if (position>2){
            holder.re.setVisibility(View.GONE);
        }
        holder.teile.setText(guideList.get(position).getT());
        holder.text.setText(guideList.get(position).getB());
        if (TextUtils.isEmpty(guideList.get(position).getU())){
holder.icon.setVisibility(View.GONE);
        }else {
            Glide.with(this.context).load(guideList.get(position).getU()).placeholder(R.mipmap.img_bg).centerCrop().override(50,50).into(holder.icon);

        }
        holder.fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HelpCentnetActivity.class);
                intent.putExtra("id",guideList.get(position).getI());
                intent.putExtra("isHelp", false);
                intent.putExtra("centent", guideList.get(position).getT());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return guideList==null?0:guideList.size();
    }

    public class MyHoder extends RecyclerView.ViewHolder {
        TextView tvnum;
        TextView teile;
        TextView re;
        TextView text;

        ImageView icon;
        private final LinearLayout fankui;

        public MyHoder(View itemView) {
            super(itemView);
            tvnum = (TextView) itemView.findViewById(R.id.tv_student_num);
            icon = (ImageView) itemView.findViewById(R.id.vv_fankui_list_icon);
            teile = (TextView) itemView.findViewById(R.id.vv_fankui_list_teile);
            re = (TextView) itemView.findViewById(R.id.vv_fankui_list_teile_re);
            text = (TextView) itemView.findViewById(R.id.vv_fankui_list_text);
            fankui = (LinearLayout) itemView.findViewById(R.id.ll_cent_fankui_ll);


        }
    }
}
