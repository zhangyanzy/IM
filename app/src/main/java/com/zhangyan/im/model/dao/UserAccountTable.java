package com.zhangyan.im.model.dao;

/**
 * Created by zhangYan on 2017/8/16.
 */

public class UserAccountTable {
    //表名
    public static final String TAB_NAME = "tab_account";
    //col
    public static final String COL_NAME = "name";
    public static final String COL_HXID = "hxid";
    public static final String COL_NICKNAME = "nickName";
    public static final String COL_PHOTO = "photo";

    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + COL_HXID + " text primary key,"
            + COL_NAME + " text,"
            + COL_NICKNAME + " text,"
            + COL_PHOTO + " text);";

}
