package com.zhangyan.im.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.zhangyan.im.model.bean.InvationInfo;
import com.zhangyan.im.model.bean.UserInfo;
import com.zhangyan.im.utils.Constant;
import com.zhangyan.im.utils.SpUtils;

/**
 * Created by zhangYan on 2017/8/24.
 * <p>
 * 全局事件监听类
 */

public class EventListener {

    private Context mContext;
    private final LocalBroadcastManager mLocalBroadcastManager;

    public EventListener(Context context) {
        mContext = context;
        //创建一个发送广播的管理者对象
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        //注册一个联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);


    }

    private final EMContactListener emContactListener = new EMContactListener() {
        //联系人增加后执行
        @Override
        public void onContactAdded(String hxid) {
            //数据更新 数据库加入
            Model.getInstance().getDbManger().getContactTableDAO().saveContact(new UserInfo(hxid), true);
            // 发送联系人变化的广播
            mLocalBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_CHANGE));

        }

        //联系人删除后
        @Override
        public void onContactDeleted(String hxid) {
            //从数据库删除
            Model.getInstance().getDbManger().getContactTableDAO().deleteContactByHxId(hxid);
            //删除邀请信息
            Model.getInstance().getDbManger().getInviteTableDAO().removeInvition(hxid);
            // 发送联系人变化的广播
            mLocalBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_CHANGE));
        }

        //联系人接收到新的邀请
        @Override
        public void onContactInvited(String hxid, String reason) {
            //数据库更新
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setUser(new UserInfo(hxid));
            invitationInfo.setReason(reason);
            invitationInfo.setStatus(InvationInfo.InvitationStatus.NEW_INVITE);//新邀请
            Model.getInstance().getDbManger().getInviteTableDAO().addInvitation(invitationInfo);
            //红点处理
            SpUtils.getInstance().sava(SpUtils.IS_NEW_INVITE, true);
            //发送邀请信息变化的广播
            mLocalBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_INVITED_CHANGE));


        }

        //别人同意了你的好友邀请
        @Override
        public void onFriendRequestAccepted(String hxid) {
            //数据库更新
            InvationInfo invationInfo = new InvationInfo();
            invationInfo.setUser(new UserInfo(hxid));
            invationInfo.setStatus(InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);//别人同意了你的邀请
            Model.getInstance().getDbManger().getInviteTableDAO().addInvitation(invationInfo);
            //红点处理
            SpUtils.getInstance().sava(SpUtils.IS_NEW_INVITE,true);
            //发送广播
            mLocalBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_INVITED_CHANGE));
        }

        //别人拒绝了你的好友邀请
        @Override
        public void onFriendRequestDeclined(String s) {
            //红点处理
            SpUtils.getInstance().sava(SpUtils.IS_NEW_INVITE,true);
            //发送广播
            mLocalBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_INVITED_CHANGE));
        }
    };
}
