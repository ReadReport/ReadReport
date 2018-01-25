package com.wy.report.business.dailydetect.fragment.tendency;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
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
import com.wy.report.util.DensityUtils;

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
        lineChart.setTouchEnabled(false);
        lineChart.setDragDecelerationEnabled(false);
        lineChart.offsetLeftAndRight(DensityUtils.dip2px(getActivity(), 10));
        lineChart.getLegend().setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v >= data.size()) {
                    return Float.toString(v);
                }
                long milliseconds = data.get((int) v)
                                        .getTestTime() * 1000;
                return new SimpleDateFormat("MM-dd").format(new Date(milliseconds));
            }
        });
        xAxis.setLabelCount(data.size());
        xAxis.setLabelRotationAngle(30);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineWidth(2);
        xAxis.setAxisLineColor(getColor(R.color.hui_575757));

        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false);

        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setAxisLineWidth(2);
        leftYAxis.setAxisLineColor(getColor(R.color.hui_575757));
        leftYAxis.setAxisMaximum(getYMaxValue());
        leftYAxis.setAxisMinimum(getYMinValue());


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (LineDataSet set : getDataSet()) {
            set.setLineWidth(3f);
            set.setCircleRadius(4f);
            set.setDrawCircleHole(false);
            set.setCircleColor(set.getColor());
            set.setDrawFilled(false);
            set.setDrawCircles(true);
            set.setDrawValues(false);
            dataSets.add(set);
        }
        lineChart.setData(new LineData(dataSets));
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    protected abstract ArrayList<LineDataSet> getDataSet();

    protected abstract float getYMaxValue();

    protected abstract float getYMinValue();
}
