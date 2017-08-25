package com.zhangyan.im;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.zhangyan.im.model.Model;

/**
 * Created by zhangYan on 2017/8/16.
 */

public class IMApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化环信EaseUI
        EMOptions options = new EMOptions();
        //设置需要同意后才能接受邀请
        options.setAcceptInvitationAlways(false);
        //设置需要同意后才能接受邀请
        options.setAutoAcceptGroupInvitation(false);
        EaseUI.getInstance().init(this, options);

        //初始化数据模型层  Model
        Model.getInstance().init(this);
        //初始化全局上下文
        mContext = this;
    }

    //全局上下文对象
    public static Context getGlobalApplication() {
        return mContext;
    }
}
