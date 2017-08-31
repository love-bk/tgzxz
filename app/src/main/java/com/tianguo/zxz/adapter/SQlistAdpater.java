package com.tianguo.zxz.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.SoWebActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import static com.tianguo.zxz.Flat.ShenMaUrl;

/**
 * Created by lx on 2017/6/1.
 */

public class SQlistAdpater extends SimpleCursorAdapter {
    private Cursor m_cursor;
    private Context m_context;
    private LayoutInflater miInflater;
    HashMap<String, String> map = new HashMap<>();
    private final Intent intent;

    public SQlistAdpater(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        m_context = context;
        m_cursor = c;
        intent = new Intent(context, SoWebActivity.class);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View inflate = View.inflate(m_context,R.layout.histortyorder,null);
        holder.tv_1= (TextView) inflate.findViewById(R.id.tv_1s);
        inflate.setTag(holder);

        return inflate;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        final String name = cursor.getString(cursor.getColumnIndex("name"));
        holder.tv_1.setText(name);
        holder.tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("type", "搜索后");
                MobclickAgent.onEvent(context, "click_search", map);
                intent.putExtra("url",ShenMaUrl+name);
                context.startActivity(intent);
            }
        });

    }

    private class ViewHolder {
        TextView tv_1;
    }
}
