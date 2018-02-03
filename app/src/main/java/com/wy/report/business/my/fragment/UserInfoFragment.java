package com.wy.report.business.my.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.StringUtils;
import com.wy.report.util.TimeUtils;
import com.wy.report.util.ToastUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的信息
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class UserInfoFragment extends NetworkFragment {


    private static final String TIME_SPLIT = "-";

    private MyService mMyService;

    @BindView(R.id.user_info_header)
    RoundedImageView header;

    @BindView(R.id.user_info_name)
    TextView name;

    @BindView(R.id.user_info_birthday)
    TextView birthday;

    @BindView(R.id.user_info_sex)
    TextView sex;

    private User user;

    private String[] sexualData;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user = UserManger.getInstance().getLoginUser();
        sexualData = getResources().getStringArray(R.array.sex);
        mMyService = RetrofitHelper.getInstance().create(MyService.class);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        updateInfo();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(getResources().getString(R.string.user_info_title));
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_user_info;
    }


    private void updateInfo() {
        if (user != null) {
            name.setText(user.getName());
            birthday.setText(TimeUtils.millis2StringWithoutTime(user.getBirthday()));
            sex.setText(StringUtils.getSex2Show(user.getSex()));
            Glide.with(getActivity()).load(user.getHead()).into(header);
        }
    }


    /**
     * 修改名字
     */
    @OnClick(R.id.user_info_user_name_info)
    public void modifyName() {
        AuthRouterManager.getInstance().getRouter().open(getActivity(),AuthRouterManager.ROUTER_EDIT_USERNAME);
    }

    /**
     * 修改生日
     */
    @OnClick(R.id.user_info_birthday_info)
    public void modifyBirthday() {
        final Calendar calendar = Calendar.getInstance();
        int            year     = calendar.get(Calendar.YEAR);
        int            month    = calendar.get(Calendar.MONTH);
        int            day      = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String time = year + TIME_SPLIT + (monthOfYear + 1) + TIME_SPLIT + dayOfMonth;
                birthday.setText(time);
                save();
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * 修改性别
     */
    @OnClick(R.id.user_info_sex_info)
    public void modifySex() {
        new AlertDialog.Builder(getActivity()).setItems(sexualData, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sex.setText(sexualData[i]);
                save();
            }
        }).show();
    }

    private void save()
    {
        final String newName = name.getText().toString();
        final String newBirthday = birthday.getText().toString();
        final int sexy = StringUtils.getSex2UploadInt(sex.getText().toString());
        final String uid = UserManger.getInstance().getLoginUser().getId();
        mMyService.editInfo(uid,newName,newBirthday,String.valueOf(sexy)).subscribe(new NetworkSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel responseModel) {
                super.onNext(responseModel);
                ToastUtils.showLong(R.string.user_info_success_tips);
                User user = UserManger.getInstance().getLoginUser();
                user.setName(newName);
                user.setSex(sexy);
                user.setBirthday(TimeUtils.string2Millis(newBirthday,TimeUtils.DATE_FORMAT));
                UserManger.getInstance().updateUser(user);
                rxBus.post(RxKey.RX_MODIFY_USER_INFO,user);
            }
        });
    }

    @Subscribe(tags = {@Tag(RxKey.RX_EDIT_USER_NAME)})
    public void onNameEdit(String newName)
    {
        name.setText(newName);
        save();
    }



}
