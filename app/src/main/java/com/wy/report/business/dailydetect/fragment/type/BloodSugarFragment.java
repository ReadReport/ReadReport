package com.wy.report.business.dailydetect.fragment.type;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.fragment.DailyDetectFragment;
import com.wy.report.business.dailydetect.fragment.data.BloodSugarDataFragment;
import com.wy.report.business.dailydetect.fragment.tendency.BloodSugarTendencyChartFragment;
import com.wy.report.business.dailydetect.fragment.tendency.DailyDetectTendencyCharFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueView;
import com.wy.report.widget.view.dailydetect.ValueViewContainer;
import com.wy.report.widget.view.wheel.WheelViewItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.wy.report.business.home.model.DailyDetectTypeModel.DETECT_TYPE_BLOOD_SUGAR;

/**
 * @author cantalou
 * @date 2018年01月04日 10:29
 * <p>
 */
public class BloodSugarFragment extends DailyDetectFragment {

    ValueViewContainer valueViewContainer;

    @Override
    public void saveRecord(final View view) {
        dailyDetectService.recordBloodSugar(user.getId(), DETECT_TYPE_BLOOD_SUGAR, "", getValue(0), getValue(1) + "." + getValue(3))
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
        ValueType time = new ValueType.Builder().values(values)
                                                .startIndex(2)
                                                .create();
        result.add(time);

        ValueType number1 = new ValueType.Builder().start(3)
                                                  .end(11)
                                                  .create();
        result.add(number1);

        ValueType number2 = new ValueType.Builder().start(1)
                                                   .end(9)
                                                   .create();
        result.add(number2);


        ViewGroup resultView = (ViewGroup) LayoutInflater.from(getActivity())
                                                         .inflate(R.layout.view_daily_detect_blood_sugar_value, null, false);
        valueViewContainer = (ValueViewContainer)resultView.findViewById(R.id.view_daily_detect_blood_sugar_value_container);
        valueViewContainer.setData(result);

        TextView textView = new TextView(getActivity());
        textView.setText(".");
        textView.setTextColor(getColor(R.color.hei_333333));
        textView.setTextSize(24);
        textView.setGravity(Gravity.CENTER);

        valueViewContainer.addView(textView, valueViewContainer.getChildCount() - 1);

        return resultView;
    }

    @Override
    protected BaseFragment[] getFragments() {
        return new BaseFragment[]{new BloodSugarTendencyChartFragment(), new BloodSugarDataFragment()};
    }

    protected String getValue(int index) {
        return ((ValueView) valueViewContainer.getChildAt(index)).getWheelView()
                                                                       .getSelectedItem()
                                                                       .getValue();
    }
}
