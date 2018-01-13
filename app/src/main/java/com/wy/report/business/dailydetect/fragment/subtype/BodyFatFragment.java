package com.wy.report.business.dailydetect.fragment.subtype;

import com.wy.report.R;
import com.wy.report.business.dailydetect.fragment.DailyDetectFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BMI;
import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BODY_FAT;

/*
 *
 * @author cantalou
 * @date 2018-01-13 21:49
 */
public class BodyFatFragment extends DailyDetectFragment {

    @Override
    public void saveRecord(DailyDetectDataModel model) {
        dailyDetectService.recordBodyFat(user.getId(), DETECT_TYPE_BODY_FAT, getValue(0));
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.daily_detect_type_body_fat);
    }
}
