package com.wy.report.business.dailydetect.fragment.data;

import android.support.annotation.NonNull;
import android.view.View;

import com.wy.report.R;

/**
 * @author cantalou
 * @date 2018年01月17日 11:05
 */
public class BodyFatDataFragment extends DailyDetectDataListFragment {
    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        titleType.setText(R.string.daily_detect_data_list_title_body_fat);
    }

    @NonNull
    @Override
    protected String createShowValue(String[] values) {
        return values[0];
    }
}
