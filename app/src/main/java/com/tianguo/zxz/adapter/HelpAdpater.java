package com.tianguo.zxz.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.HelpCentnetActivity;
import com.tianguo.zxz.bean.HelpListsBean.HelpListBean;

import java.util.List;

/**
 * Created by lx on 2017/5/16.
 */

public class HelpAdpater extends BaseAdapter {
    Activity main;
    List<HelpListBean> data;
    String tencet;
    public HelpAdpater(Activity main, List<HelpListBean> data) {
        this.main=main;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
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
        View inflate = View.inflate(main, R.layout.adapter_help_list, null);
        TextView viewById = (TextView) inflate.findViewById(R.id.tv_list_item);
        switch (data.get(position).getP()){
            case 1:
                tencet = "【必看】";
                break;
            case 2:
                tencet = "【提现问题】";
                break;
            case 3:
                tencet = "【注册问题】";
                break;
            case 4:
                tencet = "【邀请收徒】";
                break;
            case 5:
                tencet = "【其他问题】";
                break;

        }
        viewById.setText(tencet+data.get(position).getT());
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main, HelpCentnetActivity.class);
                intent.putExtra("id",data.get(position).getI());
                intent.putExtra("centent",tencet);
                intent.putExtra("isHelp", true);
                main.startActivity(intent);
            }
        });
        return inflate;
    }
}
