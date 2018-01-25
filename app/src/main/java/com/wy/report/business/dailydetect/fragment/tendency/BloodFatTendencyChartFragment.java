package com.wy.report.business.dailydetect.fragment.tendency;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.dailydetect.model.DailyDetectValueModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cantalou
 * @date 2018年01月24日 18:09
 * <p>
 */
public class BloodFatTendencyChartFragment extends DailyDetectTendencyCharFragment {

    @Override
    protected ArrayList<LineDataSet> getDataSet() {

        ArrayList<LineDataSet> result = new ArrayList<>();

        int size = data.size();
        List<Entry> hdlEntry = new ArrayList<>(size);
        List<Entry> ldlEntry = new ArrayList<>(size);
        List<Entry> cholEntry = new ArrayList<>(size);
        List<Entry> trigEntry = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            DailyDetectDataModel model = data.get(i);
            DailyDetectValueModel valueModel = model.getRes();
            hdlEntry.add(new Entry(i, Float.parseFloat(valueModel.getHdlValue())));
            ldlEntry.add(new Entry(i, Float.parseFloat(valueModel.getLdlValue())));
            cholEntry.add(new Entry(i, Float.parseFloat(valueModel.getCholValue())));
            trigEntry.add(new Entry(i, Float.parseFloat(valueModel.getTrigValue())));
        }

        LineDataSet hdl = new LineDataSet(hdlEntry, "高胆固醇");
        hdl.setColor(Color.BLUE);
        hdl.setCircleColor(Color.BLUE);
        result.add(hdl);

        LineDataSet ldl = new LineDataSet(ldlEntry, "低胆固醇");
        ldl.setColor(Color.BLACK);
        ldl.setCircleColor(Color.BLACK);
        result.add(ldl);

        LineDataSet chol = new LineDataSet(cholEntry, "总胆固醇 ");
        chol.setColor(Color.RED);
        chol.setCircleColor(Color.RED);
        result.add(chol);

        LineDataSet trig = new LineDataSet(trigEntry, "甘油三酯");
        trig.setColor(Color.RED);
        trig.setCircleColor(Color.RED);
        result.add(trig);

        return result;
    }

    @Override
    protected float getYMaxValue() {
        return 10;
    }

    @Override
    protected float getYMinValue() {
        return 0;
    }
}
