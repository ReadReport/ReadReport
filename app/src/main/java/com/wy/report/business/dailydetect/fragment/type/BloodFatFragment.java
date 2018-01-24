package com.wy.report.business.dailydetect.fragment.type;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.fragment.DailyDetectFragment;
import com.wy.report.business.dailydetect.fragment.data.BloodFatDataFragment;
import com.wy.report.business.dailydetect.fragment.tendency.BloodFatTendencyChartFragment;
import com.wy.report.business.dailydetect.fragment.tendency.DailyDetectTendencyCharFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueViewContainer;

import java.util.ArrayList;
import java.util.List;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BLOOD_FAT;

/*
 *
 * @author cantalou
 * @date 2018-01-13 21:49
 */
public class BloodFatFragment extends DailyDetectFragment {

    @Override
    public void saveRecord(final View view) {
        dailyDetectService.recordBloodFat(user.getId(), DETECT_TYPE_BLOOD_FAT, getValue(0), getValue(1), getValue(2), getValue(3))
                          .subscribe(new NetworkSubscriber<ResponseModel>(this) {
                              @Override
                              public void handleSuccess(ResponseModel responseModel) {
                                  super.handleSuccess(responseModel);
                                  BloodFatFragment.super.saveRecord(view);
                              }
                          });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.daily_detect_type_blood_fat);
    }

    @NonNull
    protected BaseFragment[] getFragments() {
        return new BaseFragment[]{new BloodFatTendencyChartFragment(), new BloodFatDataFragment()};
    }

    protected ViewGroup createDetectValueView() {

        List<ValueType> result = new ArrayList<>();

        ValueType tc = new ValueType.Builder().name("总胆固醇")
                                              .unit("mmol/L")
                                              .start(1.0)
                                              .end(9.9)
                                              .fraction("0.0")
                                              .delta(0.1)
                                              .create();
        result.add(tc);

        ValueType tg = new ValueType.Builder().name("甘油三酯")
                                              .unit("mmol/L")
                                              .start(0.10)
                                              .end(3.00)
                                              .fraction("0.0")
                                              .delta(0.1)
                                              .create();
        result.add(tg);


        ValueType hdl = new ValueType.Builder().name("高胆固醇")
                                               .unit("mmol/L")
                                               .start(0.10)
                                               .end(9.9)
                                               .fraction("0.00")
                                               .delta(0.01)
                                               .create();
        result.add(hdl);

        ValueType ldl = new ValueType.Builder().name("低胆固醇")
                                               .unit("mmol/L")
                                               .start(0)
                                               .end(3.1)
                                               .fraction("0.0")
                                               .delta(0.1)
                                               .create();
        result.add(ldl);

        ValueViewContainer detectValueContainerView = new ValueViewContainer(getActivity());
        detectValueContainerView.setData(result);
        return detectValueContainerView;
    }

}
