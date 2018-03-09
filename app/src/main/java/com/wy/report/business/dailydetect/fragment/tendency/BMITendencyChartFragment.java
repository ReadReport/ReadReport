package com.wy.report.business.dailydetect.fragment.tendency;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.wy.report.R;
import com.wy.report.business.dailydetect.model.DailyDetectValueModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cantalou
 * @date 2018年01月24日 18:09
 * <p>
 */
public class BMITendencyChartFragment extends DailyDetectTendencyCharFragment {

    @Override
    protected ArrayList<LineDataSet> getDataSet() {

        ArrayList<LineDataSet> result = new ArrayList<>();

        int size = data.size();
        List<Entry> heightEntry = new ArrayList<>(size);
        List<Entry> weightEntry = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            DailyDetectValueModel valueModel = data.get(i)
                                                   .getRes();
            heightEntry.add(new Entry(i, Float.parseFloat(valueModel.getHightValue())));
            weightEntry.add(new Entry(i, Float.parseFloat(valueModel.getWeightValue())));
        }

        LineDataSet height = new LineDataSet(heightEntry, "身高");
        height.setColor(getColor(R.color.lan_30acff));
        height.setCircleColor(getColor(R.color.lan_30acff));
        result.add(height);

        LineDataSet weight = new LineDataSet(weightEntry, "体重");
        weight.setColor(getColor(R.color.hong_f54f52));
        weight.setCircleColor(getColor(R.color.hong_f54f52));
        result.add(weight);

        return result;
    }

    @Override
    protected float getYMaxValue() {
        return 310;
    }

    @Override
    protected float getYMinValue() {
        return 20;
    }
}
