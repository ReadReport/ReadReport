package com.wy.report.business.my.fragment;

import android.view.View;
import android.widget.EditText;

import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.util.StringUtils;
import com.wy.report.util.ToastUtils;

import butterknife.BindView;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-12 下午6:35
 * @description: ReadReport
 */
public class EditUserInfoFragment extends ToolbarFragment {

    @BindView(R.id.edit_user_info_name)
    EditText userName;

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        setTitle(getResources().getString(R.string.user_info_title));
        toolbarMenu.setText(getString(R.string.edit_family_save));
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        userName.setText(UserManger.getInstance().getLoginUser().getName());
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_edit_user_info;
    }

    public void save() {
        if (StringUtils.isNotBlank(userName)) {
            rxBus.post(RxKey.RX_EDIT_USER_NAME, userName.getText().toString());
            getActivity().finish();
        } else {
            ToastUtils.showLong(R.string.user_info_edit_null_tip);
        }
    }
}
