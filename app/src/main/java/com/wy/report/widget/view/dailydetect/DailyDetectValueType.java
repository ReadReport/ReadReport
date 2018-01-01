package com.wy.report.widget.view.dailydetect;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-26 23:14
 */
public class DailyDetectValueType {

    private static String[] DECIMAL_FORMAT_PATTERN = {"", "0.0", "0.00"};

    private String name;

    private String unit;

    private List<String> values;

    private int showNum;

    private int startIndex;

    public DailyDetectValueType(String name, String unit, int showNum, double start, double end, double delta, int fraction, int startIndex) {
        this(name, unit, showNum, getValues(start, end, delta, DECIMAL_FORMAT_PATTERN[fraction]), startIndex);
    }


    public DailyDetectValueType(String name, String unit, int showNum, int start, int end, int delta, int startIndex) {
        this(name, unit, showNum, getValues(start, end, delta), startIndex);
    }

    public DailyDetectValueType(String name, String unit, int showNum, int start, int end, int startIndex) {
        this(name, unit, showNum, start, end, 1, startIndex);
    }

    public DailyDetectValueType(String name, String unit, int showNum, List<String> values, int startIndex) {
        this.name = name;
        this.unit = unit;
        this.showNum = showNum;
        this.values = values;
        this.startIndex = startIndex;
    }

    private static List<String> getValues(double start, double end, double delta, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern(pattern);
        List<String> values = new ArrayList<>((int) ((end - start) / delta));
        for (; start <= end; start += delta) {
            values.add(decimalFormat.format(start));
        }
        return values;
    }

    @NonNull
    private static List<String> getValues(int start, int end, int delta) {
        List<String> values = new ArrayList<>((end - start) / delta + 1);
        for (; start <= end; start += delta) {
            values.add(Integer.toString(start));
        }
        return values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public int getShowNum() {
        return showNum;
    }

    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
