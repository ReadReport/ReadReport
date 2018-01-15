package com.wy.report.business.dailydetect.fragment.subtype;

import android.view.View;

import com.wy.report.R;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.fragment.DailyDetectFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.util.ToastUtils;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BLOOD_PRESSURE;

/**
 * @author cantalou
 * @date 2018年01月04日 10:29
 * <p>
 */
public class BloodPressureFragment extends DailyDetectFragment {

    @Override
    public void saveRecord(View view) {
        dailyDetectService.recordBloodPressure(user.getId(), DETECT_TYPE_BLOOD_PRESSURE, getValue(0), getValue(1), getValue(2))
                          .subscribe(new NetworkSubscriber<ResponseModel>(this) {
                              @Override
                              public void handleSuccess(ResponseModel responseModel) {
                                  super.handleSuccess(responseModel);
                              }
                          });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.daily_detect_type_blood_pressure);
    }
}
