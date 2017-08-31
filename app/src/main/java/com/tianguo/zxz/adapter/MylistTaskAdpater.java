package com.tianguo.zxz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.YYupImageActivity;
import com.tianguo.zxz.bean.YYdetallBean;
import com.tianguo.zxz.uctils.LogUtils;

import java.util.List;

/**
 * Created by lx on 2017/8/8.
 */

public class MylistTaskAdpater extends BaseAdapter {
    Context context;
    List<YYdetallBean.TaskListBean> otherTask;
    public MylistTaskAdpater(Context context, List<YYdetallBean.TaskListBean> otherTask) {
        this.context = context;
        this.otherTask = otherTask;
    }
    @Override
    public int getCount() {
        LogUtils.e(otherTask.size() + "");
        return otherTask == null ? 0 : otherTask.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongConstant")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = View.inflate(context, R.layout.adapter_mylist_task, null);
        TextView viewById = (TextView) view.findViewById(R.id.tv_text1);
        TextView type = (TextView) view.findViewById(R.id.tv_up_images);
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<body>");
        sb.append(otherTask.get(i).getDesc());
        sb.append("</body>");
        sb.append("</html>");
        viewById.setText(Html.fromHtml(sb.toString()));
        if (otherTask.get(i).getType() == 2 ) {
            if ((i == 0&&otherTask.get(i).getStatus()==0)){
                LogUtils.e(i+""+otherTask.get(i).getStatus()+""+otherTask.get(i-1).getStatus());
                type.setVisibility(View.VISIBLE);
                type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, YYupImageActivity.class);
                        intent.putExtra("id",otherTask.get(i).getId());
                        context.startActivity(intent);
                    }
                });
            }
            if((otherTask.get(i-1).getStatus()==1&&otherTask.get(i).getStatus()==0)){
                type.setVisibility(View.VISIBLE);
                type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, YYupImageActivity.class);
                        intent.putExtra("id",otherTask.get(i).getId());
                        context.startActivity(intent);
                    }
                });
            }
            if (otherTask.get(i).getType() == 2 &&otherTask.get(i).getStatus()==3) {
                type.setText("正在审核");
                type.setBackgroundColor(context.getResources().getColor(R.color.xian));
            }
            type.setBackgroundColor(context.getResources().getColor(R.color.xian));

        } else {

            type.setVisibility(View.GONE);
        }
        return view;
    }
}
