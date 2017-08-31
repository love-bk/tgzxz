package com.tianguo.zxz.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.GGWed;
import com.tianguo.zxz.activity.WebListActivity;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.NewsDataBean;
import com.tianguo.zxz.uctils.DownGGUtils;
import com.tianguo.zxz.uctils.GuanGaoUtils;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.view.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by lx on 2017/6/29.
 */

public class WebListDataAdaperte extends RecyclerView.Adapter<WebListDataAdaperte.ViewHolder> {
    List<Object> listData;
    private final GuanGaoUtils guanGaoUtils;
    BaseActivity main;
    int ps;

    public WebListDataAdaperte(BaseActivity main, List<Object> listData, int ps) {
        guanGaoUtils = new GuanGaoUtils(false, main);
        this.listData = listData;
        this.ps = ps;
        this.main = main;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position>listData.size()-1){
            return;
        }
        if (listData.get(position) instanceof NewsDataBean.AdBean) {
            final NewsDataBean.AdBean adBean = (NewsDataBean.AdBean) listData.get(position);
            holder.iconAndBlurb.setVisibility(View.VISIBLE);
            holder.teile.setText(adBean.getN());
            Glide.with(main).load(adBean.getP()).placeholder(R.mipmap.img_bg).centerCrop().into(holder.photo);
            holder.iconAndBlurb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(adBean.getU())) {
                        Intent service = new Intent(main, DownGGUtils.class);
                        service.putExtra("downloadurl", adBean.getW());
                        service.putExtra("teile", adBean.getA());
                        service.putExtra("descrption", adBean.getN());
                        main.startService(service);
                        ToastUtil.showMessage("开始下载");
                    } else {
                        Intent intent = new Intent(main, GGWed.class);
                        intent.putExtra("url", adBean.getU());
                        main.startActivity(intent);
                    }

                }
            });

        } else {
            holder.iconAndBlurb.setVisibility(View.GONE);
            if (listData.get(position) instanceof NewsDataBean.NewsBean){
            final NewsDataBean.NewsBean newsBean = (NewsDataBean.NewsBean) listData.get(position);
            holder.llNewsone.setVisibility(View.VISIBLE);
            String body = newsBean.getThumb();
            if (body != null && !body.isEmpty()) {
                String[] split = body.split("[,]");
                Glide.with(main).load(split[0]).placeholder(R.mipmap.img_bg).centerCrop().into(holder.tvNewsImage);
            }
            holder.tvNewsText.setText(newsBean.getTitle());
            holder.ivIsshouHongbao.setVisibility(View.GONE);
            holder.llNewsone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(main, WebListActivity.class);
                    intent.putExtra("id", newsBean.getI());
                    intent.putExtra("award", 0);
                    intent.putExtra("ps", ps);
                    main.startActivity(intent);
                }
            });
            }

        }
    }


    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_news_image)
        ImageView tvNewsImage;
        @BindView(R.id.iv_isshou_hongbao)
        ImageView ivIsshouHongbao;
        @BindView(R.id.tv_news_text)
        TextView tvNewsText;
        @BindView(R.id.ll_news_one)
        RelativeLayout llNewsone;
        @BindView(R.id.gv_cent_shishi)
        MyGridView gvCentShishi;
        @BindView(R.id.gv_cent_layout)
        LinearLayout gvCentLayout;
        @BindView(R.id.photo)
        ImageView photo;
        @BindView(R.id.teile)
        TextView teile;
        @BindView(R.id.tv_1)
        TextView tv1;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.icon_and_blurb)
        RelativeLayout iconAndBlurb;
        @BindView(R.id.v_start)
        LinearLayout vStart;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
