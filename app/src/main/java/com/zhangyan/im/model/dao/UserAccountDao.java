package com.zhangyan.im.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhangyan.im.model.bean.UserInfo;
import com.zhangyan.im.model.db.UserAccountDB;

/**
 * Created by zhangYan on 2017/8/16.\
 * 用户账号数据库的操作类
 */

public class UserAccountDao {

    private UserAccountDB mHelper;

    public UserAccountDao(Context context) {
        mHelper = new UserAccountDB(context);

    }

    /**
     * 添加用户到数据库
     */
    public void addAccount(UserInfo user) {
        //获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行添加操作
        ContentValues values = new ContentValues();
        values.put(UserAccountTable.COL_HXID, user.getHxid());
        values.put(UserAccountTable.COL_NAME, user.getName());
        values.put(UserAccountTable.COL_NICKNAME, user.getNickName());
        values.put(UserAccountTable.COL_PHOTO, user.getPhoto());
        db.replace(UserAccountTable.TAB_NAME, null, values);
    }

    /**
     * 根据环信id获取所有的用户信息
     */
    public UserInfo getAccountByHxid(String hxId) {
        //获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行查询语句
        String sql = "select * from " + UserAccountTable.TAB_NAME + " where " + UserAccountTable.COL_HXID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxId});
        UserInfo userInfo = null;
        if (cursor.moveToNext()) {
            //封装资源
            userInfo = new UserInfo();
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userInfo.setNickName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICKNAME)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
        }
        //关闭资源
        cursor.close();

        //返回数据
        return userInfo;
    }

}
