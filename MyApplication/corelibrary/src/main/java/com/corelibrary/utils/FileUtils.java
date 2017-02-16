package com.corelibrary.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import com.corelibrary.activity.CoreApplication;
import com.corelibrary.constants.CoreConstants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Created by terry-song on 2016/10/13.
 */

public class FileUtils {

    //Android 23检测是否有写的存储权限
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTENAL_STORAGE";
    private static final int BUFFER = 8192;

    /**
     * 判断SD卡是否存在
     * @return
     */
    public static boolean isExistSD(){
        if(MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            return true;
        return false;
    }

    /**
     * 获取缓存目录
     * @param context
     * @return
     */
    public static String getCacheDirectory(Context context){
        File appCacheDir = null;
        String externalStorageState;

        try {
            externalStorageState = Environment.getExternalStorageState();
        }catch (NullPointerException e){
            externalStorageState = "";
        }

        if(MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)){
            appCacheDir = getExternalCacheDir(context);//存储SD卡的目录
            CoreApplication.CACHE_DIR_LOCATION = CoreConstants.CACHE_DIR_SD;//存储位置为SD卡
        }

        if (appCacheDir == null) { //系统目录
            appCacheDir = context.getCacheDir();
            CoreApplication.CACHE_DIR_LOCATION = CoreConstants.CACHE_DIR_SYSTEM;//存储位置为系统
        }

        /*if (appCacheDir == null) {  //android 2.x
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            L.w("Can't define system cache directory! '%s' will be used. =="+ cacheDirPath);
            appCacheDir = new File(cacheDirPath);
        }*/

        return appCacheDir.getAbsolutePath();
    }

    /**
     * 获取存储卡的缓存目录
     * @param context
     * @return
     */
    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                L.w("Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                L.i("Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    /**
     * Android 23 检测是否有写的存储权限
     * @param context
     * @return
     */
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 判断目录是否存在,不存在就创建
     * @param filePath
     * @return
     */
    public static void checkDir(String...filePath) {
        for (String path: filePath) {
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
        }
    }

    /**
     * @Description: 复制文件
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyFile(String sourceFile, String targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     * @param path 文件夹绝对路径
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
                boolean b = temp.delete();
                if (b) {
                    System.out.println("删除成功");
                } else {
                    System.out.println("删除失败：" + temp);
                }
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
     * 删除文件,包括文件夹
     * @param folderPath 文件夹绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Java文件操作 获取文件扩展名
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    public static boolean deleteFile(String path){
        File f = new File(path);
        if (f.exists()) {
            return f.delete();
        }
        return false;
    }

    /**
     * 读取文件
     * @param file
     * @return
     * @throws IOException
     */
    public static String readTextFile(File file) throws IOException {
        String text = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            text = IOUtils.inputStreamToString(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return text;
    }

    /**
     * 将文本内容写入文件
     * @param file
     * @param str
     * @throws IOException
     */
    public static void writeTextFile(File file, String str) throws IOException {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new FileOutputStream(file));
            out.write(str.getBytes());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 获得某个文件的大小
     * @param file
     * @return
     */
    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }

    /**
     * 转换文件大小
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


}
