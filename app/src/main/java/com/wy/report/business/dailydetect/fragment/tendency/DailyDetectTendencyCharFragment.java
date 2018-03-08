package com.wy.report.business.dailydetect.fragment.tendency;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
import com.wy.report.business.dailydetect.model.DailyDetectValueModel;
import com.wy.report.util.DensityUtils;

import java.lang.reflect.Field;
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

    @BindView(R.id.empty_view)
    View emptyView;

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

        boolean dataEmpty = data.isEmpty();
        emptyView.setVisibility(dataEmpty ? View.VISIBLE : View.GONE);
        lineChart.setVisibility(dataEmpty ? View.GONE : View.VISIBLE);
        if (dataEmpty) {
            return;
        }

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
        Legend legend = lineChart.getLegend();
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v >= data.size()) {
                    v = data.size() - 1;
                }
                if (v < 0) {
                    v = 0;
                }
                long milliseconds = data.get((int) v)
                                        .getTestTime() * 1000;
                return new SimpleDateFormat("MM-dd").format(new Date(milliseconds));
            }
        });
        xAxis.setLabelCount(data.size(), true);
        xAxis.setLabelRotationAngle(30);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setAxisLineColor(getColor(R.color.hui_ababab));
        xAxis.setXOffset(20);
        xAxis.setGranularity(0.5f);
        xAxis.setGranularityEnabled(true);

        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false);

        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setAxisLineWidth(1.5f);
        leftYAxis.setAxisLineColor(getColor(R.color.hui_ababab));

        float maxValue = getYMaxValue();
        if (data != null) {
            Field[] fields = DailyDetectValueModel.class.getDeclaredFields();
            float mayValue = 0;
            for (DailyDetectDataModel model : data) {
                DailyDetectValueModel valueModel = model.getRes();
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        String value = (String) field.get(valueModel);
                        if (value == null) {
                            continue;
                        }
                        mayValue = Float.parseFloat(value);
                    } catch (Exception e) {
                    }

                    if (mayValue > maxValue) {
                        maxValue = mayValue * 1.1f;
                    }
                }
            }
        }
        leftYAxis.setAxisMaximum(maxValue);
        leftYAxis.setAxisMinimum(getYMinValue());


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (LineDataSet set : getDataSet()) {
            set.setLineWidth(2.3f);
            set.setCircleRadius(3f);
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
