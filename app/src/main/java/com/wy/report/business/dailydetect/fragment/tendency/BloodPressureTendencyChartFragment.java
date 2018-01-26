package com.wy.report.business.dailydetect.fragment.tendency;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.wy.report.R;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.dailydetect.model.DailyDetectValueModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cantalou
 * @date 2018年01月24日 18:09
 * <p>
 */
public class BloodPressureTendencyChartFragment extends DailyDetectTendencyCharFragment {

    @Override
    protected ArrayList<LineDataSet> getDataSet() {

        ArrayList<LineDataSet> result = new ArrayList<>();

        int size = data.size();
        List<Entry> highEntry = new ArrayList<>(size);
        List<Entry> lowEntry = new ArrayList<>(size);
        List<Entry> pulseEntry = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            DailyDetectDataModel model = data.get(i);
            DailyDetectValueModel valueModel = model.getRes();
            highEntry.add(new Entry(i, Float.parseFloat(valueModel.getHighValue())));
            lowEntry.add(new Entry(i, Float.parseFloat(valueModel.getLowValue())));
            pulseEntry.add(new Entry(i, Float.parseFloat(valueModel.getPulseValue())));
        }

        LineDataSet high = new LineDataSet(highEntry, "高压");
        high.setColor(getColor(R.color.lan_30acff));
        high.setCircleColor(getColor(R.color.lan_30acff));
        result.add(high);

        LineDataSet low = new LineDataSet(lowEntry, "低压");
        low.setColor(getColor(R.color.lan_26f0df));
        low.setCircleColor(getColor(R.color.lan_26f0df));
        result.add(low);

        LineDataSet pulse = new LineDataSet(pulseEntry, "心率");
        pulse.setColor(getColor(R.color.huang_ffe720));
        pulse.setCircleColor(getColor(R.color.huang_ffe720));
        result.add(pulse);

        return result;
    }

    @Override
    protected float getYMaxValue() {
        float maxValue = 190;
        if(data != null){
            for (DailyDetectDataModel model : data) {
                DailyDetectValueModel valueModel = model.getRes();
                float mayValue = Float.parseFloat(valueModel.getHighValue());
                if(mayValue > maxValue){
                    maxValue = mayValue;
                }

                mayValue = Float.parseFloat(valueModel.getLowValue());
                if(mayValue > maxValue){
                    maxValue = mayValue;
                }
            }
        }
        return maxValue;
    }

    @Override
    protected float getYMinValue() {
        return 20;
    }
}
