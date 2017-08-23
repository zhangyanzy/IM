package com.zhangyan.im.model.dao;

/**
 * Created by zhangYan on 2017/8/18.
 * 联系人建表语句
 */
public class ContactTable {

    public static final String TAB_NAME = "tab_contact";
    //联系人的表存放哪些信息
    public static final String COL_HXID = "hxid";
    public static final String COL_NAME = "name";
    public static final String COL_NIKE = "nike";
    public static final String COL_PHOTO = "photo";
    //是否是联系人
    public static final String COL_IS_CONTACT = "is_contact";

    public static final String CREATE_TAB = "create table" + TAB_NAME + "("
            + COL_HXID + " text primary Key,"
            + COL_NAME + " text,"
            + COL_NIKE + " text,"
            + COL_PHOTO + "text,"
            + COL_IS_CONTACT + " integer);";

}
