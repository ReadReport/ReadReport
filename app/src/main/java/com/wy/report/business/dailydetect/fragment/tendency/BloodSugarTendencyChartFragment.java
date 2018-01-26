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
public class BloodSugarTendencyChartFragment extends DailyDetectTendencyCharFragment {

    @Override
    protected ArrayList<LineDataSet> getDataSet() {

        ArrayList<LineDataSet> result = new ArrayList<>();

        int size = data.size();
        List<Entry> sugarEntry = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            DailyDetectValueModel valueModel = data.get(i)
                                                   .getRes();
            sugarEntry.add(new Entry(i, Float.parseFloat(valueModel.getSugarValue())));
        }

        LineDataSet high = new LineDataSet(sugarEntry, "血糖");
        high.setColor(getColor(R.color.lan_30acff));
        high.setCircleColor(getColor(R.color.lan_30acff));
        result.add(high);

        return result;
    }

    @Override
    protected float getYMaxValue() {
        return 13;
    }

    @Override
    protected float getYMinValue() {
        return 1;
    }
}
