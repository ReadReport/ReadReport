package com.wy.report.helper.retrofit.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/*
 *
 * @author cantalou
 * @date 2017-12-23 21:41
 */
public class PartUtils {

    public static final MediaType JPEG_MEDIA_TYPE = MediaType.parse("image/jpeg");

    public static final String UPLOAD_FIELD_NAME = "image";

    public static MultipartBody.Part[] convertFile2Part(List<File> files) {
        MultipartBody.Part[] fileParts = new MultipartBody.Part[files.size()];
        for (int i = 0; i < files.size(); i++) {
            File        file = files.get(i);
            RequestBody body = RequestBody.create(JPEG_MEDIA_TYPE, file);
            fileParts[i] = MultipartBody.Part.createFormData(UPLOAD_FIELD_NAME, file.getName(), body);
        }
        return fileParts;
    }

    public static MultipartBody.Part[] convertPath2Part(List<String> filePaths) {
        ArrayList<File> files = new ArrayList<>(filePaths.size());
        for (int i = 0; i < filePaths.size(); i++) {
            files.add(new File(filePaths.get(i)));
        }
        return convertFile2Part(files);
    }

    /**
     * bitmap 2 multipart
     *
     * @param bitmap
     * @return
     */
    public static MultipartBody.Part convertBitmap2Part(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[]             datas     = baos.toByteArray();
        RequestBody        body      = RequestBody.create(JPEG_MEDIA_TYPE, datas);

        MultipartBody.Part fileParts = MultipartBody.Part.createFormData("image","header.png",body);
        return fileParts;
    }
}
