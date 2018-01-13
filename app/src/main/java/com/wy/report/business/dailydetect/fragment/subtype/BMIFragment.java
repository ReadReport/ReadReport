package com.wy.report.business.dailydetect.fragment.subtype;

import com.wy.report.R;
import com.wy.report.business.dailydetect.fragment.DailyDetectFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BMI;

/*
 *
 * @author cantalou
 * @date 2018-01-13 21:49
 */
public class BMIFragment extends DailyDetectFragment {

    @Override
    public void saveRecord(DailyDetectDataModel model) {
        dailyDetectService.recordBMI(user.getId(), DETECT_TYPE_BMI, getValue(0), getValue(1));
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.daily_detect_type_bmi);
    }
}
