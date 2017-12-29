package com.wy.report.widget.view.dailydetect;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
public class DailyDetectValueItem extends LinearLayout {

    @BindView(R.id.view_daily_detect_value_item_title)
    TextView title;

    @BindView(R.id.view_daily_detect_value_item_unit)
    TextView unit;

    @BindView(R.id.view_daily_detect_value_item_wv)
    WheelView wheelView;

    public DailyDetectValueItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_daily_detect_value_item, this);
        ButterKnife.bind(this, this);
    }
}
