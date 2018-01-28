package com.wy.report.widget.view.dailydetect;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.util.DensityUtils;
import com.wy.report.widget.view.wheel.WheelViewWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *
 * @author cantalou
 * @date 2017-12-26 23:13
 */
public class ValueView extends LinearLayout {

    @BindView(R.id.view_daily_detect_value_item_name)
    TextView name;

    @BindView(R.id.view_daily_detect_value_item_unit)
    TextView unit;

    @BindView(R.id.view_daily_detect_value_item_wv)
    WheelViewWrapper wheelView;

    public ValueView(Context context) {
        super(context);
        initView(context);
    }

    public ValueView(Context context, @Nullable AttributeSet attrs) {
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

    public void setData(final ValueType type) {
        if (TextUtils.isEmpty(type.getName())) {
            name.setVisibility(View.GONE);
            unit.setVisibility(View.GONE);
        } else {
            name.setText(type.getName());
            unit.setText(type.getUnit());
        }

        wheelView.setData(type.getValues());
        wheelView.setSelection(type.getStartIndex());
    }

    public WheelViewWrapper getWheelView() {
        return wheelView;
    }
}
