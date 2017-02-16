package com.corelibrary.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by terry-song on 2016/10/13.
 */

public class InputTools {

    enum SoftStatus{
        OPEN,//打开
        CLOSE,//关闭
    }

    /**
     * 隐藏虚拟键盘
     * @param v
     */
    public static void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 显示虚拟键盘
     * @param v
     */
    public static void showKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 强制显示或者关闭系统键盘
     * @param txtSearchKey
     * @param status
     */
    public static void imposedKeyBoard(final EditText txtSearchKey, final SoftStatus status) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) txtSearchKey
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);

                switch (status){
                    case OPEN://打开
                        m.showSoftInput(txtSearchKey,InputMethodManager.SHOW_FORCED);
                        break;
                    case CLOSE://关闭
                        m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
                        break;
                }
            }
        }, 300);
    }

    // 通过定时器强制隐藏虚拟键盘
    public static void timerHideKeyboard(final View v) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) v.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
                            0);
                }
            }
        }, 10);
    }

    // 输入法是否显示着
    public static boolean keyBoardIsShow(EditText edittext) {
        boolean bool = false;
        InputMethodManager imm = (InputMethodManager) edittext.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            bool = true;
        }
        return bool;
    }

}
