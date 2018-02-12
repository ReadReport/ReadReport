package com.wy.report.widget.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-27 下午4:47
 * @description: ReadReport
 */
public class CountDownTextView extends android.support.v7.widget.AppCompatTextView {

    private CountDownTimer timer = new CountDownTimer(59000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            setText((millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            setEnabled(true);
            setText("获取验证码");
        }
    };

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startCountDown() {
        setEnabled(false);
        timer.start();
    }


}
