package com.wy.report.business.dailydetect.fragment.data;

import android.support.annotation.NonNull;
import android.view.View;

import com.wy.report.R;
import com.wy.report.business.dailydetect.model.DailyDetectValueModel;

/**
 * @author cantalou
 * @date 2018年01月17日 11:05
 */
public class BMIDataFragment extends DailyDetectDataListFragment {

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        titleType.setText(R.string.daily_detect_data_list_title_bmi);
    }

    @NonNull
    @Override
    protected String createShowValue(DailyDetectValueModel valueModel) {
        return valueModel.getWeightValue() + "/" + valueModel.getHightValue();
    }
}