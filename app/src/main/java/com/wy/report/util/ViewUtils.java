package com.wy.report.util;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.cantalou.android.util.DeviceUtils;

/**
 * Project Name: ReadReport<p>
 * File Name:    ViewUtils.java<p>
 * ClassName:    ViewUtils<p>
 * <p>
 * TODO.
 *
 * @author LinZhiWei
 * @date 2017年11月29日 9:38
 * <p>
 * Copyright (c) 2017年, 4399 Network CO.ltd. All Rights Reserved.
 */
public class ViewUtils {

    public static void convertToLeftTopCrop(ImageView imageView) {

        Drawable drawable = imageView.getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() < 1 || drawable.getIntrinsicHeight() < 1) {
            return;
        }

        imageView.setScaleType(ImageView.ScaleType.MATRIX);

        float widthScaleRatio = DeviceUtils.getDeviceWidthPixels(imageView.getContext()) / drawable.getIntrinsicWidth();
        float heightScaleRatio = DeviceUtils.getDeviceHeightPixels(imageView.getContext()) / drawable.getIntrinsicHeight();
        float scaleRatio = widthScaleRatio > heightScaleRatio ? widthScaleRatio : heightScaleRatio;
        Matrix matrix = imageView.getImageMatrix();
        matrix.postScale(scaleRatio, scaleRatio);
        imageView.setImageMatrix(matrix);
    }
}
