package com.example.a12306.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * date   : 2019/9/38:35
 * desc   :
 * version: 1.0
 */
public class QueryHistoryDBOpenHelper extends SQLiteOpenHelper {


    public QueryHistoryDBOpenHelper(Context context,String name,
                                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    //数据库第一次创建时调用
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table historytable(_id integer primary key autoincrement,stationfrom varchar(255),stationto varchar(255))");//执行创建表的sql语句
    }

    //数据库改变时，将原有的表删除，然后建立新表
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                String sql = "drop table if exists historytable";
                db.execSQL(sql);
                onCreate(db);
    }
}
