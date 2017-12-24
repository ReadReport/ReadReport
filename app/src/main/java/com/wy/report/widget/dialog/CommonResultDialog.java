package com.wy.report.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wy.report.R;

/*
 *
 * @author cantalou
 * @date 2017-12-24 21:01
 */
public class CommonResultDialog extends CommonDialog {

    public CommonResultDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder extends CommonDialog.Builder {

        private int resultIconId;

        private String resultTips1;

        private int resultTips1Id;

        private String resultTips2;

        private int resultTips2Id;


        public Builder(Context context) {
            super(context);
        }

        @Override
        public CommonDialog create() {
            CommonDialog dialog = super.create();
            ImageView icon = (ImageView) dialog.findViewById(R.id.dialog_result_icon);
            icon.setImageResource(resultIconId);

            TextView tv1 = (TextView) dialog.findViewById(R.id.dialog_result_tips1);
            if (resultTips1 == null) {
                resultTips1 = dialog.getContext()
                                    .getString(resultTips1Id);
            }
            tv1.setText(resultTips1);

            TextView tv2 = (TextView) dialog.findViewById(R.id.dialog_result_tips2);
            if (resultTips2 == null) {
                if (resultTips2Id != 0) {
                    resultTips2 = dialog.getContext()
                                        .getString(resultTips2Id);
                }
            }
            if (resultTips2 == null) {
                tv2.setVisibility(View.GONE);
            } else {
                tv2.setText(resultTips2);
            }
            return dialog;
        }

        public void setResultIconId(int resultIconId) {
            this.resultIconId = resultIconId;
        }

        public void setResultTips1(String resultTips1) {
            this.resultTips1 = resultTips1;
        }

        public void setResultTips2(String resultTips2) {
            this.resultTips2 = resultTips2;
        }

        public void setResultTips1Id(int resultTips1Id) {
            this.resultTips1Id = resultTips1Id;
        }

        public void setResultTips2Id(int resultTips2Id) {
            this.resultTips2Id = resultTips2Id;
        }
    }

}
