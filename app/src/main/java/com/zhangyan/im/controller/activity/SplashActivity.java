package com.zhangyan.im.controller.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.zhangyan.im.R;
import com.zhangyan.im.model.Model;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //如果当前Activity已经退出，那就不处理handler中的消息
            if (isFinishing()) {
                return;
            }
            //判断进入主页面还是登录页面
            toMainOrLogin();
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置3秒自动跳转
        handler.sendMessageDelayed(Message.obtain(), 3000);
    }

    private void toMainOrLogin() {
        /**
         * 使用全局线程池进行优化，减少内存泄露
         */
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //要获取当前用户的登录信息，存储在数据库中
                //hTODO 获取当前用户登录信息

                //通过服务器判断是否已经登录过，由于没有服务器，就从环信服务器进行判断
                if (EMClient.getInstance().isLoggedInBefore()) {
                    //登录过
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //没有登录过
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
