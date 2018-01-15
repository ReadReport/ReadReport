package com.wy.report.business.dailydetect.fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-31 14:50
 */
public class DailyDetectTendencyCharFragment extends NetworkFragment {

    private List<DailyDetectDataModel> data;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            data = arguments.getParcelableArrayList(BundleKey.BUNDLE_KEY_DAILY_DETECT_DATA);
        }
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_daily_detect_tendency_chart;
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }

    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_ADD)})
    public void addData(DailyDetectDataModel model) {
        data.remove(model);
        updateTendencyChar();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_DELETE)})
    public void deleteData(DailyDetectDataModel model) {
        data.remove(model);
        updateTendencyChar();
    }

    private void updateTendencyChar() {

    }

    // 设置显示的样式
    private void showChart(LineChart lineChart, LineData lineData, int color) {
        Description description = new Description();
        description.setText("测试图表");
        description.setTextColor(Color.BLACK);
        description.setTextSize(20);
        lineChart.setDescription(description);//设置图表描述信息
        lineChart.setNoDataText("没有数据熬");//没有数据时显示的文字
        lineChart.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色
        lineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        lineChart.setDrawBorders(false);//禁止绘制图表边框的线
        //lineChart.setBorderColor(); //设置 chart 边框线的颜色。
        //lineChart.setBorderWidth(); //设置 chart 边界线的宽度，单位 dp。
        //lineChart.notifyDataSetChanged();//刷新数据
    }


}
