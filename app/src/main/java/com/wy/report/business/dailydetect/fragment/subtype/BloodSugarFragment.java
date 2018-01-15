package com.wy.report.business.dailydetect.fragment.subtype;

import android.view.View;

import com.wy.report.R;
import com.wy.report.business.dailydetect.fragment.DailyDetectFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.dailydetect.service.DailyDetectService;
import com.wy.report.business.home.model.DailyDetectTypeModel;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BLOOD_SUGAR;

/**
 * @author cantalou
 * @date 2018年01月04日 10:29
 * <p>
 */
public class BloodSugarFragment extends DailyDetectFragment {

    @Override
    public void saveRecord(View view) {
        dailyDetectService.recordBloodSugar(user.getId(), DETECT_TYPE_BLOOD_SUGAR, "", getValue(0), getValue(1) + "." + getValue(2));
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.daily_detect_type_blood_sugar);
    }
}
