package com.corelibrary.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by terry-song on 2016/10/13.
 */

public class TextUtils {

    /**
     * 获取文本内容的长度(中文算一个字符，英文算半个字符，包括标点符号)
     * @param str
     * @return
     */
    public static int getTextLengthes(String str){
        int number=getTextLength(str);
        int length=number/2;
        if(number % 2 != 0){
            length+=1;
        }
        return length;
    }

    /**
     * 获取文本内容的长度(中文算两个字符，英文算一个字符)
     * @param str
     * @return
     */
    public static int getTextLength(String str){
        int length=0;
        try {
            str=new String(str.getBytes("GBK"), "ISO8859_1");
            length=str.length();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 格式化,保留二位小数
     * @param value
     * @return
     */
    public static String format(double value){
        return String.format("%.2f", value);
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
}
