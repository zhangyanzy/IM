package com.zhangyan.im.model.bean;

/**
 * Created by zhangYan on 2017/8/16.
 * 用户信息类
 */

public class UserInfo {

    private String name;//用户名称
    private String hxid;//环信服务器中唯一标识
    private String nickName;//用户昵称
    private String photo;//用户头像

    public UserInfo() {
    }

    public UserInfo(String name) {
        this.name = name;
        this.hxid = name;
        this.nickName = name;
//        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", hxid='" + hxid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
