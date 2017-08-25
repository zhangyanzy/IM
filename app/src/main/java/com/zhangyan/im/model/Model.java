package com.zhangyan.im.model;

import android.content.Context;

import com.zhangyan.im.model.bean.UserInfo;
import com.zhangyan.im.model.dao.UserAccountDao;
import com.zhangyan.im.model.db.DBManger;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangYan on 2017/8/16.
 * 数据模型层，全局类，处理全局数据
 */

public class Model {
    private Context mContext;
    //创建线程池对象
    private ExecutorService executors = Executors.newCachedThreadPool();

    private UserAccountDao userAccountDao;

    private DBManger dbManger;

    //创建对象
    private static Model model = new Model();

    //私有化构造
    private Model() {

    }

    //获取单例对象
    public static Model getInstance() {
        return model;
    }


    //初始化的方法
    public void init(Context context) {
        mContext = context;
        //创建用户账号数据操的操作类对象
        userAccountDao = new UserAccountDao(mContext);
        //开启全局监听
        new EventListener(mContext);

    }

    /**
     * 获取全局线程池对象
     */
    public ExecutorService getGlobalThreadPool() {
        return executors;
    }

    /**
     * 用户登录成功后的处理方法
     */
    public void loginSuccess(UserInfo account) {
        if (account == null) {
            return;
        }
        if (dbManger != null) {
            dbManger.close();
        }
        dbManger = new DBManger(mContext, account.getName());

    }

    public DBManger getDbManger() {
        return dbManger;
    }

    /**
     * 获取用户账号数据的操作类对象
     */
    public UserAccountDao getUserAccountDao() {
        return userAccountDao;
    }


}
