package com.wy.report.business.dailydetect.fragment.type;

import android.view.View;
import android.view.ViewGroup;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.fragment.DailyDetectFragment;
import com.wy.report.business.dailydetect.fragment.data.BloodPressureDataFragment;
import com.wy.report.business.dailydetect.fragment.tendency.DailyDetectTendencyCharFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueViewContainer;

import java.util.ArrayList;
import java.util.List;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BLOOD_PRESSURE;

/**
 * @author cantalou
 * @date 2018年01月04日 10:29
 * <p>
 */
public class BloodPressureFragment extends DailyDetectFragment {

    @Override
    public void saveRecord(final View view) {
        dailyDetectService.recordBloodPressure(user.getId(), DETECT_TYPE_BLOOD_PRESSURE, getValue(0), getValue(1), getValue(2))
                          .subscribe(new NetworkSubscriber<ResponseModel>(this) {
                              @Override
                              public void handleSuccess(ResponseModel responseModel) {
                                  super.handleSuccess(responseModel);
                                  BloodPressureFragment.super.saveRecord(view);
                              }
                          });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.daily_detect_type_blood_pressure);
    }

    @Override
    protected BaseFragment[] getFragments() {
        return new BaseFragment[]{new DailyDetectTendencyCharFragment(), new BloodPressureDataFragment()};
    }


    @Override
    protected ViewGroup createDetectValueView() {

        List<ValueType> result = new ArrayList<>();

        ValueType high = new ValueType.Builder().name("高压")
                                                .unit("mmhg")
                                                .start(30)
                                                .end(240)
                                                .startIndex(50)
                                                .create();
        result.add(high);

        ValueType low = new ValueType.Builder().name("低压")
                                               .unit("mmhg")
                                               .start(30)
                                               .startIndex(30)
                                               .end(240)
                                               .create();
        result.add(low);

        ValueType bmp = new ValueType.Builder().name("心率")
                                               .unit("bmp")
                                               .start(30)
                                               .startIndex(50)
                                               .end(220)
                                               .create();
        result.add(bmp);

        high.setMinValue(low);
        low.setMaxValue(low);

        ValueViewContainer detectValueContainerView = new ValueViewContainer(getActivity());
        detectValueContainerView.setData(result);
        return detectValueContainerView;
    }

}
