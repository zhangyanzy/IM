package com.zhangyan.im.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhangyan.im.model.bean.UserInfo;
import com.zhangyan.im.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangYan on 2017/8/18.
 * 联系人表的操作类
 */

public class ContactTableDAO {

    private DBHelper mHelp;

    public ContactTableDAO(DBHelper helper) {
        mHelp = helper;
    }

    /**
     * 增
     */
    //获取所有联系人
    public List<UserInfo> getContacts() {
        //获取数据库连接
        SQLiteDatabase db = mHelp.getReadableDatabase();
        //执行查询语句
        String sql = "select * from " + ContactTable.TAB_NAME + " where " + ContactTable.COL_IS_CONTACT + "=1";
        Cursor cursor = db.rawQuery(sql, null);

        List<UserInfo> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));
            userInfo.setNickName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NIKE)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));

            users.add(userInfo);
        }
        //关闭资源
        //返回数据0
        cursor.close();
        return users;

    }

    //通过环信ID获取单个联系人的信息
    public UserInfo getContactByHx(String hxid) {
        if (hxid == null) {
            return null;
        }
        SQLiteDatabase db = mHelp.getReadableDatabase();

        String sql = "select * from " + ContactTable.TAB_NAME + " where " + ContactTable.COL_HXID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxid});
        UserInfo userInfo = null;
        if (cursor.moveToNext()) {
            userInfo = new UserInfo();

            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));
            userInfo.setNickName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NIKE)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
        }
        cursor.close();
        return userInfo;
    }

    //通过环信ID获取用户联系人的信息
    public List<UserInfo> getContactsByHx(List<String> hxids) {
        if (hxids == null || hxids.size() <= 0) {
            return null;
        }
//        SQLiteDatabase db = mHelp.getReadableDatabase();
        //遍历环信ID的集合
        List<UserInfo> contacts = new ArrayList<>();
        for (String hxid : hxids) {
            UserInfo contact = getContactByHx(hxid);
            contacts.add(contact);
        }
        return contacts;
    }

    /**
     * 查
     */

    //保存单个联系人
    public void saveContact(UserInfo user, boolean isMyContact) {
        if (user == null) {
            return;
        }
        SQLiteDatabase db = mHelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactTable.COL_HXID, user.getHxid());
        values.put(ContactTable.COL_NAME, user.getName());
        values.put(ContactTable.COL_NIKE, user.getNickName());
        values.put(ContactTable.COL_PHOTO, user.getPhoto());
        values.put(ContactTable.COL_IS_CONTACT, isMyContact ? 1 : 0);
        db.replace(ContactTable.COL_NAME, null, values);
    }

    //保存联系人信息
    public void saveContact(List<UserInfo> contacts, boolean isMyContact) {
        if (contacts == null || contacts.size() <= 0) {
            return;
        }
        for (UserInfo contact : contacts) {
            saveContact(contact, isMyContact);
        }
    }

    /**
     * 删
     */

    //删除联系人信息
    public void deleteContactByHxId(String hxId) {
        if (hxId == null) {
            return;
        }
        SQLiteDatabase db = mHelp.getReadableDatabase();
        db.delete(ContactTable.TAB_NAME, ContactTable.COL_HXID + "=?", new String[]{hxId});
    }

}
