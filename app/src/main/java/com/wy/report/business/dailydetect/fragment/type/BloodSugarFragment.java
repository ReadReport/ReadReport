package com.wy.report.business.dailydetect.fragment.type;

import android.view.View;
import android.view.ViewGroup;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.fragment.DailyDetectFragment;
import com.wy.report.business.dailydetect.fragment.data.BloodSugarDataFragment;
import com.wy.report.business.dailydetect.fragment.tendency.DailyDetectTendencyCharFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueViewContainer;
import com.wy.report.widget.view.wheel.WheelViewItem;

import java.util.ArrayList;
import java.util.List;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BLOOD_SUGAR;

/**
 * @author cantalou
 * @date 2018年01月04日 10:29
 * <p>
 */
public class BloodSugarFragment extends DailyDetectFragment {

    @Override
    public void saveRecord(final View view) {
        dailyDetectService.recordBloodSugar(user.getId(), DETECT_TYPE_BLOOD_SUGAR, "", getValue(0), getValue(1) + "." + getValue(2))
                          .subscribe(new NetworkSubscriber<ResponseModel>(this) {
                              @Override
                              public void handleSuccess(ResponseModel responseModel) {
                                  super.handleSuccess(responseModel);
                                  BloodSugarFragment.super.saveRecord(view);
                              }
                          });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.daily_detect_type_blood_sugar);
    }

    protected ViewGroup createDetectValueView() {

        List<ValueType> result = new ArrayList<>();

        List<WheelViewItem> values = new ArrayList<WheelViewItem>() {{
            add(new WheelViewItem("1", "空腹"));
            add(new WheelViewItem("2", "早餐后"));
            add(new WheelViewItem("3", "午餐前"));
            add(new WheelViewItem("4", "午餐后"));
            add(new WheelViewItem("5", "晚餐前"));
            add(new WheelViewItem("6", "晚餐后"));
            add(new WheelViewItem("7", "睡前"));
            add(new WheelViewItem("8", "夜间"));
            add(new WheelViewItem("0", "随机"));
        }};
        ValueType time = new ValueType.Builder().name("时间段")
                                                .unit("- -")
                                                .values(values)
                                                .create();
        result.add(time);

        ValueType number = new ValueType.Builder().unit("血糖值")
                                                  .start(0)
                                                  .end(30)
                                                  .fraction("0.0")
                                                  .create();
        result.add(number);

        ValueViewContainer detectValueContainerView = new ValueViewContainer(getActivity());
        detectValueContainerView.setData(result);
        return detectValueContainerView;
    }

    @Override
    protected BaseFragment[] getFragments() {
        return new BaseFragment[]{new DailyDetectTendencyCharFragment(), new BloodSugarDataFragment()};
    }

}
