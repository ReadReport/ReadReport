package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
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
public class EditUserInfoFragment extends NetworkFragment {

    @BindView(R.id.edit_user_info_name)
    EditText userName;
    private MyService mMyService;

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        setTitle(getResources().getString(R.string.user_info_name));
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
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mMyService = RetrofitHelper.getInstance().create(MyService.class);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_edit_user_info;
    }

    public void save() {
        if (StringUtils.isNotBlank(userName)) {
            saveAll();
        } else {
            ToastUtils.showLong(R.string.user_info_edit_null_tip);
        }
    }

    private void saveAll() {
        User         user        = UserManger.getInstance().getLoginUser();
        final String newName     = userName.getText().toString();
        final long   newBirthday = user.getBirthday();
        final int    sexy        = user.getSex();
        final String uid         = UserManger.getInstance().getLoginUser().getId();

        int nameLength = StringUtils.length(newName);
        if (nameLength < 4 || nameLength > 16) {
            ToastUtils.showLong(getString(R.string.user_info_edit_tip));
            return;
        }
        mMyService.editInfo(uid, newName, String.valueOf(newBirthday), String.valueOf(sexy)).subscribe(new NetworkSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel responseModel) {
                super.onNext(responseModel);
                ToastUtils.showLong(R.string.user_info_success_tips);
                User user = UserManger.getInstance().getLoginUser();
                user.setName(newName);
                user.setSex(sexy);
                user.setBirthday(newBirthday);
                UserManger.getInstance().updateUser(user);
                rxBus.post(RxKey.RX_MODIFY_USER_INFO, user);
                getActivity().finish();
            }
        });
    }
}
