package com.corelibrary.utils;

import android.util.Log;

import static com.corelibrary.constants.CoreConstants.IS_DEBUG;


/**
 * Created by terry-song on 2016/9/29.
 */

public class L {

    private static final String TAG = "--Main--";

    public static void e(Object message){
        if(IS_DEBUG){
            Log.e(TAG, message.toString());
        }
    }

    public static void w(Object message){
        if(IS_DEBUG){
            Log.e(TAG, message.toString());
        }
    }

    public static void i(Object message){
        if(IS_DEBUG){
            Log.e(TAG, message.toString());
        }
    }
}
