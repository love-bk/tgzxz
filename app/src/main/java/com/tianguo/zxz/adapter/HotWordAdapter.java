package com.tianguo.zxz.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.ReCiBean;
import com.tianguo.zxz.uctils.GetAwardUtils;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by admin on 2017/7/13.
 */

public class HotWordAdapter extends BaseAdapter {
    private boolean flag;
    BaseActivity main;
    OnSoListner onSoListner;
    List<ReCiBean.ListBean> data;
    HashMap<String, String> map = new HashMap<>();
    Random random = new Random();
    private int reSou;
    public static int[] randArr;

    public HotWordAdapter(BaseActivity main, List<ReCiBean.ListBean> data) {
        this.main = main;
        this.data = data;
        reSou = SharedPreferencesUtil.getReSou(main);
    }
   public HotWordAdapter(BaseActivity main, List<ReCiBean.ListBean> data,boolean flag) {
        this.main = main;
        this.data = data;
       this.flag = flag;
        reSou = SharedPreferencesUtil.getReSou(main);
    }



    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        } else {
            if (flag){
                return data.size();
            }else {
                if (data.size() > 16)
                    return 16;
                return data.size();
            }

        }
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
        if (convertView == null) {
            convertView = LayoutInflater.from(main).inflate(R.layout.item_hot_word, parent, false);
            convertView.setTag(new HotWordViewHolder(convertView));
        }
        final HotWordViewHolder holder = (HotWordViewHolder) convertView.getTag();
        if (randArr != null) {
            for (int j = 0; j < randArr.length; j++) {
                if (randArr[j] == position) {
                    holder.iv_icon.setVisibility(View.VISIBLE);
                    break;
                } else {
                    holder.iv_icon.setVisibility(View.GONE);
                }
            }
        }

        holder.tv_news_teile.setText(data.get(position).getName());
        holder.tv_news_teile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String falgStr = "";
                if (data.get(position).getBus() == 0) {
                    falgStr = "商业热词";
                } else {
                    falgStr = "热词";
                }
                map.put("click_shenma", falgStr + position);
                MobclickAgent.onEvent(main, "feed", map);
                onSoListner.onsoListner(holder.tv_news_teile.getText().toString().trim(), position);
                if (SharedPreferencesUtil.getReSou(main) != 10) {
                    reSou++;
                    SharedPreferencesUtil.saveReSou(main, reSou);
                    int i = random.nextInt(5);
                    if (i == 3 || SharedPreferencesUtil.getReSou(main) == 5) {
                        GetAwardUtils.getAward(1, System.currentTimeMillis() + "", main, new GetAwardUtils.GetAwardListner() {
                            @Override
                            public void getAward(double m) {
                                reSou = 0;
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

    public void refreshView(int clickedPostion) {
        getMandom();
        data.remove(clickedPostion);
        notifyDataSetChanged();
    }

    public List<ReCiBean.ListBean> getData() {
        return data;
    }

    public void setData(List<ReCiBean.ListBean> data) {
        this.data = data;
    }

    public interface OnSoListner {
        void onsoListner(String datas, int postion);
    }

    public void addAll(List<ReCiBean.ListBean> datas) {
        if (data != null){
            data.addAll(datas);
            getMandom();
            notifyDataSetChanged();
        }
    }

    public void addAll(List<ReCiBean.ListBean> datas,boolean isRefresh) {
        if (isRefresh&&data != null){
            data.clear();
        }
        data.addAll(datas);
        getMandom();
        notifyDataSetChanged();
    }
    private class HotWordViewHolder {
        TextView tv_news_teile;
        ImageView iv_icon;

        public HotWordViewHolder(View convertView) {
            tv_news_teile = (TextView) convertView.findViewById(R.id.tv_news_itmes);
            iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
        }
    }

    private void getMandom() {
        int size = 0;
        int length;
        if (flag){
            size = 2;
            length = data.size();
        }else {
            size = 6;
            length = 16;
        }
        LogUtils.e("火图标的个数"+size,"gjj");
        randArr = new int[size];

        int i = 0;
        while (i < size) {
            int rand = new Random().nextInt(length);
            boolean isRandExist = false;
            for (int j = 0; j < randArr.length; j++) {
                if (randArr[j] == rand) {
                    isRandExist = true;
                    break;
                }
            }
            if (isRandExist == false) {
                randArr[i] = rand;
                i++;
            }
        }

        LogUtils.e("火图标数组的个数"+randArr.length,"gjj");
        for (int j = 0; j < randArr.length; j++) {
            LogUtils.e("图标数组的"+j+"对应："+randArr[j],"gjj");
        }
    }
//    private void getMandom(int size,int clickPostion){
//        randArr = new int[6];
//        int i=0;
//        while(i<6){
//            int rand=new Random().nextInt(size);
//            boolean isRandExist=false;
//            for(int j=0;j<randArr.length;j++){
//                if(randArr[j]==rand||rand == clickPostion){
//                    isRandExist=true;
//                    break;
//                }
//            }
//            if(isRandExist==false){
//                randArr[i]=rand;
//                i++;
//            }
//        }
//    }
}