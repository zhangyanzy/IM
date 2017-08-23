package com.zhangyan.im.controller.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.zhangyan.im.R;
import com.zhangyan.im.controller.activity.AddFriendActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangYan on 2017/8/17.
 */

public class ContactListFragment extends EaseContactListFragment implements View.OnClickListener {

    @BindView(R.id.ll_contact_invite)
    LinearLayout mInvite;
    @BindView(R.id.ll_contact_group)
    LinearLayout mGroup;

    @Override
    protected void initView() {
        super.initView();
        titleBar.setRightImageResource(R.mipmap.em_add);
        View view = View.inflate(getActivity(), R.layout.header_fragment_contact, null);
        listView.addHeaderView(view);
        ButterKnife.bind(this, view);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                startActivity(intent);

            }
        });

    }

    @OnClick(R.id.ll_contact_invite)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_contact_invite:
                Toast.makeText(getActivity(), "Fragment", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }


}
