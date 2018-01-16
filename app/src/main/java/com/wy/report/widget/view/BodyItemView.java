package com.wy.report.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.util.DensityUtils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-15 下午8:02
 * @description: ReadReport
 */
public class BodyItemView extends LinearLayout {

    private static final int TITLE_SIZE            = 10;
    private static final int MARGIN_SIZE           = 10;
    private static final int TITLE_COLOR           = R.color.hui_575757;
    private static final int TITLE_UN_NORMAL_COLOR = R.color.hong_e60012;

    private TextView  titleTv;
    private ImageView logoImg;
    private String    title;
    private boolean   rotate;
    private int       normalRes;
    private int       unNormalRes;

    private boolean normal;

    private UnNormalClickListener mUnNormalClickListener;

    public BodyItemView(Context context) {
        super(context);
        initConfig(context, null);
        initView(context);
    }

    public BodyItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initConfig(context, attrs);
        initView(context);
    }

    public BodyItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig(context, attrs);
        initView(context);
    }


    private void initConfig(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BodyItemView);
        title = a.getString(R.styleable.BodyItemView_title);
        rotate = a.getBoolean(R.styleable.BodyItemView_rotate, false);
        normalRes = a.getResourceId(R.styleable.BodyItemView_icon_normal, R.drawable.sjxt);
        unNormalRes = a.getResourceId(R.styleable.BodyItemView_icon_un_normal, R.drawable.sjxt_red);
        a.recycle();
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        if (rotate) {
            LinearLayout.LayoutParams titleParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            titleParams.gravity = Gravity.CENTER_HORIZONTAL;
            titleTv = new TextView(context);
            titleTv.setText(title);
            titleTv.setTextSize(TITLE_SIZE);
            titleTv.setTextColor(context.getResources().getColor(TITLE_COLOR));
            addView(titleTv, titleParams);

            LinearLayout.LayoutParams logoParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            logoParams.gravity = Gravity.CENTER_HORIZONTAL;
            logoParams.setMargins(0, DensityUtils.dip2px(getContext(), MARGIN_SIZE), 0, 0);
            logoImg = new ImageView(context);
            logoImg.setImageResource(normalRes);
            addView(logoImg, logoParams);
        } else {
            LinearLayout.LayoutParams logoParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            logoParams.gravity = Gravity.CENTER_HORIZONTAL;
            logoImg = new ImageView(context);
            logoImg.setImageResource(normalRes);
            addView(logoImg, logoParams);

            LinearLayout.LayoutParams titleParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            titleParams.gravity = Gravity.CENTER_HORIZONTAL;
            titleParams.setMargins(0, DensityUtils.dip2px(getContext(), MARGIN_SIZE), 0, 0);
            titleTv = new TextView(context);
            titleTv.setText(title);
            titleTv.setTextSize(TITLE_SIZE);
            titleTv.setTextColor(context.getResources().getColor(TITLE_COLOR));
            addView(titleTv, titleParams);
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!normal) {
                    if (mUnNormalClickListener != null) {
                        mUnNormalClickListener.onUnNormalClick();
                    }
                }
            }
        });
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
        int textColor = normal ? TITLE_COLOR : TITLE_UN_NORMAL_COLOR;
        int imgSrc    = normal ? normalRes : unNormalRes;
        titleTv.setTextColor(getResources().getColor(textColor));
        logoImg.setImageResource(imgSrc);
    }

    public void addUnNormalClickListener(UnNormalClickListener clickListener) {
        this.mUnNormalClickListener = clickListener;
    }

    public static interface UnNormalClickListener {
        void onUnNormalClick();
    }
}
