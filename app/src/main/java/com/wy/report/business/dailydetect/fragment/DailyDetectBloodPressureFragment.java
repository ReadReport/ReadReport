package com.wy.report.business.dailydetect.fragment;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BLOOD_PRESSURE;

/**
 * @author cantalou
 * @date 2018年01月04日 10:29
 * <p>
 */
public class DailyDetectBloodPressureFragment extends DailyDetectFragment {

    @Override
    public void saveRecord() {
        dailyDetectService.recordBloodPressure(user.getId(), DETECT_TYPE_BLOOD_PRESSURE, getValue(0), getValue(1), getValue(2));
    }

}
