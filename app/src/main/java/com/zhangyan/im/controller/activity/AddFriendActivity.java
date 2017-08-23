package com.zhangyan.im.controller.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zhangyan.im.R;
import com.zhangyan.im.databinding.ActivityAddFriendBinding;
import com.zhangyan.im.model.Model;
import com.zhangyan.im.model.bean.UserInfo;

/**
 * 添加好友Activity
 */

public class AddFriendActivity extends BaseActivity {

    private ActivityAddFriendBinding binding;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_friend);
        binding.setPresenter(new Presenter());
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.search_contact_activity:
                    find();
                    break;
                case R.id.add_add_activity:
                    add();
                    break;
                default:
                    break;

            }
        }
    }

    private void add() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //去环信服务器添加好友
                try {
                    EMClient.getInstance().contactManager().addContact(mUserInfo.getName(), "添加好友");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "发送添加好友成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void find() {
        final String userName = binding.etUsernameAddActivity.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "不能为null", Toast.LENGTH_SHORT).show();
            return;
        }
        //去服务器去判断当前用户是否存在
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                mUserInfo = new UserInfo();
                //
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.searchContactItem.setVisibility(View.VISIBLE);
                        binding.usernameAddActivity.setText(mUserInfo.getName());
                    }
                });
            }
        });
    }
}
