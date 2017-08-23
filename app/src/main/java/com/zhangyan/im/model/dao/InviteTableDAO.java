package com.zhangyan.im.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhangyan.im.model.bean.GroupInfo;
import com.zhangyan.im.model.bean.InvationInfo;
import com.zhangyan.im.model.bean.UserInfo;
import com.zhangyan.im.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangYan on 2017/8/22.
 */

public class InviteTableDAO {

    private DBHelper mHelper;

    public InviteTableDAO(DBHelper helper) {
        mHelper = helper;
    }

    //添加邀请
    public void addInvitation(InvationInfo invatationInfo) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(InviteTable.COL_REASON, invatationInfo.getReason());//原因
        values.put(InviteTable.COL_STATUS, invatationInfo.getStatus().ordinal());//状态
        UserInfo userInfo = new UserInfo();

        if (userInfo != null) {//联系人
            values.put(InviteTable.COL_USER_HXID, invatationInfo.getUserInfo().getHxid());
            values.put(InviteTable.COL_USER_NAME, invatationInfo.getUserInfo().getName());
        } else {
            //群组
            values.put(InviteTable.COL_CROUP_HXID, invatationInfo.getGroupInfo().getGroupId());
            values.put(InviteTable.COL_GROUP_NAME, invatationInfo.getGroupInfo().getGroupName());
            values.put(InviteTable.COL_USER_HXID, invatationInfo.getGroupInfo().getInvitePerson());
        }

        db.replace(InviteTable.TAB_NAME, null, values);

    }

    //获取所有邀请信息
    public List<InvationInfo> getInvitions() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sql = " select * from " + InviteTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        List<InvationInfo> invationInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            InvationInfo invationInfo = new InvationInfo();
            invationInfo.setReason(cursor.getString(cursor.getColumnIndex(InviteTable.COL_REASON)));//原因
            invationInfo.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InviteTable.COL_STATUS))));

            String groupId = cursor.getString(cursor.getColumnIndex(InviteTable.COL_CROUP_HXID));
            if (groupId == null) {
                //联系人的邀请信息
                UserInfo userInfo = new UserInfo();
                userInfo.setHxid(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
                userInfo.setName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_NAME)));
                userInfo.setNickName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
            } else {
                //群组
                GroupInfo groupInfo = new GroupInfo();

                groupInfo.setGroupId(cursor.getString(cursor.getColumnIndex(InviteTable.COL_CROUP_HXID)));
                groupInfo.setGroupName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_NAME)));
                groupInfo.setInvitePerson(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
            }
            invationInfos.add(invationInfo);
        }
        cursor.close();
        return invationInfos;

    }

    //将int类型转换为邀请状态
    private InvationInfo.InvitationStatus int2InviteStatus(int intStatus) {

        if (intStatus == InvationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvationInfo.InvitationStatus.INVITE_ACCEPT;
        }

        if (intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        }

        if (intStatus == InvationInfo.InvitationStatus.NEW_GROUP_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_GROUP_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_REJECT_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_REJECT_INVITE;
        }

        return null;

    }

    //删除邀请
    public void removeInvition(String hxid) {
        if (hxid == null) {
            return;
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        db.delete(InviteTable.TAB_NAME, InviteTable.COL_CROUP_HXID + "=?", new String[]{hxid});


    }

    //更新邀请状态
    public void updateInvitation(InvationInfo.InvitationStatus invitationStatus, String hxId) {
        if (hxId == null) {
            return;
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(InviteTable.COL_STATUS, invitationStatus.ordinal());
        db.update(InviteTable.TAB_NAME, values, InviteTable.COL_USER_HXID + "=?", new String[]{hxId});

    }


}
