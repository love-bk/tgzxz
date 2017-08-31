package com.tianguo.zxz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.bean.QiandaoBean;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 2017/7/5.
 */
public class TaskListViewAdapter extends BaseAdapter implements View.OnClickListener {

    private final LayoutInflater inflate;
    private List<QiandaoBean.TaskListBean> lists;
    private Context context;
    private TaskClickListener listener;
    public TaskListViewAdapter(List<QiandaoBean.TaskListBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists == null ? 0 : lists.size();
    }

    public String getY(int position) {
        return BigDecimal.valueOf(lists.get(position).getM()).divide(new BigDecimal(100)).toString();
    }

    @Override
    public void onClick(View v) {

    }

    public void update(int p, int is) {
        lists.get(p).setIs(is);
        if (this!=null){
            notifyDataSetChanged();
        }
    }

    public interface TaskClickListener{
        void taskOnClick(QiandaoBean.TaskListBean taskListBean, int position);
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setListener(TaskClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = this.inflate.inflate(R.layout.item_rw, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(lists.get(position).getT());
        viewHolder.tvMoney.setText("奖励"+getY(position)+"元");
        viewHolder.tvContent.setText(lists.get(position).getC());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.taskOnClick(lists.get(position),position);
                }
            }
        });
        viewHolder.btnRw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.taskOnClick(lists.get(position),position);
                }
            }
        });
        switch (lists.get(position).getIs()){
            case 0:
                viewHolder.btnRw.setText("做任务");
                convertView.setClickable(true);
                viewHolder.btnRw.setClickable(true);
                viewHolder.btnRw.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.task_unfinish_bg));
                break;
            case 1:
                viewHolder.btnRw.setText("领奖励");
                convertView.setClickable(true);
                viewHolder.btnRw.setClickable(true);
                viewHolder.btnRw.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.task_reward_bg));
                break;
            case 2:
                viewHolder.btnRw.setText("已完成");
                viewHolder.btnRw.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.task_finish));
                break;
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.btn_rw)
        Button btnRw;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void addAll(List<QiandaoBean.TaskListBean> datas){
        if (lists != null){
            lists.clear();
            lists.addAll(datas);
        }else {
            lists = datas;
        }
        notifyDataSetChanged();
    }

    public List<QiandaoBean.TaskListBean> getLists() {
        return lists;
    }


}
