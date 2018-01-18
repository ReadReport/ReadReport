package com.wy.report.business.dailydetect.fragment.type;

import android.view.View;
import android.view.ViewGroup;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.business.dailydetect.fragment.DailyDetectFragment;
import com.wy.report.business.dailydetect.fragment.data.BodyFatDataFragment;
import com.wy.report.business.dailydetect.fragment.tendency.DailyDetectTendencyCharFragment;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueViewContainer;

import java.util.ArrayList;
import java.util.List;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BODY_FAT;

/*
 *
 * @author cantalou
 * @date 2018-01-13 21:49
 */
public class BodyFatFragment extends DailyDetectFragment {

    @Override
    public void saveRecord(View view) {
        dailyDetectService.recordBodyFat(user.getId(), DETECT_TYPE_BODY_FAT, getValue(0));
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.daily_detect_type_body_fat);
    }

    @Override
    protected BaseFragment[] getFragments() {
        return new BaseFragment[]{new DailyDetectTendencyCharFragment(), new BodyFatDataFragment()};
    }

    @Override
    protected ViewGroup createDetectValueView() {
        List<ValueType> result = new ArrayList<>();

        ValueType dailyDetectValueType = new ValueType.Builder().name("体脂率")
                                                                .unit("%")
                                                                .start(1)
                                                                .end(100)
                                                                .startIndex(20)
                                                                .create();
        result.add(dailyDetectValueType);

        ValueViewContainer detectValueContainerView = new ValueViewContainer(getActivity());
        detectValueContainerView.setData(result);
        return detectValueContainerView;
    }

}
