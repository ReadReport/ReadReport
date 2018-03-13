package com.wy.report.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.report.R;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-29 下午3:38
 * @description: ReadReport
 */
public class CloseTitleLayout extends LinearLayout {

    private TextView mTitle;

    private ImageView mIcon;

    private boolean isOpen;

    private OnSwitchListener mOnSwitchListener;

    private View controlView;

    public CloseTitleLayout(Context context) {
        super(context);
        init(context, null);
    }

    public CloseTitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CloseTitleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        // 获取控件资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CloseTitleLayout);
        final int  id         = typedArray.getResourceId(R.styleable.CloseTitleLayout_control_id, 0);
        String     title      = typedArray.getString(R.styleable.CloseTitleLayout_show_title);


        LayoutInflater.from(context).inflate(R.layout.merge_doctor_suggestion_title, this, true);
        setOrientation(HORIZONTAL);
        mTitle = (TextView) findViewById(R.id.doctor_detail_suggestion_title);
        mIcon = (ImageView) findViewById(R.id.doctor_detail_suggestion_icon);
        isOpen = true;
        mTitle.setText(title);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    if (mOnSwitchListener != null) {
                        mOnSwitchListener.onClose();
                    }
                    mIcon.setImageResource(R.drawable.selector_btn_report_detail_open);
                    isOpen = false;
                } else {
                    if (mOnSwitchListener != null) {
                        mOnSwitchListener.onOpen();
                    }
                    mIcon.setImageResource(R.drawable.selector_btn_report_detail_close);
                    isOpen = true;
                }
            }
        });

        addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                View parent = (LinearLayout) getParent();
                controlView = parent.findViewById(id);
                setOnSwitchListener(new OnSwitchListener() {
                    @Override
                    public void onClose() {
                        controlView.setVisibility(GONE);
                    }

                    @Override
                    public void onOpen() {
                        controlView.setVisibility(VISIBLE);
                    }
                });
            }

            @Override
            public void onViewDetachedFromWindow(View view) {

            }
        });


    }

    public void setOnSwitchListener(OnSwitchListener onSwitchListener) {
        mOnSwitchListener = onSwitchListener;
    }

    public interface OnSwitchListener {

        /**
         * 关闭
         */
        void onClose();

        /**
         * 打开
         */
        void onOpen();
    }
}


