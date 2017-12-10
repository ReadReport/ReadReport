package com.wy.report.util;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.wy.report.ReportApplication;

public class SDCardUtils {

    /**
     * sdcard路径
     */
    private static String mAppPath;

    /**
     * 缓存
     */
    private static String mAppCachePath;
    /**
     * sdcard类型
     */
    private static SDCardType mSdcardType;
    /**
     * sdcard路径
     */
    private static String[] mSdcardPath = checkSDCardPath();

    /**
     * 检测sd卡是否存在,包含内置还有外置sdcard
     *
     * @return
     */
    public static boolean checkSDCard() {
        return getSDCardAvailable() > 0;
    }

    /**
     * 获取sdcard路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return mSdcardPath[1];
    }

    /**
     */
    public static String getAppPath() {
        if (TextUtils.isEmpty(mAppPath)) {
            mAppPath = getSDCardPath() + "/" + ReportApplication.getGlobalContext()
                                                                .getPackageName();

            File file = new File(mAppPath);

            if (!file.exists()) {
                file.mkdir();
            }
        }

        return mAppPath;
    }

    /**
     *
     */
    public static String getAppCachePath() {
        if (TextUtils.isEmpty(mAppCachePath)) {
            ReportApplication context = ReportApplication.getGlobalContext();
            mAppCachePath = context.getCacheDir()
                                   .getPath() + "/" + context.getPackageName();
            File file = new File(mAppCachePath);
            if (!file.exists()) {
                file.mkdir();
                FileUtil.chmodAppCacheFile(file);
            }
        }
        return mAppCachePath;
    }

    /**
     * 设置手机的Sdcard路径
     */
    @SuppressWarnings("deprecation")
    public static String[] checkSDCardPath() {
        String externalSdcardPath = getExternalSdcardPath();

        File sdcardDir = null;

        if (TextUtils.isEmpty(externalSdcardPath)) {
            sdcardDir = Environment.getExternalStorageDirectory();
            mSdcardType = SDCardType.INTERNAL;
        } else {
            File path = new File(externalSdcardPath);

            boolean mountSDPathValid = path.exists() && path.isDirectory() && path
                    .canRead() && path.canWrite();

            if (mountSDPathValid) {
                mountSDPathValid = checkSDCardPathAllowWrite(externalSdcardPath);
            }

            boolean isHasExternalSD = false;

            if (mountSDPathValid) {
                StatFs sf = new StatFs(externalSdcardPath);
                isHasExternalSD = sf.getAvailableBlocks() > 1024;
            }

            if (isHasExternalSD) {
                sdcardDir = new File(externalSdcardPath);
                mSdcardType = SDCardType.EXTERNAL;
            } else {
                sdcardDir = Environment.getExternalStorageDirectory();
                mSdcardType = SDCardType.INTERNAL;
            }
        }

        return new String[]{sdcardDir.getPath(), sdcardDir.getAbsolutePath()};
    }

    private static boolean checkSDCardPathAllowWrite(String path) {
        boolean result = false;
        RandomAccessFile buffer = null;

        try {
            File testFile = new File(path + "/test.txt");
            if (!testFile.exists()) {
                buffer = new RandomAccessFile(testFile, "rw");
                buffer.writeChars("ok");
                buffer.close();
                result = true;
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取SD卡，可用容量,如果外置sdcard不存在或者外置SDCard没有空间，则读取内置sdcard的空间
     *
     * @return 返回sdcard可用容量。如果没有装载sdcard，返回-1
     */
    @SuppressWarnings("deprecation")
    public static long getSDCardAvailable() {
        long size = 0;

        StatFs sf;
        long bSize;
        long availBlocks;

        try {
            sf = new StatFs(mSdcardPath[0]);
            bSize = sf.getBlockSize();
            availBlocks = sf.getAvailableBlocks();
            size = bSize * availBlocks;// 可用大小
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (mSdcardType == SDCardType.EXTERNAL) {
            if (size == 0) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                sf = new StatFs(sdcardDir.getPath());
                bSize = sf.getBlockSize();
                availBlocks = sf.getAvailableBlocks();
                size = bSize * availBlocks;// 可用大小
            }
        }

        return size;
    }

    /**
     * 获取SD卡使用信息
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long[] getSDCardUseInfo() {
        long[] sdCardInfo = new long[2];
        try {
            StatFs sf = new StatFs(mSdcardPath[0]);
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long availBlocks = sf.getAvailableBlocks();

            sdCardInfo[0] = bSize * bCount;// 总大小
            sdCardInfo[1] = bSize * availBlocks;// 可用大小
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdCardInfo;
    }

    /**
     * @return 获取挂起外置的sdcard路径, 没有的话则返回空字符
     */
    @SuppressWarnings("resource")
    private static String getExternalSdcardPath() {
        String sdcardPath = "";

        try {
            File fbvodFile = new File("/system/etc/vold.fstab");
            if (fbvodFile.exists()) {
                Scanner scanner = new Scanner(fbvodFile);
                ArrayList<String> sdcardPaths = new ArrayList<String>();
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();

                    if (line.startsWith("dev_mount")) {
                        String[] lineElements = line.split(" ");
                        String element = lineElements[2];
                        if ((lineElements[1].contains("sdcard"))) {
                            sdcardPaths.add(element);
                        }
                    }
                }

                if (sdcardPaths.size() == 2) {
                    sdcardPath = sdcardPaths.get(1);
                } else if (sdcardPaths.size() == 1) {
                    sdcardPath = sdcardPaths.get(0);
                }

                /**
                 * 如果外置和内置sdcard位置一样的话，则返回空字符串
                 */
                if (sdcardPath.equals(Environment.getExternalStorageDirectory()
                                                 .getPath())) {
                    sdcardPath = "";
                }
            }

            /**
             * 如果还获取不到外置sdcard的路径时,尝试用第二种获取外置sdcard路径的方法
             */
            if (TextUtils.isEmpty(sdcardPath)) {
                sdcardPath = getExternalSdcardPathSomeMachine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sdcardPath;
    }

    /**
     * 部分设备用此方法获取外置sdcard路径
     *
     * @return
     */
    private static String getExternalSdcardPathSomeMachine() {
        String externalSdcardPath = "";

        Map<String, String> map = System.getenv();
        Set<String> set = map.keySet();
        Iterator<String> keys = set.iterator();

        while (keys.hasNext()) {
            String key = keys.next();
            String value = map.get(key);

            if ("SECONDARY_STORAGE".equals(key)) {
                externalSdcardPath = value;
            }
        }

        return externalSdcardPath;
    }

    /**
     * sdcard类型
     */
    public enum SDCardType {
        INTERNAL, EXTERNAL
    }
}
