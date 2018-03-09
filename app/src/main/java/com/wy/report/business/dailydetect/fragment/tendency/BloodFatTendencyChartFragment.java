package com.wy.report.business.dailydetect.fragment.tendency;

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


        LineDataSet chol = new LineDataSet(cholEntry, "总胆固醇 ");
        chol.setColor(getColor(R.color.lan_30acff));
        chol.setCircleColor(getColor(R.color.lan_30acff));
        result.add(chol);

        LineDataSet trig = new LineDataSet(trigEntry, "甘油三酯");
        trig.setColor(getColor(R.color.hong_f54f52));
        trig.setCircleColor(getColor(R.color.hong_f54f52));
        result.add(trig);

        LineDataSet hdl = new LineDataSet(hdlEntry, "高胆固醇");
        hdl.setColor(getColor(R.color.huang_ffe720));
        hdl.setCircleColor(getColor(R.color.huang_ffe720));
        result.add(hdl);

        LineDataSet ldl = new LineDataSet(ldlEntry, "低胆固醇");
        ldl.setColor(getColor(R.color.lan_26f0df));
        ldl.setCircleColor(getColor(R.color.lan_26f0df));
        result.add(ldl);


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
