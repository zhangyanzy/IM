package com.zhangyan.im.controller.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zhangyan.im.R;
import com.zhangyan.im.databinding.ActivityLoginBinding;
import com.zhangyan.im.model.Model;
import com.zhangyan.im.model.bean.UserInfo;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setPresenter(new Presenter());
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.regist_btn:
                    register();
                    break;
                case R.id.login_btn:
                    login();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 注册
     */
    private void register() {
        //获取用户输入的用户名和密码
        final String registerName = binding.userNameEdit.getText().toString();
        final String registerPaw = binding.userPawEdit.getText().toString();
        //校验输入的用户名和密码
        if (TextUtils.isEmpty(registerName) || TextUtils.isEmpty(registerPaw)) {
            Toast.makeText(getApplicationContext(), "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //提交到服务器注册（自己的服务器or环信的服务器）
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(registerName, registerPaw);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "注册失败" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        final String loginName = binding.userNameEdit.getText().toString();
        final String loginPaw = binding.userPawEdit.getText().toString();
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPaw)) {
            Toast.makeText(getApplicationContext(), "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //登录逻辑处理
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //登录环信服务器
                EMClient.getInstance().login(loginName, loginPaw, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //模型层数据的处理
                        Model.getInstance().loginSuccess(new UserInfo(loginName));
                        //保存用户账号信息到本地数据库
                        Model.getInstance().getUserAccountDao().addAccount(new UserInfo(loginName));
                        //Toast提示
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //跳转
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                        //Toast提示失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });

    }

}
