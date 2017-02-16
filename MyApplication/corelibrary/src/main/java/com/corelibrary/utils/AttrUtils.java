package com.corelibrary.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by terry-song on 2016/10/13.
 */

public class AttrUtils {

    /**
     * color = AttrUtils.getValueOfColorAttr(getActivity(),R.styleable.Theme,R.styleable.Theme_colorPrimaryDark);
     * 获取属性颜色值
     * @param context
     * @param attrs
     * @param attrValue
     * @return
     */
    public static int getValueOfColorAttr(Context context, int[] attrs, int attrValue){
        TypedArray a = context.obtainStyledAttributes(attrs);
        int color=a.getColor(attrValue, 0);
        return color;
    }
}
