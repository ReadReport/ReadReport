package com.wy.report.helper.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.wy.report.business.home.model.AppUpdateMode;
import com.wy.report.business.home.service.DownloadService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.util.LogUtils;
import com.wy.report.util.SDCardUtils;
import com.wy.report.util.ToastUtils;
import com.wy.report.widget.dialog.CommonProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-2-7 上午11:45
 * @description: ReadReport
 */
public class AppUpdateHelper {

    private Context mContext;
    private Dialog  mDialog;

    private DownloadService mDownloadService;

    private String               filePath;
    private CommonProgressDialog mProgressDialog;


    public AppUpdateHelper(Context context) {
        mContext = context;
        mDownloadService = RetrofitHelper.getInstance().create(DownloadService.class);
    }

    public void checkUpdate(AppUpdateMode appUpdateMode) {
        int versionCode = getVersionCode(mContext);
//        if (versionCode < 0) {
            return;
//        }
//        int serverCode = appUpdateMode.getVersionCode();
//        if (serverCode > versionCode) {
//            showDialog(appUpdateMode);
//        }
    }

    public void showDialog(final AppUpdateMode appUpdateMode) {
        if (mDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext).setMessage(appUpdateMode.getUpdateInfo()).setTitle("版本更新");
            if (appUpdateMode.isForceUpload()) {
                builder.setCancelable(false);
            } else {
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                    }
                });
            }
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mDialog.dismiss();
                    filePath = getFilePath(appUpdateMode.getVersionCode());
                    if (appUpdateMode.isForceUpload()) {
                        mProgressDialog = new CommonProgressDialog(mContext);
                        mProgressDialog.setCancelable(false);
                        mProgressDialog.show();
                    }
//                    if (checkExists(filePath)) {
//                        installApk(mContext, filePath);
//                        return;
//                    }
                    mDownloadService.downloadApk(appUpdateMode.getApkDownloadUrl()).subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            if (appUpdateMode.isForceUpload()) {
                                mDialog.show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            LogUtils.v(responseBody.contentLength());
                            ToastUtils.showLong(String.valueOf(responseBody.contentLength()));

                            if (writeResponseBodyToDisk(responseBody)) {
                                installApk(mContext, filePath);
                            }
                        }
                    });
                }
            });

            mDialog = builder.create();
        }
        mDialog.show();

    }


    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String getFilePath(int versionCode) {
        String filePath = SDCardUtils.getSDCardPath() + File.separator + "ReadReportV" + versionCode + ".apk";
        return filePath;
    }

    private boolean checkExists(String filePath) {
        try {
            return new File(filePath).exists();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File futureStudioIconFile = new File(filePath);

            InputStream  inputStream  = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[1024];

                long fileSize           = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }
                LogUtils.w("saveFile", "file download: " + fileSizeDownloaded + " of " + fileSize);
                LogUtils.w("FilePath", filePath);

                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 安装 APK。
     *
     * @param filePath APK 文件路径
     */
    public static void installApk(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filePath)),
                              "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

}
