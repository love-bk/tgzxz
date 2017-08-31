package com.tianguo.zxz.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tianguo.zxz.adapter.SQlistAdpater;

/**
 * Created by lx on 2017/6/1.
 */

public class SQlishiUtils {
    private RecordSQLiteOpenHelper  helper ;
    private SQLiteDatabase db;
    Context context;
    private SQlistAdpater simpleCursorAdapter;

    public SQlishiUtils(Context context) {
        this.context=context;
        helper = new RecordSQLiteOpenHelper(context);
    }
    /**
     * 插入数据
     */
    public void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    public SQlistAdpater queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        simpleCursorAdapter = new SQlistAdpater(context, android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1});
        // 设置适配器
        return simpleCursorAdapter;
    }
    /**
     * 检查数据库中是否已经有该条记录
     */
    public boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    public void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }
}
