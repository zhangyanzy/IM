package com.zhangyan.im.controller.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zhangYan on 2017/8/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        initView();
        initData();
        initEvent();
    }

    protected void initEvent() {

    }

    protected void initData() {

    }

    protected void initView() {

    }
}
