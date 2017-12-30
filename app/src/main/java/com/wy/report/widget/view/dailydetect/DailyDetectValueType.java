package com.wy.report.widget.view.dailydetect;

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

    public DailyDetectValueType() {
    }

    public DailyDetectValueType(String name, String unit, int showNum, double start, double end, double delta, int fraction) {
        this.name = name;
        this.unit = unit;
        this.showNum = showNum;
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern(DECIMAL_FORMAT_PATTERN[fraction]);
        values = new ArrayList<>((int) ((end - start) / delta));
        for (; start <= end; start += delta) {
            values.add(decimalFormat.format(start));
        }
    }

    public DailyDetectValueType(String name, String unit, int showNum, int start, int end, int delta) {
        this.name = name;
        this.unit = unit;
        this.showNum = showNum;
        values = new ArrayList<>((end - start) / delta + 1);
        for (; start <= end; start += delta) {
            values.add(Integer.toString(start));
        }
    }

    public DailyDetectValueType(String name, String unit, int showNum, int start, int end) {
        this(name, unit, showNum, start, end, 1);
    }

    public DailyDetectValueType(String name, String unit, int showNum, List<String> values) {
        this.name = name;
        this.unit = unit;
        this.showNum = showNum;
        this.values = values;
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
}
