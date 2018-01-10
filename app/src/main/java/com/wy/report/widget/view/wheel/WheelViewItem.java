package com.wy.report.widget.view.wheel;

/**
 * @author cantalou
 * @date 2018年01月10日 10:21
 * <p>
 */
public class WheelViewItem {

    private String value;

    private String title;

    public WheelViewItem(String value) {
        this.value = value;
        this.title = value.toString();
    }

    public WheelViewItem(String value, String title) {
        this.value = value;
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }


    public static final WheelViewItem EMPTY = new WheelViewItem("");
}
