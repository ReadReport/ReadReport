package com.wy.report.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.cantalou.android.util.Log;
import com.wy.report.R;

/*
 *
 * @author cantalou
 * @date 2018-01-20 23:08
 */
public class SwipeLayout extends HorizontalScrollView {

    private TextView mTextView_Delete;

    private int mScrollWidth;

    private IonSlidingButtonListener mIonSlidingButtonListener;

    private Boolean isOpen = false;

    private Boolean once = false;

    private float x;

    private int touchSlop;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        touchSlop = ViewConfiguration.get(getContext())
                                     .getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!once) {
            mTextView_Delete = (TextView) findViewById(R.id.vh_daily_detect_data_list_delete);
            once = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(0, 0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            mScrollWidth = mTextView_Delete.getWidth();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollx() {
        if (getScrollX() >= (mScrollWidth / 2)) {
            this.smoothScrollTo(mScrollWidth, 0);
            isOpen = true;
            if (mIonSlidingButtonListener != null) {
                mIonSlidingButtonListener.onMenuIsOpen(this);
            }
        } else {
            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) {
            return;
        }
        this.smoothScrollTo(mScrollWidth, 0);
        isOpen = true;
        if (mIonSlidingButtonListener != null) {
            mIonSlidingButtonListener.onMenuIsOpen(this);
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }


    public void setSlidingButtonListener(IonSlidingButtonListener listener) {
        mIonSlidingButtonListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int scrollX = getScrollX();
        Log.d("onTouchEvent scrollX:{}", scrollX);
        if (Math.abs(scrollX) != 0) {
            return super.onInterceptTouchEvent(ev);
        }

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE: {
                Log.d("onTouchEvent ev.getX():{}, x:{}, {}, touchSlop:{}", ev.getX(), x, ev.getX() - x, touchSlop);
                if (Math.abs(scrollX) == 0 && ev.getX() - x > touchSlop) {
                    requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                requestDisallowInterceptTouchEvent(false);
                break;
        }

        /*
        * The only time we want to intercept motion events is if we are in the
        * drag mode.
        */
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int scrollX = getScrollX();
        if (Math.abs(scrollX) != 0) {
            return super.onTouchEvent(ev);
        }

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE: {
                if (mIonSlidingButtonListener != null) {
                    mIonSlidingButtonListener.onDownOrMove(this);
                }
                Log.d("onTouchEvent ev.getX():{}, x:{}, {}, touchSlop:{}", ev.getX(), x, ev.getX() - x, touchSlop);
                if (Math.abs(scrollX) == 0 && ev.getX() - x > touchSlop) {
                    requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                break;
            }

            case MotionEvent.ACTION_DOWN: {
                this.x = ev.getX();
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                requestDisallowInterceptTouchEvent(false);
                break;
        }

        /*
        * The only time we want to intercept motion events is if we are in the
        * drag mode.
        */
        return super.onTouchEvent(ev);
    }

    public interface IonSlidingButtonListener {
        void onMenuIsOpen(View view);

        void onDownOrMove(SwipeLayout slidingButtonView);
    }
}
