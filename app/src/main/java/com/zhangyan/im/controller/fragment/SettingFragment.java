package com.zhangyan.im.controller.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zhangyan.im.R;
import com.zhangyan.im.controller.activity.LoginActivity;
import com.zhangyan.im.databinding.FragmentSettingBinding;
import com.zhangyan.im.model.Model;

/**
 * Created by zhangYan on 2017/8/17.
 */

public class SettingFragment extends BaseFragment {

    private FragmentSettingBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        binding.setPresenter(new Presenter());
        return binding.getRoot();
    }

    @Override
    protected View initView() {
        return null;
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_setting_logout:
                    logout();
                    break;
                default:
                    break;
            }
        }

    }

    private void logout() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //登录环信服务器进行退出
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //关闭DBhelp
                        Model.getInstance().getDbManger().close();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //回到登录页面
                                Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });


                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });

    }

    @Override
    protected void initData() {
        binding.btnSettingLogout.setText("退出登录(" + EMClient.getInstance().getCurrentUser() + ")");
    }
}
