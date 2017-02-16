package com.corelibrary.utils;

import android.content.Context;
import android.content.res.Resources;

import com.corelibrary.R;
import com.corelibrary.activity.CoreApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * Created by terry-song on 2016/10/13.
 */

public class DateUtils {

    // yyyy-MM-dd hh:mm:ss 12小时制
    // yyyy-MM-dd HH:mm:ss 24小时制

    public static final String TYPE_01 = "yyyy-MM-dd HH:mm:ss";

    public static final String TYPE_02 = "yyyy-MM-dd";

    public static final String TYPE_03 = "HH:mm:ss";

    public static final String TYPE_04 = "yyyy年MM月dd日";

    public static final String TYPE_05 = "yyyy年MM月";

    private static final String[] WEEK = { "天", "一", "二", "三", "四", "五", "六" };
    public static final String XING_QI = "星期";
    public static final String ZHOU = "周";


    /**
     * long型日期
     * @param time
     * @param format
     * @return
     */
    public static String formatDate(long time, String format) {
        Calendar cal = Calendar.getInstance();
        if(time>0)
            cal.setTimeInMillis(time);
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    /**
     * long型字符串日期
     * @param longStr
     * @param format
     * @return
     */
    public static String formatDate(String longStr, String format) {
        try {
            return formatDate(Long.parseLong(longStr), format);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 字符串型日期 转为 long型日期
     * @param timeStr
     * @param pattern
     * @return
     */
    public static long formatStr(String timeStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(timeStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static String getTime(long time) {
        return new SimpleDateFormat("HH:mm").format(new Date(time));
    }

    /**
     * 转换日期
     * @param timesamp
     * @return
     */
    public static String getDay(long timesamp) {
        if(timesamp == 0L){
            return "未";
        }
        String result = "未";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        int temp = Integer.parseInt(sdf.format(today))
                - Integer.parseInt(sdf.format(otherDay));

        switch (temp) {
            case 0:
                result = "今天" + getTime(timesamp);
                break;
            case 1:
                result = "昨天"+ getTime(timesamp);
                break;
            case 2:
                result = "前天"+ getTime(timesamp);
                break;

            default:
                result = temp + "天前"+ getTime(timesamp);
                break;
        }
        return result;
    }

    /**
     * 转换日期
     * @param time
     * @return
     */
    public static String convDate(String time) {
        Context context = CoreApplication.getContext();
        Resources res = context.getResources();

        StringBuffer buffer = new StringBuffer();

        Calendar createCal = Calendar.getInstance();
        createCal.setTimeInMillis(Date.parse(time));
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTimeInMillis(System.currentTimeMillis());

        long diffTime = (currentCal.getTimeInMillis() - createCal.getTimeInMillis()) / 1000;

        // 同一月
        if (currentCal.get(Calendar.MONTH) == createCal.get(Calendar.MONTH)) {
            // 同一天
            if (currentCal.get(Calendar.DAY_OF_MONTH) == createCal.get(Calendar.DAY_OF_MONTH)) {
                if (diffTime < 3600 && diffTime >= 60) {
                    buffer.append((diffTime / 60) + res.getString(R.string.msg_few_minutes_ago));
                } else if (diffTime < 60) {
                    buffer.append(res.getString(R.string.msg_now));
                } else {
                    buffer.append(res.getString(R.string.msg_today)).append(" ").append(DateUtils.formatDate(createCal.getTimeInMillis(), "HH:mm"));
                }
            }
            // 前一天
            else if (currentCal.get(Calendar.DAY_OF_MONTH) - createCal.get(Calendar.DAY_OF_MONTH) == 1) {
                buffer.append(res.getString(R.string.msg_yesterday)).append(" ").append(DateUtils.formatDate(createCal.getTimeInMillis(), "HH:mm"));
            }
        }

        if (buffer.length() == 0) {
            buffer.append(DateUtils.formatDate(createCal.getTimeInMillis(), "MM-dd HH:mm"));
        }

        String timeStr = buffer.toString();
        if (currentCal.get(Calendar.YEAR) != createCal.get(Calendar.YEAR)) {
            timeStr = createCal.get(Calendar.YEAR) + " " + timeStr;
        }
        return timeStr;
    }

}
