package com.wy.report.helper.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

import com.wy.report.R;
import com.wy.report.widget.dialog.CommonResultDialog;

/*
 *
 * @author cantalou
 * @date 2017-12-24 13:06
 */
public class DialogHelper {

    public static Dialog showReportUploadSuccessDialog(Context context, OnClickListener negative, OnClickListener positive) {
        CommonResultDialog.Builder builder = new CommonResultDialog.Builder(context);
        builder.setContentViewLayoutId(R.layout.view_dialog_result);
        builder.setNegativeButton(R.string.report_interpret_later, negative);
        builder.setPositiveButton(R.string.report_interpret_now, positive);
        builder.setResultIconId(R.drawable.msg_success);
        builder.setResultTips1Id(R.string.report_upload_success_tips1);
        builder.setResultTips2Id(R.string.report_upload_success_tips2);
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static Dialog showReportUploadConfirmDialog(Context context, OnClickListener negative, OnClickListener positive) {
        CommonResultDialog.Builder builder = new CommonResultDialog.Builder(context);
        builder.setContentViewLayoutId(R.layout.view_dialog_result);
        builder.setNegativeButton(R.string.report_upload_lack_info_leave, negative);
        builder.setPositiveButton(R.string.report_upload_lack_info_continue, positive);
        builder.setResultIconId(R.drawable.msg_prompt);
        builder.setResultTips1Id(R.string.report_upload_lack_info_tips1);
        builder.setResultTips2Id(R.string.report_upload_lack_info_tips2);
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

}
