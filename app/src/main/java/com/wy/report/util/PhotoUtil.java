package com.wy.report.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;

import com.cantalou.android.util.BitmapUtils;
import com.wy.report.ReportApplication;
import com.wy.report.base.constant.ActivityRequestCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class PhotoUtil {

    private static final String TAG = "PhotoUtil";

    private static final String PHOTO_UTIL_ON_SAVE_INSTANCE_STATE_KEY = "PHOTO_UTIL_ON_SAVE_INSTANCE_STATE_KEY";

    /**
     * 生成的图片地址
     */
    private static HashMap<String, String> generatedPaths = new HashMap<String, String>();

    /**
     * 获取用户从相册中选择的图片的地址
     *
     * @param context
     * @param data
     * @return 图片的地址, 失败返回null
     */
    public static String onActivityResultForOpenAlbum(Context context, Intent data) {
        if (data == null) {
            return null;
        }

        Uri selectedImage = data.getData();
        if (selectedImage == null) {
            return null;
        }

        return FileUtil.getPath(context, selectedImage);
    }

    /**
     * 生成照片的路径
     *
     * @return
     * @date 2015年5月21日 上午11:19:19
     */
    public static String generatePhotoPath() {
        String photoName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".jpg";
        File file = FileUtil.getFile("/camera", photoName);
        return file.getAbsolutePath();
    }


    /**
     * 保存图片到相册,返回路径
     *
     * @Title: PhotoUtil.java
     * @date 2015年6月3日 下午5:51:41
     */
    public static String saveImageToGallery(Bitmap bmp) {

        String rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                                     .getPath();
        if (!new File(rootPath).exists()) {
            return null;
        }
        // 首先保存图片,首先保存在Camera文件夹下，没有就保存在100MEDIA文件夹下，在没有就保存在DCIM文件夹下
        String cameraPath = rootPath + "/Camera";
        String mediaPath = rootPath + "/100MEDIA";
        File cFile = new File(cameraPath);
        File mFile = new File(mediaPath);
        File picFile;
        if (cFile.exists()) {
            picFile = new File(cFile, String.valueOf(System.currentTimeMillis()) + ".jpg");
        } else if (mFile.exists()) {
            picFile = new File(mFile, String.valueOf(System.currentTimeMillis()) + ".jpg");
        } else {
            picFile = new File(rootPath + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        }

        try {
            FileOutputStream fos = new FileOutputStream(picFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return picFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 打开图库
     *
     * @param activity
     */
    public static void openAlbum(Activity activity) {
        Intent intent = SystemIntentUtil.createJumpIntoSystemAlbumIntent();
        try {
            activity.startActivityForResult(intent, ActivityRequestCode.CODE_OPEN_ALBUM);
        } catch (Exception e) {
        }
    }

    /**
     * 打开相机
     *
     * @param activity
     * @return 有sd卡的手机返回拍照后图片保存路径; 无sd卡手机返回null,拍照结果通过intent的data返回
     */
    public static String openCamera(Activity activity) {
        Intent intent;
        String path = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path = generatePhotoPath();
            intent = SystemIntentUtil.createJumpIntoSystemCameraIntent(path);
        } else {
            intent = SystemIntentUtil.createJumpIntoSystemCameraIntent();
        }

        try {
            activity.startActivityForResult(intent, ActivityRequestCode.CODE_OPEN_CAMERA);
        } catch (Exception e) {
            ToastUtils.showShort("相机打开失败");
            path = null;
        }
        generatedPaths.put(activity.getLocalClassName(), path);
        return path;
    }

    /**
     * 打开相机
     *
     * @param fragment
     * @return 有sd卡的手机返回拍照后图片保存路径; 无sd卡手机返回null,拍照结果通过intent的data返回
     */
    public static String openCamera(Fragment fragment) {
        Intent intent;
        String path = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path = generatePhotoPath();
            intent = SystemIntentUtil.createJumpIntoSystemCameraIntent(path);
        } else {
            intent = SystemIntentUtil.createJumpIntoSystemCameraIntent();
        }

        try {
            fragment.startActivityForResult(intent, ActivityRequestCode.CODE_OPEN_CAMERA);
        } catch (Exception e) {
            ToastUtils.showShort("相机打开失败");
            path = null;
        }
        generatedPaths.put(fragment.getClass().getName(), path);
        return path;
    }

    /**
     * 处理相册和拍照的onActivityResult回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static String onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return null;
        }
        String path = null;
        if (requestCode == ActivityRequestCode.CODE_OPEN_ALBUM) {
            path = onActivityResultForOpenAlbum(activity, data);
        } else if (requestCode == ActivityRequestCode.CODE_OPEN_CAMERA) {
            path = generatedPaths.get(activity.getLocalClassName());
            if (StringUtils.isBlank(path)) {
                Bitmap bmp = null;
                if(data == null){
                    ToastUtils.showShort("照片保存失败");
                    return null;
                }
                Bundle extras = data.getExtras();
                if (extras != null && (bmp = extras.getParcelable("data")) != null) {
                    path = generatePhotoPath();
                    BitmapUtils.saveBitmap2file(bmp, path);
                    BitmapUtils.destroyBitmap(bmp);
                }
            }else{
                MediaScanner.scanFile(path, MediaScanner.TYPE_IMG);
            }
        }
        return path;
    }

    /**
     * 处理相册和拍照的onActivityResult回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static String onActivityResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return null;
        }
        String path = null;
        if (requestCode == ActivityRequestCode.CODE_OPEN_ALBUM) {
            path = onActivityResultForOpenAlbum(fragment.getActivity(), data);
        } else if (requestCode == ActivityRequestCode.CODE_OPEN_CAMERA) {
            path = generatedPaths.get(fragment.getClass().getName());
            if (StringUtils.isBlank(path)) {
                Bitmap bmp = null;
                Bundle extras = data.getExtras();
                if (extras != null && (bmp = extras.getParcelable("data")) != null) {
                    path = generatePhotoPath();
                    BitmapUtils.saveBitmap2file(bmp, path);
                    BitmapUtils.destroyBitmap(bmp);
                }
            }else{
                MediaScanner.scanFile(path, MediaScanner.TYPE_IMG);
            }
        }
        return path;
    }

    public static synchronized void onSaveInstanceState(Bundle outState) {
        if (generatedPaths != null && generatedPaths.size() > 0) {
            outState.putSerializable(PHOTO_UTIL_ON_SAVE_INSTANCE_STATE_KEY, generatedPaths);
        }
    }

    public static synchronized void onRestoreInstanceState(Bundle savedInstanceState) {
        HashMap<String, String> maps = (HashMap<String, String>) savedInstanceState.getSerializable(PHOTO_UTIL_ON_SAVE_INSTANCE_STATE_KEY);
        if (maps != null && maps.size() > 0) {
            generatedPaths = maps;
        }
    }

    public static class MediaScanner
    {

        public final static String TYPE_IMG = "image/jpeg";


        public static void scanFile(final String path, final String type)
        {
            Context context = ReportApplication.getGlobalContext();
            MediaScannerConnection.scanFile(context, new String[]{path}, new String[]{type}, null);
            if (Build.MODEL.equalsIgnoreCase("vivo X6D"))
            {
                try
                {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(Uri.fromFile(new File(path)));
                    context.sendBroadcast(mediaScanIntent);
                }
                catch (Exception e)
                {
                }
            }
        }
    }
}
