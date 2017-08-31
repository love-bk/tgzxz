package com.tianguo.zxz.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.ReCiBean;
import com.tianguo.zxz.uctils.GetAwardUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by lx on 2017/4/21.
 */

public class gcSoAdapter extends BaseAdapter {
    BaseActivity main;
    OnSoListner onSoListner;
    ArrayList<ReCiBean> data;
    HashMap<String, String> map = new HashMap<>();
    Random random = new Random();
    private int reSou;

    public gcSoAdapter(BaseActivity main, ArrayList<ReCiBean> data) {
        this.main = main;
        this.data = data;
        reSou = SharedPreferencesUtil.getReSou(main);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(main, R.layout.gc_itme, null);
        if (data.size() - 1 < position) {
            return convertView;
        }
        final TextView tv_news_teile = (TextView) convertView.findViewById(R.id.tv_news_itmes);
        if (null == data.get(position).getTitle()) {
            return convertView;
        }

        tv_news_teile.setText(data.get(position).getTitle());
        tv_news_teile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("click_shenma", "神马热词"+position);
                MobclickAgent.onEvent(main, "feed", map);
                onSoListner.onsoListner(tv_news_teile.getText().toString().trim());
                if (SharedPreferencesUtil.getReSou(main) != 10) {
                    reSou++;
                    SharedPreferencesUtil.saveReSou(main, reSou);
                    int i = random.nextInt(5);
                    if (i == 3 || SharedPreferencesUtil.getReSou(main) == 5) {
                        GetAwardUtils.getAward(1, System.currentTimeMillis() + "", main, new GetAwardUtils.GetAwardListner() {
                            @Override
                            public void getAward(double m) {
                                reSou=0;
                                SharedPreferencesUtil.saveReSou(main, 0);
                            }

                            @Override
                            public void nullAward() {
                                SharedPreferencesUtil.saveReSou(main, 10);

                            }
                        });
                    }


                }

            }
        });
        return convertView;
    }

    public void setOnSoListner(OnSoListner onSoListner) {
        this.onSoListner = onSoListner;
    }

    public interface OnSoListner {
        void onsoListner(String datas);
    }
}
