package com.wy.report.widget.view.dailydetect;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-26 23:14
 */
public class DailyDetectValueType {

    public static class Builder {

        private DailyDetectValueType result;

        private double start;

        private double end;

        private double delta = 1;

        private String fraction;

        public Builder() {
            result = new DailyDetectValueType();
        }

        public Builder name(String name) {
            result.name = name;
            return this;
        }

        public Builder unit(String unit) {
            result.unit = unit;
            return this;
        }

        public Builder showNum(int showNum) {
            result.showNum = showNum;
            return this;
        }

        public Builder startIndex(int startIndex) {
            result.startIndex = startIndex;
            return this;
        }

        public Builder start(double start) {
            this.start = start;
            return this;
        }

        public Builder end(double end) {
            this.end = end;
            return this;
        }

        public Builder delta(double delta) {
            this.delta = delta;
            return this;
        }

        public Builder fraction(String fraction) {
            this.fraction = fraction;
            return this;
        }

        public Builder values(List<String> values) {
            result.values = values;
            return this;
        }

        public DailyDetectValueType create() {
            if (result.values == null) {
                if (TextUtils.isEmpty(fraction)) {
                    result.setValues(getValues((int) start, (int) end, (int) delta));
                } else {
                    result.setValues(getValues(start, end, delta, fraction));
                }
            }
            return result;
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
    }

    private static String[] DECIMAL_FORMAT_PATTERN = {"", "0.0", "0.00"};

    private String name;

    private String unit;

    private List<String> values;

    private int showNum = 5;

    private int startIndex = -1;

    private DailyDetectValueType minValue;

    private DailyDetectValueType maxValue;

    private String selectValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
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

    public int getStartIndex() {
        return startIndex;
    }

    public DailyDetectValueType getMinValue() {
        return minValue;
    }

    public void setMinValue(DailyDetectValueType minValue) {
        this.minValue = minValue;
    }

    public DailyDetectValueType getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(DailyDetectValueType maxValue) {
        this.maxValue = maxValue;
    }

    public String getSelectValue() {
        return selectValue;
    }

    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
    }
}
