package com.zhangyan.im.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zhangyan.im.model.dao.UserAccountTable;

/**
 * Created by zhangYan on 2017/8/16.
 * 用户信息的数据库
 */

public class UserAccountDB extends SQLiteOpenHelper {


    public UserAccountDB(Context context) {
        super(context, "account.db", null, 1);
    }

    //数据库创建的时候调用

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //执行创建数据库表的语句
        sqLiteDatabase.execSQL(UserAccountTable.CREATE_TAB);
    }

    //数据库更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
