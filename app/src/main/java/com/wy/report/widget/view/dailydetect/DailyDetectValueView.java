package com.wy.report.widget.view.dailydetect;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.report.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *
 * @author cantalou
 * @date 2017-12-26 23:13
 */
public class DailyDetectValueView extends LinearLayout {

    @BindView(R.id.view_daily_detect_value_item_name)
    TextView name;

    @BindView(R.id.view_daily_detect_value_item_unit)
    TextView unit;

    @BindView(R.id.view_daily_detect_value_item_wv)
    WheelView wheelView;

    public DailyDetectValueView(Context context) {
        super(context);
        initView(context);
    }

    public DailyDetectValueView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_daily_detect_value, this);
        ButterKnife.bind(this, this);
    }

    public void setData(DailyDetectValueType type) {
        name.setText(type.getName());
        unit.setText(type.getUnit());
        wheelView.setItems(type.getValues());
    }
}
