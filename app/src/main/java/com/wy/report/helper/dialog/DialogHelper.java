package com.wy.report.helper.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.wy.report.R;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.widget.dialog.CommonDialog;
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

    public static Dialog showReportQueryConfirmDialog(Context context, OnClickListener positive) {
        CommonResultDialog.Builder builder = new CommonResultDialog.Builder(context);
        builder.setContentViewLayoutId(R.layout.view_dialog_result);
        builder.setPositiveButton(R.string.confirm, positive);
        builder.setPositiveBackgroundResource(R.drawable.selector_btn_blue_corner_5_bottom);
        builder.setResultIconId(R.drawable.msg_success);
        builder.setResultTips1Id(R.string.report_query_success_tips1);
        builder.setResultTips2Id(R.string.report_query_success_tips2);
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static Dialog showChoosePictureMenu(final Context context, final Bundle param) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setContainerViewLayoutId(R.layout.view_dialog_picture_choose);
        final Dialog dialog = builder.create();
        dialog.getWindow()
              .getAttributes().gravity = Gravity.BOTTOM;
        dialog.findViewById(R.id.view_dialog_choose_picture_album)
              .setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      AuthRouterManager.getInstance()
                                       .getRouter()
                                       .open(context, AuthRouterManager.ROUTER_PICTURE_CHOOSE_CATEGORY, param);
                      dialog.dismiss();
                  }
              });
        dialog.findViewById(R.id.view_dialog_choose_picture_camera)
              .setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      AuthRouterManager.getInstance()
                                       .getRouter()
                                       .open(context, AuthRouterManager.ROUTER_PICTURE_CHOOSE_CAMERA_PREVIEW, param);
                      dialog.dismiss();
                  }
              });
        dialog.findViewById(R.id.view_dialog_choose_picture_cancel)
              .setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      dialog.dismiss();
                  }
              });
        dialog.show();
        return dialog;
    }
}
