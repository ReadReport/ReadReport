package com.wy.report.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wy.report.R;

/*
 *
 * @author cantalou
 * @date 2017-12-24 17:11
 */
public class CommonDialog extends Dialog {
    /**
     * Instantiates a new common dialog.
     *
     * @param context the context
     * @param theme   the theme
     */
    public CommonDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * Instantiates a new common dialog.
     *
     * @param context the context
     */
    public CommonDialog(Context context) {
        super(context, R.style.AppDialog);
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            //异步调用dialog的dismiss,dialog所属的activity已回收导致关闭对话框失败,忽略
        }
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            //异步调用dialog的show,dialog所属的activity已回收导致对话框失败,忽略
        }
    }

    /**
     * Helper class for creating a custom dialog.
     */
    public static class Builder {

        /**
         * The context.
         */
        private Context context;

        /**
         * The content view.
         */
        private View containerView;

        /**
         * The content view.
         */
        private int containerViewLayoutId;

        private View contentView;

        private int contentViewLayoutId;

        /**
         * The positive button text.
         */
        private CharSequence positiveButtonText;

        /**
         * The positive background res.
         */
        private int positiveBackgroundRes = -1;

        /**
         * The positive text color.
         */
        private int positiveTextColor = -1;

        /**
         * The negative button text.
         */
        private CharSequence negativeButtonText;

        /**
         * The negative background res.
         */
        private int negativeBackgroundRes = -1;

        /**
         * The negative text color.
         */
        private int negativeTextColor = -1;

        /**
         * The negative button click listener.
         */
        private DialogInterface.OnClickListener positiveButtonClickListener, negativeButtonClickListener;

        /**
         * Instantiates a new builder.
         *
         * @param context the context
         */
        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set a custom content view for the Dialog. If a message is set, the containerView is not added to the Dialog...
         *
         * @param v the v
         * @return the builder
         */
        public Builder setContainerView(View v) {
            this.containerView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener.
         *
         * @param positiveButtonText the positive button text
         * @param listener           the listener
         * @return the builder
         */
        public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener.
         *
         * @param positiveButtonText the positive button text
         * @param listener           the listener
         * @return the builder
         */
        public Builder setPositiveButton(CharSequence positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener.
         *
         * @param negativeButtonText the negative button text
         * @param listener           the listener
         * @return the builder
         */
        public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener.
         *
         * @param negativeButtonText the negative button text
         * @param listener           the listener
         * @return the builder
         */
        public Builder setNegativeButton(CharSequence negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button background.
         *
         * @param resid the resid
         * @return the builder
         */
        public Builder setPositiveBackgroundResource(int resid) {
            this.positiveBackgroundRes = resid;
            return this;
        }

        /**
         * Set the positive button background.
         *
         * @param resid the resid
         * @return the builder
         */
        public Builder setPositiveTextColor(int resid) {
            this.positiveTextColor = resid;
            return this;
        }

        /**
         * Set the positive button background.
         *
         * @param resid the resid
         * @return the builder
         */
        public Builder setNegativeBackgroundResource(int resid) {
            this.negativeBackgroundRes = resid;
            return this;
        }

        /**
         * Set the positive button background.
         *
         * @param resid the resid
         * @return the builder
         */
        public Builder setNegativeTextColor(int resid) {
            this.negativeTextColor = resid;
            return this;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setContainerViewLayoutId(int containerViewLayoutId) {
            this.containerViewLayoutId = containerViewLayoutId;
            return this;
        }

        public Builder setContentViewLayoutId(int contentViewLayoutId) {
            this.contentViewLayoutId = contentViewLayoutId;
            return this;
        }

        /**
         * Create the custom dialog.
         *
         * @return the common dialog
         */
        public CommonDialog create() {
            final CommonDialog dialog = new CommonDialog(context, R.style.AppDialog);

            if (containerView != null) {
                dialog.setContentView(containerView);
            }
            if (containerViewLayoutId != 0) {
                dialog.setContentView(containerViewLayoutId);
            } else {
                dialog.setContentView(R.layout.view_dialog_container);
            }

            ViewGroup layout = (ViewGroup) dialog.findViewById(R.id.dialog_content);
            if (layout == null) {
                return dialog;
            }

            if (contentView != null) {
                layout.addView(contentView);
            } else if (contentViewLayoutId != 0) {
                LayoutInflater.from(context)
                              .inflate(contentViewLayoutId, layout);
            }

            TextView positive = ((TextView) dialog.findViewById(R.id.dialog_positive_button));
            if (positiveButtonText != null) {
                positive.setText(positiveButtonText);

                if (positiveBackgroundRes == -1) {
                    positiveBackgroundRes = R.drawable.shape_blue_corner_5_right_bottom;
                }
                positive.setBackgroundResource(positiveBackgroundRes);

                if (positiveTextColor == -1) {
                    positiveTextColor = context.getResources()
                                               .getColor(R.color.bai_ffffff);
                }
                positive.setTextColor(positiveTextColor);

                positive.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (positiveButtonClickListener != null) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            dialog.dismiss();
                        }
                    }
                });
            } else {
                positive.setVisibility(View.GONE);
            }

            TextView negative = ((TextView) dialog.findViewById(R.id.dialog_negative_button));
            if (negativeButtonText != null) {
                negative.setText(negativeButtonText);

                if (negativeBackgroundRes == -1) {
                    negativeBackgroundRes = R.drawable.shape_grey_corner_5_left_bottom;
                }
                negative.setBackgroundResource(negativeBackgroundRes);

                if (negativeTextColor == -1) {
                    negativeTextColor = context.getResources()
                                               .getColor(R.color.hui_575757);
                }
                negative.setTextColor(negativeTextColor);

                negative.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (negativeButtonClickListener != null) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                        dialog.dismiss();
                    }
                });
            } else {
                negative.setVisibility(View.GONE);
            }
            return dialog;
        }
    }
}

