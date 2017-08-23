package com.zhangyan.im.model.bean;

/**
 * Created by zhangYan on 2017/8/18.
 * 群组信息
 */

public class GroupInfo {

    private String groupName;//群名称
    private String groupId;//群ID
    private String invitePerson;//邀请人

    public GroupInfo() {

    }

    public GroupInfo(String groupName, String groupId, String invatePerso) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.invitePerson = invatePerso;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getInvitePerson() {
        return invitePerson;
    }

    public void setInvitePerson(String invitePerson) {
        this.invitePerson = invitePerson;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
                "groupName='" + groupName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", invitePerson='" + invitePerson + '\'' +
                '}';
    }
}
