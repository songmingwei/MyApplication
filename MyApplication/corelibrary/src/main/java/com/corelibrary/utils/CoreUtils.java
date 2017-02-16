package com.corelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.corelibrary.network.ListenNetStateService;

import java.util.ArrayList;

/**
 * Created by terry-song on 2016/10/13.
 */

public class CoreUtils {

    //Activity列表
    public static ArrayList<Activity> activityList = new ArrayList<>();

    /**
     * 添加Activity到列表中
     * @param activity
     */
    public static void addAppActivity(Activity activity){
        if(!activityList.contains(activity)){
            activityList.add(activity);
        }
    }

    /**
     * 从列表移除Activity
     * @param activity
     */
    public static void removeAppActivity(Activity activity){
        if(activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

    /**
     * 退出应用程序
     */
    public static void exitApp(Context context){
        context.stopService(new Intent(context, ListenNetStateService.class));
        L.w("销毁Activity size:" + activityList.size());
        for (Activity ac : activityList) {
            if(!ac.isFinishing()){
                ac.finish();
            }
        }
        activityList.clear();

        //杀死应用
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * @Description: 重新运行应用程序
     */
    public static void restartApp(Context context){
        context.stopService(new Intent(context, ListenNetStateService.class));

        L.e("--- 销毁 Activity size--->>:" + activityList.size());
        for (Activity ac : activityList) {
            if(!ac.isFinishing()){
                ac.finish();
            }
        }
        activityList.clear();
    }

}
