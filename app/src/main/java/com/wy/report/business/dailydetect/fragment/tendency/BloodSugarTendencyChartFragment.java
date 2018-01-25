package com.wy.report.business.dailydetect.fragment.tendency;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.wy.report.business.dailydetect.model.DailyDetectValueModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cantalou
 * @date 2018年01月24日 18:09
 * <p>
 */
public class BloodSugarTendencyChartFragment extends DailyDetectTendencyCharFragment {

    @Override
    protected ArrayList<LineDataSet> getDataSet() {

        ArrayList<LineDataSet> result = new ArrayList<>();

        int size = data.size();
        List<Entry> highEntry = new ArrayList<>(size);
        List<Entry> lowEntry = new ArrayList<>(size);
        List<Entry> pulseEntry = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            DailyDetectValueModel valueModel = data.get(i)
                                                   .getRes();
            highEntry.add(new Entry(i, Float.parseFloat(valueModel.getHighValue())));
            lowEntry.add(new Entry(i, Float.parseFloat(valueModel.getLowValue())));
            pulseEntry.add(new Entry(i, Float.parseFloat(valueModel.getPulseValue())));
        }

        LineDataSet high = new LineDataSet(highEntry, "血糖");
        high.setColor(Color.BLACK);
        high.setCircleColor(Color.BLACK);
        result.add(high);

        LineDataSet low = new LineDataSet(highEntry, "低压");
        low.setColor(Color.BLACK);
        low.setCircleColor(Color.BLACK);
        result.add(low);

        LineDataSet pulse = new LineDataSet(highEntry, "心率");
        pulse.setColor(Color.BLACK);
        pulse.setCircleColor(Color.BLACK);
        result.add(pulse);

        return result;
    }

    @Override
    protected float getYMaxValue() {
        return 0;
    }

    @Override
    protected float getYMinValue() {
        return 0;
    }
}
