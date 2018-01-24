package com.wy.report.business.dailydetect.fragment.tendency;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-31 14:50
 */
public abstract class DailyDetectTendencyCharFragment extends NetworkFragment {

    protected List<DailyDetectDataModel> data;

    @BindView(R.id.line_chart)
    LineChart lineChart;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_daily_detect_tendency_chart;
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }

    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_LOADED)})
    public void dataLoaded(ArrayList<DailyDetectDataModel> data) {
        this.data = data;
        updateTendencyChar();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_DELETE)})
    public void deleteData(DailyDetectDataModel model) {
        data.remove(model);
        updateTendencyChar();
    }

    protected void updateTendencyChar() {

        lineChart.getDescription()
                 .setEnabled(false);
        lineChart.setNoDataText("没有数据");
        lineChart.setNoDataTextColor(Color.BLUE);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);

        XAxis leftXAxis = lineChart.getXAxis();
        leftXAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                long milliseconds = data.get((int) v)
                                        .getTestTime() * 1000;
                return new SimpleDateFormat("MM-dd").format(new Date(milliseconds));
            }
        });
        leftXAxis.setLabelCount(data.size());
        leftXAxis.setLabelRotationAngle(30);
        leftXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        leftXAxis.setAvoidFirstLastClipping(true);

        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false);

        lineChart.setTouchEnabled(false);
        lineChart.setDragDecelerationEnabled(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (LineDataSet set : getDataSet()) {
            set.setLineWidth(1f);
            set.setCircleRadius(3f);
            set.setDrawCircleHole(false);
            set.setValueTextSize(9f);
            set.setDrawFilled(false);
            dataSets.add(set);
        }
        lineChart.setData(new LineData(dataSets));

    }

    protected abstract ArrayList<LineDataSet> getDataSet();
}
