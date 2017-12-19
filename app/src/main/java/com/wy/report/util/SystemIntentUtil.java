package com.wy.report.util;

import java.io.File;

import android.content.Intent;
import android.databinding.repacked.apache.commons.io.FileUtils;
import android.net.Uri;
import android.provider.MediaStore;


public class SystemIntentUtil {


    /**
     * 返回跳转到系统相册页的 intent
     *
     * @return
     */
    public static Intent createJumpIntoSystemAlbumIntent() {
        Intent jumpIntent = new Intent(Intent.ACTION_PICK, null);
        jumpIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return jumpIntent;
    }

    /**
     * 返回跳转到系统相机页的 intent
     *
     * @return
     */
    public static Intent createJumpIntoSystemCameraIntent(String path) {
        Intent jumpIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File outputFile = new File(path);
        jumpIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        jumpIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, true);
        return jumpIntent;
    }

    /**
     * 返回跳转到系统相机页的 intent
     *
     * @param root
     * @param fileName
     * @return
     */
    public static Intent createJumpIntoSystemCameraIntent(String root, String fileName) {
        Intent jumpIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File outputFile = FileUtils.getFile(root, fileName);
        jumpIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        jumpIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, true);
        return jumpIntent;
    }

    /**
     * 返回跳转到系统相机页的 intent
     *
     * @return
     */
    public static Intent createJumpIntoSystemCameraIntent() {
        Intent jumpIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return jumpIntent;
    }
}
