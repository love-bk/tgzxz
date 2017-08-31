package com.tianguo.zxz.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.GetMoneyActivity;
import com.tianguo.zxz.activity.MyActivity.NoPassManeryActivity;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.MyGGbean;

import java.util.List;

/**
 * Created by lx on 2017/6/16.
 */

public class GetMonerAdapter extends BaseAdapter {
    BaseActivity main;
    List<MyGGbean.ArrayBean> data;

    public GetMonerAdapter(GetMoneyActivity main, List<MyGGbean.ArrayBean> data) {
        this.main = main;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(main, R.layout.itme_getmonery, null);
        TextView tv_getmoney_list = (TextView) convertView.findViewById(R.id.tv_getmoney_list);
        TextView tv_getmoney_time = (TextView) convertView.findViewById(R.id.tv_getmoney_time);
        tv_getmoney_time.setText(data.get(position).getT());

        switch (data.get(position).getS()) {
            case 1:
                tv_getmoney_list.setText(data.get(position).getNum() + "元（处理中）");
                break;
            case 2:
                tv_getmoney_list.setText(data.get(position).getNum() + "元（已完成）");
                break;
            case 3:
                String s = data.get(position).getNum() + "元（提现失败）？";
                SpannableStringBuilder builder = new SpannableStringBuilder(s);
                ForegroundColorSpan red = new ForegroundColorSpan(Color.RED);
                builder.setSpan(red, s.length() - 1, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_getmoney_list.setText(builder);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
main.startActivity(new Intent(main,NoPassManeryActivity.class));
                    }
                });
                break;

        }

        return convertView;
    }
}
