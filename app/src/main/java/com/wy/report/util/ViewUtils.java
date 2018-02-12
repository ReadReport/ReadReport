package com.wy.report.util;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.cantalou.android.util.DeviceUtils;

/**
 * @author cantalou
 * @date 2017年11月29日 9:38
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

    public static String getText(TextView tv) {
        return tv.getText()
                 .toString();
    }

    /**
     * 修改TextView的提示文字大小
     *
     * @param tv
     * @param size 文字大小 sp
     */
    public static void setTextViewHintSize(TextView tv, int size){
        SpannableString ss = new SpannableString(tv.getHint());
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size,true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setHint(ss);
    }
}
