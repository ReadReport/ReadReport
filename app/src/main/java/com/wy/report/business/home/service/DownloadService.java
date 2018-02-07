package com.wy.report.business.home.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-2-7 下午4:22
 * @description: ReadReport
 */
public interface DownloadService {

    @GET
    Observable<ResponseBody> downloadApk(@Url String fileUrl);
}
