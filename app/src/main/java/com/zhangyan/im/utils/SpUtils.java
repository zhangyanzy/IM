package com.zhangyan.im.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhangyan.im.IMApplication;

/**
 * Created by zhangYan on 2017/8/24.
 * SharedPreferences的封装
 * 1.保存
 * 2.获取数据
 */

public class SpUtils {

    public static final String IS_NEW_INVITE = "is_new_invite";
    //3创建一个实例对象
    private static SpUtils instance = new SpUtils();
    private static SharedPreferences mSp;

    /**
     * 封装单例
     * 1、静态构造方法
     * 2、
     */
//1、静态构造方法
    private SpUtils() {

    }

    //2、通过静态方法创建一个实例，返回实例，获取实例对象
    public static SpUtils getInstance() {
        //4. 获取SP对象  通过上下文 从Appliaction中获取
        if (mSp == null) {
            mSp = IMApplication.getGlobalApplication().getSharedPreferences("im", Context.MODE_PRIVATE);
        }

        return instance;

    }

    //保存
    public void sava(String key, Object value) {
        //如果value是String类型的
        if (value instanceof String) {
            mSp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Integer){
            mSp.edit().putInt(key, (Integer) value).commit();
        }
    }

    //获取数据(String)
    public String getString(String key,String defValue){
        return mSp.getString(key, defValue);
    }
    //获取数据(boolean)
    public boolean getBoolean(String key,boolean defValue){
        return mSp.getBoolean(key,defValue);
    }
    //获取数据(int)
    public int getInt(String key,int defValue){
        return mSp.getInt(key, defValue);

    }





}
