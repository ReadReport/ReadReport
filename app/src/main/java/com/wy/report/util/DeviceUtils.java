package com.wy.report.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/*
 *
 * @author cantalou
 * @date 2017-12-09 17:56
 */
public class DeviceUtils {
    /**
     * 获取内部存储的大小
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long[] getSystem() {
        File root = Environment.getDataDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long blockCount = sf.getBlockCount();
        long availCount = sf.getAvailableBlocks();
        return new long[]{blockSize * blockCount, availCount * blockSize};
    }

}
