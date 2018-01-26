package com.wy.report.widget.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cantalou.android.util.DeviceUtils;
import com.wy.report.R;

@SuppressLint("InflateParams")
public class CommonProgressDialog extends AlertDialog {

    /**
     * The m message.
     */
    private CharSequence mMessage;

    /**
     * The m progress.
     */
    private ImageView mProgress;

    /**
     * The m message view.
     */
    private TextView mMessageView;

    /**
     * The container view.
     */
    private View containerView;

    /**
     * Instantiates a new common progress dialog.
     *
     * @param context the context
     */
    public CommonProgressDialog(Context context) {
        super(context);
    }

    /**
     * Instantiates a new common progress dialog.
     *
     * @param context the context
     * @param theme   the theme
     */
    public CommonProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * @param savedInstanceState
     * @see AlertDialog#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        containerView = LayoutInflater.from(getContext())
                                      .inflate(R.layout.dialog_progress, null);
        findViews(containerView);
        setContentView(containerView);
        int width = (int) (DeviceUtils.getDeviceWidthPixels(getContext()) * 0.8);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        containerView.setLayoutParams(lp);
        containerView.setMinimumWidth(width);
        if (mMessage != null) {
            setMessage(mMessage);
        }
    }

    /**
     * Find views.
     *
     * @param view the view
     */
    private void findViews(View view) {
        mMessageView = (TextView) view.findViewById(R.id.common_progress_content);
        mProgress = (ImageView) view.findViewById(R.id.common_progress_bar);
        ((AnimationDrawable) mProgress.getDrawable()).start();
    }

    /**
     * @param titleId
     * @see android.app.Dialog#setTitle(int)
     */
    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getText(titleId));
    }

    /**
     * Sets the button.
     *
     * @param buttonText the button text
     * @param listener   the listener
     */
    public void setButton(int buttonText, OnClickListener listener) {
        setButton((String) getContext().getText(buttonText), listener);
    }

    /**
     * Sets the message.
     *
     * @param messageId the new message
     */
    public void setMessage(int messageId) {
        setMessage(getContext().getText(messageId));
    }

    /**
     * @param message
     * @see AlertDialog#setMessage(CharSequence)
     */
    @Override
    public void setMessage(CharSequence message) {
        if (mProgress != null) {
            mMessageView.setText(message);
        } else {
            mMessage = message;
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            //ignore
        }
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            //ignore
        }
    }
}
