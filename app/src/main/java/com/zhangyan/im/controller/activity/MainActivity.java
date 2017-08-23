package com.zhangyan.im.controller.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zhangyan.im.R;
import com.zhangyan.im.controller.fragment.ChatFragment;
import com.zhangyan.im.controller.fragment.ContactListFragment;
import com.zhangyan.im.controller.fragment.SettingFragment;
import com.zhangyan.im.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;



    private ChatFragment mChatFragment;
    private ContactListFragment mContactListFragment;
    private SettingFragment mSettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPresenter(new Presenter());
        binding.rgMain.check(R.id.rb_main_chat);
    }

    @Override
    protected void initData() {
        mChatFragment = new ChatFragment();
        mContactListFragment = new ContactListFragment();
        mSettingFragment = new SettingFragment();
    }


    public class Presenter  {

        public void onClick(View view) {

        }

        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

            Fragment fragment = null;
            switch (i) {
                case R.id.rb_main_chat:
                    fragment = mChatFragment;
                    break;
                case R.id.rb_main_contact:
                    fragment = mContactListFragment;
                    break;
                case R.id.rb_main_setting:
                    fragment = mSettingFragment;
                    break;
                default:
                    break;
            }
            //实现Fragment切换方法
            switchFragment(fragment);
        }

    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main, fragment).commit();
    }


}
