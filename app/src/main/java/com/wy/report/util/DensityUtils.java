package com.wy.report.util;

import android.content.Context;


public class DensityUtils {

    /**
     * Description: 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = getDensity(context);
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Description: 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = getDensity(context);
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Description: 获取当前设备的像素
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        return context.getResources()
                      .getDisplayMetrics().density;
    }

    /**
     * sp转换为像数方法
     *
     * @param spValue sp值
     * @return 像数值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources()
                                       .getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
