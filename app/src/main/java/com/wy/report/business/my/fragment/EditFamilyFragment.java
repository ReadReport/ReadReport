package com.wy.report.business.my.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.AddMemberMode;
import com.wy.report.business.my.model.FamilyItemMode;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.util.RegexUtils;
import com.wy.report.util.StringUtils;
import com.wy.report.util.TimeUtils;
import com.wy.report.util.ToastUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wy.report.util.TimeUtils.DATE_FORMAT;

/**
 * 编辑家庭成员
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class EditFamilyFragment extends NetworkFragment {


    private MyService      myService;
    private String         uid;
    private FamilyItemMode familyItem;
    private static final String TIME_SPLIT = "-";

    @BindView(R.id.edit_family_name)
    EditText name;
    @BindView(R.id.edit_family_birthday)
    EditText birthday;
    @BindView(R.id.edit_family_sex)
    EditText sex;
    @BindView(R.id.edit_family_relationship)
    EditText relationship;
    @BindView(R.id.edit_family_phone)
    EditText phone;
    @BindView(R.id.edit_family_id_card)
    EditText idCard;


    private String[] relationshipData;
    private String[] sexualData;

    private boolean editMode;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            familyItem = intent.getParcelableExtra(BundleKey.BUNDLE_KEY_FAMILY_ITEM);
            editMode = familyItem != null;
        }
        if (!UserManger.getInstance().isLogin()) {
            getActivity().finish();
        }
        uid = String.valueOf(UserManger.getInstance().getLoginUser().getId());
        relationshipData = getResources().getStringArray(R.array.relationship);
        sexualData = getResources().getStringArray(R.array.sex);
    }


    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_edit_family;
    }


    @Override
    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        setTitle(editMode ? getString(R.string.edit_family_title) : getString(R.string.add_family_title));
        name.setOnFocusChangeListener(new FocusListener());
        phone.setOnFocusChangeListener(new FocusListener());
        idCard.setOnFocusChangeListener(new FocusListener());
        updateFamilyInfo();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbarMenu.setText(getString(R.string.edit_family_save));
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    /**
     * 填充信息
     */
    private void updateFamilyInfo() {
        if (familyItem != null) {
            name.setText(familyItem.getName());
            birthday.setText(TimeUtils.millis2String(Long.valueOf(familyItem.getBirthday()), DATE_FORMAT));
            sex.setText(StringUtils.getSex2Show(familyItem.getSex()));
            relationship.setText(familyItem.getRelationship());
            phone.setText(familyItem.getMobile());
            idCard.setText(familyItem.getIdCard());
        }
    }


    @OnClick(R.id.edit_family_relationship_line)
    public void onClickRelationship() {
        new AlertDialog.Builder(getActivity()).setItems(relationshipData, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                relationship.setText(relationshipData[i]);
            }
        }).show();
    }


    @OnClick(R.id.edit_family_sex_line)
    public void onClickSex() {
        new AlertDialog.Builder(getActivity()).setItems(sexualData, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sex.setText(sexualData[i]);
            }
        }).show();
    }

    @OnClick(R.id.edit_family_birthday_line)
    public void onClickBirthday() {
        final Calendar calendar = Calendar.getInstance();
        int            year     = calendar.get(Calendar.YEAR);
        int            month    = calendar.get(Calendar.MONTH);
        int            day      = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String  time     = year + TIME_SPLIT + (monthOfYear + 1) + TIME_SPLIT + dayOfMonth;
                long    millis   = TimeUtils.string2Millis(time, DATE_FORMAT);
                boolean isBefore = TimeUtils.isBeforeNow(millis);
                if (isBefore) {
                    birthday.setText(time);
                } else {
                    ToastUtils.showLong("日期不能晚于当前时间");
                }
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private class FocusListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean b) {
            //            EditText editText = (EditText) view;
            //            int      gravity  = b ? Gravity.LEFT : Gravity.RIGHT;
            //            editText.setGravity(gravity | Gravity.CENTER_VERTICAL);
        }
    }

    private void save() {
        final String newName         = name.getText().toString();
        final String newBirthday     = string2millis(birthday.getText().toString());
        final String newRelationship = relationship.getText().toString();
        final String newPhone        = phone.getText().toString();
        final String newSex          = StringUtils.getSex2Upload(sex.getText().toString());
        final String newIdCard       = idCard.getText().toString();

        if (StringUtils.isBlank(name)) {
            ToastUtils.showLong("姓名不能为空");
            return;
        }

        int nameLength = StringUtils.length(newName);
        if (nameLength < 4 || nameLength > 16) {
            ToastUtils.showLong(getString(R.string.user_info_edit_tip));
            return;
        }

        if (StringUtils.isBlank(birthday)) {
            ToastUtils.showLong("生日不能为空");
            return;
        }
        if (StringUtils.isBlank(relationship)) {
            ToastUtils.showLong("关系不能为空");
            return;
        }
        if (StringUtils.isBlank(phone)) {
            ToastUtils.showLong("电话不能为空");
            return;
        }
        if (!RegexUtils.isMobileSimple(phone.getText().toString())) {
            ToastUtils.showLong("电话号码格式不对");
            return;
        }
        if (StringUtils.isBlank(sex)) {
            ToastUtils.showLong("性别不能为空");
            return;
        }

        if (StringUtils.isBlank(idCard)) {
            ToastUtils.showLong("身份证不能为空");
            return;
        }

        if (!RegexUtils.isIDCard18(idCard.getText().toString()) && !RegexUtils.isIDCard15(idCard.getText().toString())) {
            ToastUtils.showLong("身份证号格式不对");
            return;
        }




        if (editMode) {
            myService.editFamilyMember(familyItem.getId(), newName, newSex, newBirthday, newRelationship, newPhone, newIdCard).subscribe(new NetworkSubscriber<ResponseModel>(this) {
                @Override
                public void onNext(ResponseModel responseModel) {
                    super.onNext(responseModel);
                    familyItem.setName(newName);
                    familyItem.setBirthday(newBirthday);
                    familyItem.setMobile(newPhone);
                    familyItem.setSex((newSex));
                    familyItem.setIdCard(newIdCard);
                    familyItem.setRelationship(newRelationship);

                    rxBus.post(RxKey.RX_FAMILY_EDIT, familyItem);
                    getActivity().finish();
                }
            });
        } else {
            myService.addFamilyMember(uid, newName, newSex, newBirthday, newRelationship, newPhone, newIdCard).subscribe(new NetworkSubscriber<ResponseModel<AddMemberMode>>(this) {
                @Override
                public void onNext(ResponseModel<AddMemberMode> responseModel) {
                    super.onNext(responseModel);
                    familyItem = new FamilyItemMode();
                    familyItem.setId(responseModel.getData().getId());
                    familyItem.setName(newName);
                    familyItem.setBirthday(newBirthday);
                    familyItem.setMobile(newPhone);
                    familyItem.setSex((newSex));
                    familyItem.setIdCard(newIdCard);
                    familyItem.setRelationship(newRelationship);
                    rxBus.post(RxKey.RX_FAMILY_EDIT_ADD, familyItem);
                    getActivity().finish();
                }
            });
        }

    }


    private String string2millis(String dateString) {
        return String.valueOf(TimeUtils.string2Millis(dateString, DATE_FORMAT));
    }


}
