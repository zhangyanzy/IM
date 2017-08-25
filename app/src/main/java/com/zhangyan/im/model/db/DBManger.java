package com.zhangyan.im.model.db;

import android.content.Context;

import com.zhangyan.im.model.dao.ContactTableDAO;
import com.zhangyan.im.model.dao.InviteTableDAO;

/**
 * Created by zhangYan on 2017/8/23.
 * 联系人和邀请信息表的操作类的管理类
 */

public class DBManger {

    private DBHelper dbHelper;
    private ContactTableDAO contactTableDAO;
    private InviteTableDAO inviteTableDAO;

    public DBManger(Context context, String name) {
        //创建数据库
        dbHelper = new DBHelper(context, name);

        //创建该数据库中两张表的操作类

        contactTableDAO = new ContactTableDAO(dbHelper);
        inviteTableDAO = new InviteTableDAO(dbHelper);

    }

    //获取联系人表的操作类对象
    public ContactTableDAO getContactTableDAO() {
        return contactTableDAO;
    }

    //获取邀请人信息表的操作类
    public InviteTableDAO getInviteTableDAO() {
        return inviteTableDAO;
    }

    //关闭数据库
    public void close() {
        dbHelper.close();
    }
}
