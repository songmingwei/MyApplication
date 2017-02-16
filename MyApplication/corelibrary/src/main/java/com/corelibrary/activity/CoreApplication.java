package com.corelibrary.activity;

import android.app.Application;
import android.content.Context;
import com.corelibrary.utils.FileUtils;
import com.corelibrary.utils.L;
import java.io.File;

/**
 * Created by terry-song on 2016/10/13.
 */

public class CoreApplication extends Application{

    private static Context context;
    public static String CACHE_DIR; //缓存目录
    public static int CACHE_DIR_LOCATION; //缓存位置
    public static String IMAGE_DIR; //图片目录
    public static String FILE_DIR; //文件目录
    public static String STRING_DIR; //文本目录
    public static String LOG; //日志
    public static String ALL_LOG; //所有
    public static String IMAGE_UPLOAD_TEMP; //上传图片临时目录

    @Override
    public void onCreate() {
        super.onCreate();
        context = CoreApplication.this;
        init();
    }

    public static Context getContext(){
        return context;
    }

    /**
     * 初始化
     */
    private void init(){
        //获取缓存目录
        CACHE_DIR = FileUtils.getCacheDirectory(context);

        CACHE_DIR += File.separator;   // + "/"
        L.e("-------缓存目录------>>>:"+CACHE_DIR);

        IMAGE_DIR = CACHE_DIR + "images/";
        FILE_DIR = CACHE_DIR + "files/";
        LOG = CACHE_DIR + "cache.log";
        ALL_LOG = CACHE_DIR + "allCache.log";
        IMAGE_UPLOAD_TEMP = CACHE_DIR + "imageUploadTemp/";
        STRING_DIR = CACHE_DIR + "strings/";

        FileUtils.checkDir(CACHE_DIR,IMAGE_DIR,FILE_DIR,LOG,ALL_LOG,IMAGE_UPLOAD_TEMP,STRING_DIR);
    }


}
