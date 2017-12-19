package com.wy.report.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.wy.report.ReportApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class FileUtil {

    private static final int DEFAULT_BUF_SIZE = 8 * 1024;
    /**
     * 图片文件过滤器
     */
    public static FileFilter imagefileFilter = new FileFilter() {
        public boolean accept(File pathname) {
            String tmp = pathname.getName()
                                 .toLowerCase(Locale.CHINA);
            if (tmp.endsWith(".png") || tmp.endsWith(".jpg") || tmp.endsWith(".bmp") || tmp.endsWith(".gif") || tmp.endsWith(".jpeg")) {
                return true;
            }
            return false;
        }
    };
    /**
     * 铃声文件过滤器
     */
    public static FileFilter mp3fileFilter = new FileFilter() {
        public boolean accept(File pathname) {
            String tmp = pathname.getName()
                                 .toLowerCase(Locale.CHINA);
            if (tmp.endsWith(".mp3")) {
                return true;
            }
            return false;
        }
    };
    /**
     * 保留当getFile方法被调用时候，所对应文件的完整路径
     */
    private static String sPathOnceGetFileCalled;

    /**
     * 获取完整路径的文件对象
     *
     * @param root
     * @param fileName
     * @return
     */
    public static File getFile(String root, String fileName) {
        String dir = null;

        int index = fileName.lastIndexOf(File.separator);
        if (index != -1) {
            dir = fileName.substring(0, index);
            fileName = fileName.substring(index + 1, fileName.length());
        }

        if (dir != null && TextUtils.isEmpty(dir.trim())) {
            dir = null;
        }

        File dirFile = getDir(root, dir);
        File file = new File(dirFile, fileName);

        sPathOnceGetFileCalled = file.getAbsolutePath();

        return file;
    }

    /**
     * 目录的名称 如果dir为空，则返回root目录
     *
     * @param root
     * @param dir
     * @return
     */
    public static File getDir(String root, String dir) {
        if (root == null) {
            root = "";
        }

        File file = null;
        if (SDCardUtils.checkSDCard()) {
            StringBuilder path = new StringBuilder(SDCardUtils.getAppPath());
            path.append(root);
            if (dir == null) {
                dir = "";
            }
            path.append(File.separatorChar);
            path.append(dir);
            file = new File(path.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
        } else if (!TextUtils.isEmpty(SDCardUtils.getAppCachePath())) {
            StringBuilder path = new StringBuilder(SDCardUtils.getAppCachePath());
            path.append(root);
            if (dir == null) {
                dir = "";
            }
            path.append(File.separatorChar);
            path.append(dir);
            file = new File(path.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file;
    }

    /**
     * 创建文件夹
     */
    public static String createDir(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }

        return dir;
    }

    /**
     * 往创建文件，并往文件中写内容
     */
    public static void writeFile(String path, String content, boolean append) {
        FileWriter fw = null;
        try {
            File f = new File(path);
            f.mkdirs();
            fw = new FileWriter(f, append);
            if ((content != null) && !"".equals(content)) {
                fw.write(content);
                fw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSilent(fw);
        }
    }

    /**
     * 删除某个文件
     *
     * @param path 文件路径
     */
    public static void delFile(String path) {
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File f = new File(filePath);
            f.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹，返回是否成功
     *
     * @param folderPath
     */
    public static boolean deleteFolder(String folderPath) {
        if (delAllFile(folderPath)) // 删除完里面所有内容
        {
            String filePath = folderPath;
            filePath = filePath.toString();
            File f = new File(filePath);
            if (f.delete()) // 删除空文件夹
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 文件复制类
     *
     * @param srcFile  源文件
     * @param destFile 目录文件
     * @return 是否复制成功
     */
    public static boolean copy(String srcFile, String destFile) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] bytes = new byte[DEFAULT_BUF_SIZE];
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeSilent(in, out);
        }
    }

    /**
     * 文件复制类
     *
     * @param inputStream 源文件
     * @param destFile    目录文件
     * @return 是否复制成功
     */
    public static boolean copy(InputStream inputStream, String destFile) {
        OutputStream out = null;
        try {
            if (!(inputStream instanceof BufferedInputStream)) {
                inputStream = new BufferedInputStream(inputStream);
            }
            out = new BufferedOutputStream(new FileOutputStream(destFile));
            byte[] bytes = new byte[DEFAULT_BUF_SIZE];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            closeSilent(out);
        }
    }

    /**
     * 获取流内容
     *
     * @param inputStream 源文件
     * @return 是否复制成功
     */
    public static String getContent(InputStream inputStream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            if (!(inputStream instanceof BufferedInputStream)) {
                inputStream = new BufferedInputStream(inputStream);
            }
            byte[] bytes = new byte[DEFAULT_BUF_SIZE];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, len);
                out.flush();
            }
            return new String(out.toByteArray());
        } catch (Exception e) {
            return "";
        } finally {
            closeSilent(out);
        }
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
        File a = new File(oldPath);
        String[] file = a.list();
        if (null == file) {
            return;
        }
        File temp = null;
        for (int i = 0; i < file.length; i++) {
            FileInputStream input = null;
            FileOutputStream output = null;
            try {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    input = new FileInputStream(temp);
                    output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[DEFAULT_BUF_SIZE];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            } finally {
                closeSilent(output, input);
            }
        }
    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String 如：c:/fqf.txt
     * @param newPath String 如：d:/fqf.txt
     */
    public static void moveFile(String oldPath, String newPath) {
        copy(oldPath, newPath);
        delFile(oldPath);

    }

    /**
     * 重命名文件或文件夹
     *
     * @param resFilePath 源文件路径
     * @param newFilePath 重命名
     * @return 操作成功标识
     */
    public static boolean renameFile(String resFilePath, String newFilePath) {
        File resFile = new File(resFilePath);
        File newFile = new File(newFilePath);
        return resFile.renameTo(newFile);
    }

    /**
     * 移动文件夹到指定目录
     *
     * @param oldPath String 如：c:/fqf
     * @param newPath String 如：d:/fqf
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    /**
     * <br>
     * Description: 将输入流保存为文件 <br>
     * Author:caizp <br>
     * Date:2011-7-19下午02:37:25
     *
     * @param in
     * @param fileName 文件名称
     */
    public static void saveStream2File(InputStream in, String fileName) {
        int size;
        byte[] buffer = new byte[1000];
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            while ((size = in.read(buffer)) > -1) {
                bufferedOutputStream.write(buffer, 0, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSilent(bufferedOutputStream);
        }
    }

    public static File[] getFilesFromDir(String dirPath, FileFilter fileFilter) {
        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            if (fileFilter != null) {
                return dir.listFiles(fileFilter);
            } else {
                return dir.listFiles();
            }
        }
        return null;
    }

    /**
     * <br>
     * Description: 获取已存在的文件名列表 <br>
     * Author:caizp <br>
     * Date:2011-8-10上午11:22:07
     *
     * @param dir
     * @param fileFilter
     * @param hasSuffix
     * @return
     */
    public static List<String> getExistsFileNames(String dir, FileFilter fileFilter, boolean hasSuffix) {
        String path = dir;
        File file = new File(path);
        File[] files = file.listFiles(fileFilter);
        List<String> fileNameList = new ArrayList<String>();
        if (null != files) {
            for (File tmpFile : files) {
                String tmppath = tmpFile.getAbsolutePath();
                String fileName = getFileName(tmppath, hasSuffix);
                fileNameList.add(fileName);
            }
        }
        return fileNameList;
    }

    /**
     * 描述: 获取已存在的文件名列表 (包括子目录)
     *
     * @param dir
     * @param hasSuffix
     * @param suffix    后缀名
     * @return
     * @Since 2012-6-6
     */
    public static List<String> getAllExistsFileNames(String dir, boolean hasSuffix, String[] suffix) {
        String path = dir;
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> fileNameList = new ArrayList<String>();
        if (null != files) {
            for (File tmpFile : files) {
                if (tmpFile.isDirectory()) {
                    fileNameList.addAll(getAllExistsFileNames(tmpFile.getPath(), hasSuffix, suffix));
                } else {
                    String tmp = tmpFile.getName()
                                        .toLowerCase(Locale.CHINA);
                    if (suffix != null) {
                        for (String s : suffix) {
                            if (tmp.endsWith(s)) {
                                fileNameList.add(tmpFile.getAbsolutePath());
                            }
                        }
                    } else {
                        fileNameList.add(tmpFile.getAbsolutePath());
                    }
                }
            }
        }
        return fileNameList;
    }

    /**
     * 从路径中获取 文件名
     *
     * @param path
     * @param hasSuffix 是否包括后缀
     * @return
     */
    public static String getFileName(String path, boolean hasSuffix) {
        if (null == path || -1 == path.lastIndexOf("/") || -1 == path.lastIndexOf(".")) {
            return null;
        }
        if (!hasSuffix) {
            return path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
        } else {
            return path.substring(path.lastIndexOf("/") + 1);
        }
    }

    /**
     * 从路径中获取 文件名后缀名
     *
     * @param path
     * @return 文件后缀名
     */
    public static String getFileSuffix(String path) {
        int dotIndex = -1;
        if (StringUtils.isBlank(path) || -1 == (dotIndex = path.lastIndexOf(".")) || dotIndex == path.length() - 1) {
            return null;
        }
        return path.substring(dotIndex + 1);
    }

    /**
     * 获取目录
     *
     * @param path
     * @return
     */
    public static String getPath(String path) {
        File file = new File(path);

        try {
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定目录下是否存在指定名称的文件
     *
     * @param dir      目录
     * @param fileName 文件名称
     * @return
     */
    public static boolean isFileExits(String dir, String fileName) {
        fileName = fileName == null ? "" : fileName;
        dir = dir == null ? "" : dir;
        int index = dir.lastIndexOf("/");
        String filePath;
        if (index == dir.length() - 1) {
            filePath = dir + fileName;
        } else {
            filePath = dir + "/" + fileName;
        }
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 指定路么下是否存在文件
     *
     * @param filePath 文件路径
     * @return
     */
    public static boolean isFileExits(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 保存图片
     *
     * @param dirPath
     * @param fileName
     * @param bmp
     * @return
     */
    public static boolean saveImageFile(String dirPath, String fileName, Bitmap bmp) {
        try {
            File dir = new File(dirPath);

            // 目录不存时创建目录
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();
                if (flag == false) {
                    return false;
                }
            }

            // 未指定文件名时取当前毫秒作为文件名
            if (fileName == null || fileName.trim()
                                            .length() == 0) {
                fileName = System.currentTimeMillis() + ".jpg";
            }
            File picPath = new File(dirPath, fileName);
            FileOutputStream fos = new FileOutputStream(picPath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存图片,带保存格式：jpg\png
     *
     * @param dirPath
     * @param fileName
     * @param bmp
     * @param format   保存的格式
     * @return
     */
    public static boolean saveImageFile(String dirPath, String fileName, Bitmap bmp, Bitmap.CompressFormat format) {
        try {
            File dir = new File(dirPath);

            // 目录不存时创建目录
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();
                if (flag == false) {
                    return false;
                }
            }

            format = format == null ? Bitmap.CompressFormat.JPEG : format;
            // 未指定文件名时取当前毫秒作为文件名
            if (fileName == null || fileName.trim()
                                            .length() == 0) {
                fileName = System.currentTimeMillis() + "";
                if (format.equals(Bitmap.CompressFormat.PNG)) {
                    fileName += ".png";
                } else {
                    fileName += ".jpg";
                }
            }
            File picPath = new File(dirPath, fileName);
            FileOutputStream fos = new FileOutputStream(picPath);
            bmp.compress(format, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取文件夹总大小 B单位
     *
     * @param path
     * @return
     */
    public static long getFileAllSize(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                long size = 0;
                for (File f : children) {
                    size += getFileAllSize(f.getPath());
                }
                return size;
            } else {
                long size = file.length();
                return size;
            }
        } else {
            return 0;
        }

    }

    /**
     * 读取文件内容
     *
     * @param path
     * @return
     */
    public static String readFileContent(String path) {
        StringBuffer sb = new StringBuffer();
        if (!isFileExits(path)) {
            return sb.toString();
        }
        InputStream ins = null;
        try {
            ins = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSilent(ins);
        }
        return sb.toString();
    }

    public static String getMemorySizeString(long size) {
        float result = size;
        if (result < 1024) {
            BigDecimal temp = new BigDecimal(result);
            temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
            return temp + "Bytes";
        } else {
            result = result / 1024;
            if (result < 1024) {
                BigDecimal temp = new BigDecimal(result);
                temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                return temp + "KB";
            } else {
                result = result / 1024;
                if (result < 1024) {
                    BigDecimal temp = new BigDecimal(result);
                    temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                    return temp + "MB";
                } else {
                    result = result / 1024;
                    if (result < 1024) {
                        BigDecimal temp = new BigDecimal(result);
                        temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                        return temp + "GB";
                    } else {
                        result = result / 1024;
                        BigDecimal temp = new BigDecimal(result);
                        temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                        return temp + "TB";
                    }
                }
            }
        }
    }

    public static String getMemoryPercentString(float percent) {
        BigDecimal result = new BigDecimal(percent * 100.0f);
        return result.setScale(2, BigDecimal.ROUND_HALF_UP) + "%";
    }

    /**
     * 从assets目录复制文件到指定路径
     *
     * @param context
     * @param srcFileName    复制的文件名
     * @param targetDir      目标目录
     * @param targetFileName 目标文件名
     * @return
     */
    public static void copyAssetsFile(Context context, String srcFileName, String targetDir, String targetFileName) throws IOException {
        AssetManager asm = context.getAssets();
        FileOutputStream fos = null;
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(asm.open(srcFileName));
            createDir(targetDir);
            File targetFile = new File(targetDir, targetFileName);
            if (targetFile.exists()) {
                targetFile.delete();
            }

            fos = new FileOutputStream(targetFile);
            byte[] buffer = new byte[DEFAULT_BUF_SIZE];
            int len = 0;
            while ((len = dis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
        } finally {
            closeSilent(fos, dis);
        }
    }

    /**
     * 解压模板包
     */
    public static boolean unzip(String unzipFile, String unzipTarget) {
        ZipInputStream zipStream = null;
        BufferedOutputStream bufOut = null;
        try {
            Thread current = Thread.currentThread();

            //创建解压目录
            File unzipDir = new File(unzipTarget);
            if (!unzipDir.exists()) {
                unzipDir.mkdirs();
            }

            zipStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(unzipFile)));
            ZipEntry zEntry = null;
            while ((zEntry = zipStream.getNextEntry()) != null) {
                if (current.interrupted()) {
                    throw new InterruptedException("Thread interrupted ");
                }

                if (zEntry.isDirectory()) {
                    String dirPath = unzipTarget + "/" + zEntry.getName();
                    createDir(dirPath);
                } else {
                    File targetFile = new File(unzipTarget + "/" + zEntry.getName());

                    bufOut = new BufferedOutputStream(new FileOutputStream(targetFile));
                    byte[] buffer = new byte[DEFAULT_BUF_SIZE];
                    int len;
                    while ((len = zipStream.read(buffer)) != -1) {
                        if (current.interrupted()) {
                            //在某些机型下,read()方法会一直返回0, 导致死循环,通过interrupt退出
                            throw new InterruptedException("Thread interrupted ");
                        }
                        bufOut.write(buffer, 0, len);
                        bufOut.flush();
                    }
                    closeSilent(bufOut);
                    zipStream.closeEntry();
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            closeSilent(zipStream, bufOut);
        }
    }

    /**
     * ios参数不支持4.1版本以下的Cursor游标的关闭
     *
     * @param ios
     */
    public static void closeSilent(Closeable... ios) {
        if (ios == null) {
            return;
        }
        for (Closeable io : ios) {
            if (io != null) {
                try {
                    io.close();
                } catch (Exception e) {
                    //ignore
                }
            }
        }
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.<br>
     * <br>
     * Callers should check whether the path is local before assuming it
     * represents a local file.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    @SuppressLint("NewApi")
    public static String getPath(Context context, final Uri uri) {
        boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if ("com.ianhanniballake.localstorage.documents".equals(uri.getAuthority())) {
                return DocumentsContract.getDocumentId(uri);
            } else if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                String id = DocumentsContract.getDocumentId(uri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                return getDataColumn(context, contentUri, "_id=?", new String[]{split[1]});
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if ("com.google.android.apps.photos.content".equals(uri.getAuthority())) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     * @author paulburke
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        try {
            cursor = context.getContentResolver()
                            .query(uri, new String[]{column}, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static boolean deleteFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            return deleteDir(new File(filePath));
        } else {
            return false;
        }
    }

    /**
     * 删除一个文件，或者一个目录下的所有文件
     *
     * @param file 文件或者目录
     */
    public static boolean deleteDir(File file) {
        try {
            if (file == null || !file.exists()) {
                return true;
            }
            if (file.exists() && file.isDirectory()) {
                File[] files = file.listFiles();
                if (files.length > 0) {
                    for (File child : files) {
                        deleteDir(child);
                    }
                }
                if (file.listFiles().length == 0) {
                    return file.delete();
                }
            } else if (file.isFile()) {
                return file.delete();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * 获取完整路径的文件对象
     *
     * @param root
     * @param fileName
     * @return
     */
    public static File getFile2(String root, String fileName) {
        if (root == null) {
            root = "";
        }

        int warningSize = 1;
        if (SDCardUtils.checkSDCard()
                && (SDCardUtils.getSDCardUseInfo()[1] / 1024 / 1024 > warningSize)) {

            StringBuilder sdCardImageCacheDir = new StringBuilder(
                    SDCardUtils.getAppPath()).append(root);
            File sdCardImageCacheDirFile = new File(
                    sdCardImageCacheDir.toString());

            // 如果不存在或者不是一个目录,则新建目录
            if (!sdCardImageCacheDirFile.exists()
                    || !sdCardImageCacheDirFile.isDirectory()) {
                boolean isMkDirSuccess = sdCardImageCacheDirFile.mkdirs();
                if (isMkDirSuccess) {
                    File sdCardImageAbsoluteFile = new File(
                            sdCardImageCacheDirFile.getAbsolutePath()
                                    + fileName);
                    sPathOnceGetFileCalled = sdCardImageAbsoluteFile.getAbsolutePath();
                    return sdCardImageAbsoluteFile;
                } else {
                }
            } else {
                File sdCardImageAbsoluteFile = new File(
                        sdCardImageCacheDirFile.getAbsolutePath()
                                + File.separator + fileName);
                return sdCardImageAbsoluteFile;
            }
        } else if ((DeviceUtils.getSystem()[1] / 1024 / 1024) > warningSize) {
            String cachePath = ReportApplication.getGlobalContext()
                                                .getCacheDir()
                                                .getPath();
            File appCahceImageAbsoluteFile = new File(cachePath
                    + File.separatorChar + fileName);
            sPathOnceGetFileCalled = appCahceImageAbsoluteFile
                    .getAbsolutePath();

            return appCahceImageAbsoluteFile;
        } else {
        }
        return null;
    }

    public static String getPathOnceGetFileCalled() {
        return sPathOnceGetFileCalled;
    }

    /**
     * 修改缓存目录或文件的访问权限
     *
     * @param file 要修改的目录或文件
     */
    public static void chmodAppCacheFile(File file) {
        if (file == null) {
            return;
        }
        // 修改文件节点 权限；
        if (Build.VERSION.SDK_INT >= 9) {
            file.setReadable(true, false);
            file.setExecutable(true, false);
        } else {
            try {
                Runtime.getRuntime()
                       .exec("chmod 777 " + file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileName(File file) {
        if (file == null) {
            return "";
        }

        return file.getAbsolutePath();
    }

}
