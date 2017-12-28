package com.wy.report.widget.view.dailydetect;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wy.report.R;

import java.util.ArrayList;
import java.util.List;


public class WheelView extends ScrollView {

    public static final int OFF_SET_DEFAULT = 1;
    private static final int SCROLL_DIRECTION_UP = 0;
    private static final int SCROLL_DIRECTION_DOWN = 1;
    int offset = OFF_SET_DEFAULT;
    Paint paint;
    int viewWidth;
    int[] selectedAreaBorder;
    int initialY;
    Runnable scrollerTask;
    int newCheck = 50;
    List<String> items;
    int displayItemCount;
    int selectedIndex = 1;
    int itemHeight = 0;
    private Context context;
    private LinearLayout linearLayout;
    private Resources res;
    private int normalViewHeight;
    private int selectedViewHeight;
    private int scrollDirection = -1;
    private OnWheelViewListener onWheelViewListener;

    public WheelView(Context context) {
        super(context);
        init(context);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private List<String> getItems() {
        return items;
    }

    public void setItems(List<String> list) {
        if (null == items) {
            items = new ArrayList<String>();
        }
        items.clear();
        items.addAll(list);

        for (int i = 0; i < offset; i++) {
            items.add(0, "");
            items.add("");
        }
        initData();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    private void init(Context context) {
        this.context = context;
        this.res = context.getResources();

        normalViewHeight = dip2px(34);
        selectedViewHeight = dip2px(38);
        itemHeight = normalViewHeight;

        this.setVerticalScrollBarEnabled(false);

        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        this.addView(linearLayout);

        scrollerTask = new Runnable() {

            public void run() {

                int newY = getScrollY();
                if (initialY - newY == 0) {
                    final int remainder = initialY % itemHeight;
                    final int divided = initialY / itemHeight;
                    if (remainder == 0) {
                        selectedIndex = divided + offset;
                        onSeletedCallBack();
                    } else {
                        if (remainder > itemHeight / 2) {
                            WheelView.this.post(new Runnable() {
                                @Override
                                public void run() {
                                    WheelView.this.smoothScrollTo(0, initialY - remainder + itemHeight);
                                    selectedIndex = divided + offset + 1;
                                    onSeletedCallBack();
                                }
                            });
                        } else {
                            WheelView.this.post(new Runnable() {
                                @Override
                                public void run() {
                                    WheelView.this.smoothScrollTo(0, initialY - remainder);
                                    selectedIndex = divided + offset;
                                    onSeletedCallBack();
                                }
                            });
                        }
                    }
                } else {
                    initialY = getScrollY();
                    WheelView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    public void startScrollerTask() {
        initialY = getScrollY();
        this.postDelayed(scrollerTask, newCheck);
    }

    private void initData() {
        displayItemCount = offset * 2 + 1;
        linearLayout.removeAllViews();
        for (String item : items) {
            linearLayout.addView(createView(item));
        }
        refreshItemView(0);
    }

    private TextView createView(String item) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LayoutParams(dip2px(40), dip2px(38)));
        tv.setSingleLine(true);
        tv.setText(item);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshItemView(t);
        if (t > oldt) {
            scrollDirection = SCROLL_DIRECTION_DOWN;
        } else {
            scrollDirection = SCROLL_DIRECTION_UP;
        }
    }

    private void refreshItemView(int y) {
        int position = y / itemHeight + offset;
        int remainder = y % itemHeight;
        int divided = y / itemHeight;

        if (remainder == 0) {
            position = divided + offset;
        } else {
            if (remainder > itemHeight / 2) {
                position = divided + offset + 1;
            }
        }

        int childSize = linearLayout.getChildCount();
        for (int i = 0; i < childSize; i++) {
            TextView itemView = (TextView) linearLayout.getChildAt(i);
            if (null == itemView) {
                return;
            }
            if (position == i) {
                setToSelectedView(itemView);
            } else {
                setToNormalView(itemView);
            }
        }
    }

    private void setToNormalView(TextView tv) {
        tv.setLayoutParams(new LayoutParams(dip2px(40), dip2px(34)));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv.setTextColor(res.getColor(R.color.hui_ababab));
    }

    private void setToSelectedView(TextView tv) {
        tv.setLayoutParams(new LayoutParams(dip2px(40), dip2px(34)));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tv.setTextColor(res.getColor(R.color.hei_333333));
    }


    private int[] obtainSelectedAreaBorder() {
        if (null == selectedAreaBorder) {
            selectedAreaBorder = new int[2];
            for (int i = 0; i < offset; i++) {
                selectedAreaBorder[0] += getChildAt(i).getMeasuredHeight();
            }
            selectedAreaBorder[1] = selectedAreaBorder[0] + getChildAt(offset).getMeasuredHeight();
        }

        return selectedAreaBorder;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (viewWidth == 0) {
            viewWidth = getMeasuredWidth();
        }

        if (null == paint) {
            paint = new Paint();
            paint.setColor(res.getColor(R.color.hui_ababab));
            paint.setStrokeWidth(dip2px(0.5f));
        }

        int width = getChildAt(0).getMeasuredWidth();
        int[] selectedAreaBorder = obtainSelectedAreaBorder();
        canvas.drawLine(0, selectedAreaBorder[0], width, selectedAreaBorder[0], paint);
        canvas.drawLine(0, selectedAreaBorder[1], width, selectedAreaBorder[1], paint);
        super.onDraw(canvas);
    }

    /**
     * 选中回调
     */
    private void onSeletedCallBack() {
        if (null != onWheelViewListener) {
            onWheelViewListener.onSelected(selectedIndex, items.get(selectedIndex));
        }

    }

    public void setSeletion(int position) {
        final int p = position;
        selectedIndex = p + offset;
        this.post(new Runnable() {
            @Override
            public void run() {
                WheelView.this.smoothScrollTo(0, p * itemHeight);
            }
        });

    }

    public String getSeletedItem() {
        return items.get(selectedIndex);
    }

    public int getSeletedIndex() {
        return selectedIndex - offset;
    }


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {

            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    public OnWheelViewListener getOnWheelViewListener() {
        return onWheelViewListener;
    }

    public void setOnWheelViewListener(OnWheelViewListener onWheelViewListener) {
        this.onWheelViewListener = onWheelViewListener;
    }

    private int dip2px(float dpValue) {
        final float scale = context.getResources()
                                   .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getViewMeasuredHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
        return view.getMeasuredHeight();
    }

    public static class OnWheelViewListener {
        public void onSelected(int selectedIndex, String item) {
        }
    }

}