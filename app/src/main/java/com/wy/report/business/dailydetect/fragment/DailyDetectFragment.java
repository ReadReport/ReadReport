package com.wy.report.business.dailydetect.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.business.dailydetect.service.DailyDetectService;
import com.wy.report.business.home.model.DailyDetectTypeModel;
import com.wy.report.helper.dailydetect.DailyDetectHelper;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.widget.view.dailydetect.DailyDetectValueContainerView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *
 * 日常检测
 * @author cantalou
 * @date 2017-12-26 21:14
 */
public class DailyDetectFragment extends ToolbarFragment implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.daily_detect_value_container)
    FrameLayout detectValueContainer;

    @BindView(R.id.daily_detect_date_value)
    TextView dailyDetectDate;

    @BindView(R.id.daily_detect_time_value)
    TextView dailyDetectTime;

    @BindView(R.id.daily_detect_data_list_operate)
    TextView dataListOperateMode;

    private boolean editMode = false;

    private DailyDetectTypeModel model;

    private DailyDetectService dailyDetectService;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        model = getArguments().getParcelable(BundleKey.BUNDLE_KEY_MODEL);
        dailyDetectService = RetrofitHelper.getInstance()
                                           .create(DailyDetectService.class);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        View detectValueView = createDetectValueView();
        detectValueContainer.addView(detectValueView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    protected View createDetectValueView() {
        DailyDetectValueContainerView containerView = new DailyDetectValueContainerView(getActivity());
        containerView.setData(DailyDetectHelper.getTypes(model));
        return containerView;
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_daily_detect;
    }

    @Override
    protected int getMenuLayoutId() {
        return R.menu.menu_find_daily_detect;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.find_daily_detect_more: {
                break;
            }
        }
        return false;
    }

    @OnClick({R.id.daily_detect_date_title, R.id.daily_detect_date_value})
    public void detectDateClick() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dailyDetectDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick({R.id.daily_detect_time_value, R.id.daily_detect_time_icon})
    public void detectTimeClick() {
        Calendar cal = Calendar.getInstance();
        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dailyDetectTime.setText(hourOfDay + "：" + minute);
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
    }

    @OnClick(R.id.daily_detect_data_list_operate)
    public void dataListOperateClick() {
        editMode = !editMode;
        dataListOperateMode.setText(editMode ? R.string.done : R.string.edit);
        rxBus.post(RxKey.RX_DAILY_DETECT_DATA_OPERATE, Boolean.valueOf(editMode));
    }

    @OnClick(R.id.daily_detect_save)
    public void saveRecord() {

    }
}
