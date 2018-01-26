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
public class BodyFatTendencyChartFragment extends DailyDetectTendencyCharFragment {

    @Override
    protected ArrayList<LineDataSet> getDataSet() {

        ArrayList<LineDataSet> result = new ArrayList<>();

        int size = data.size();
        List<Entry> cholEntry = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            DailyDetectValueModel valueModel = data.get(i)
                                                   .getRes();
            cholEntry.add(new Entry(i, Float.parseFloat(valueModel.getCholValue())));
        }

        LineDataSet chol = new LineDataSet(cholEntry, "体脂率");
        chol.setColor(getColor(R.color.lan_30acff));
        chol.setCircleColor(getColor(R.color.lan_30acff));
        result.add(chol);

        return result;
    }


    @Override
    protected float getYMaxValue() {
        return 100;
    }

    @Override
    protected float getYMinValue() {
        return 1;
    }
}
