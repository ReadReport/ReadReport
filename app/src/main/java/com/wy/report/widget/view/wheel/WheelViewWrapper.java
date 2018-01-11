package com.wy.report.widget.view.wheel;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.widget.view.wheel.adapter.BaseWheelAdapter;

import java.util.List;

/**
 * @author cantalou
 * @date 2018年01月11日 14:08
 * <p>
 */
public class WheelViewWrapper extends com.wy.report.widget.view.wheel.widget.WheelView<WheelViewItem> {

    public WheelViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private BaseWheelAdapter adapter;

    private void init() {
        adapter = new BaseWheelAdapter<WheelViewItem>() {
            @Override
            protected View bindView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext())
                                                .inflate(R.layout.vh_wheel_item, parent, false);
                }
                ((TextView) convertView).setText(getItem(position).getTitle());
                return convertView;
            }
        };
        setWheelAdapter(adapter);
        setWheelSize(5);
    }

    public void setData(List<WheelViewItem> data) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
        setWheelData(data);
    }

    public WheelViewItem getSelectedItem() {
        return getSelectionItem();
    }

    /**
     * 刷新文本
     *
     * @param position
     * @param curPosition
     * @param itemView
     * @param textView
     */
    protected void refreshTextView(int position, int curPosition, View itemView, TextView textView) {
        Resources res = getResources();
        if (curPosition == position) { // 选中
            setTextView(itemView, textView, res.getColor(R.color.hei_333333), 14, 1.0f);
        } else {    // 未选中
            setTextView(itemView, textView, res.getColor(R.color.hui_ababab), 13, 1);
        }
    }
}
