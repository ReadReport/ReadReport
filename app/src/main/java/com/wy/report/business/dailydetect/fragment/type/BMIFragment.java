package com.wy.report.business.dailydetect.fragment.type;

import android.view.View;
import android.view.ViewGroup;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.fragment.DailyDetectFragment;
import com.wy.report.business.dailydetect.fragment.data.BMIDataFragment;
import com.wy.report.business.dailydetect.fragment.tendency.DailyDetectTendencyCharFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueViewContainer;

import java.util.ArrayList;
import java.util.List;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BMI;

/*
 *
 * @author cantalou
 * @date 2018-01-13 21:49
 */
public class BMIFragment extends DailyDetectFragment {

    @Override
    public void saveRecord(View view) {
        dailyDetectService.recordBMI(user.getId(), DETECT_TYPE_BMI, getValue(0), getValue(1))
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
        setTitle(R.string.daily_detect_type_bmi);
    }

    @Override
    protected BaseFragment[] getFragments() {
        return new BaseFragment[]{new DailyDetectTendencyCharFragment(), new BMIDataFragment()};
    }

    @Override
    protected ViewGroup createDetectValueView() {
        List<ValueType> result = new ArrayList<>();

        ValueType weight = new ValueType.Builder().name("体重")
                                                  .unit("kg")
                                                  .start(30)
                                                  .end(300)
                                                  .startIndex(30)
                                                  .create();
        result.add(weight);

        ValueType height = new ValueType.Builder().name("身高")
                                                  .unit("cm")
                                                  .start(80)
                                                  .end(250)
                                                  .startIndex(80)
                                                  .create();
        result.add(height);

        ValueViewContainer detectValueContainerView = new ValueViewContainer(getActivity());
        detectValueContainerView.setData(result);
        return detectValueContainerView;
    }

    @Override
    protected String[] parseResValue(DailyDetectDataModel model) {
        return model.getRes()
                    .replace("cm", "")
                    .replace("kg", "")
                    .split("-");

    }
}
