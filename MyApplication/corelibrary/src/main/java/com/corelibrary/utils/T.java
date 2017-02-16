package com.corelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by terry-song on 2016/10/13.
 */

public class T {

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param resId
     */
    public static void showShort(Context context, @StringRes int resId) {
        if (isShow)
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param resId
     */
    public static void showLong(Context context, @StringRes int resId) {
        if (isShow)
            Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     * @param context
     * @param resId
     * @param duration
     */
    public static void show(Context context, @StringRes int resId, int duration) {
        if (isShow)
            Toast.makeText(context, resId, duration).show();
    }

    /**
     * 显示Toast消息,当在非UI线程中需要显示消息时调用此方法
     * @param activity
     * @param message
     */
    public static void showToastMsgOnUiThread(final Activity activity, final String message){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
