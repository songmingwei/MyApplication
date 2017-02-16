package com.corelibrary.utils;

/**
 * Created by terry-song on 2016/10/13.
 */

public class BitmapUtils {

    public  enum BitmapType{
        gif,jpg,png,bmp
    }

    public static BitmapType getType(byte[] imageData){

        if(imageData.length >= 2){
            if((imageData[0] & 0xFF) == 0xFF && (imageData[1] & 0xD8) == 0xD8){
                return BitmapType.jpg;
            }
        }

        if(imageData.length >= 8){
            short[] png = new short[]{0x89,0x50,0x4E,0x47,0x0D,0x0A,0x1A,0x0A};
            for (int i = 0; i < png.length; i++) {
                if((imageData[i] & png[i]) != png[i]){
                    break;
                }
                if(i == png.length-1){
                    return BitmapType.png;
                }
            }
        }

        return BitmapType.bmp;

    }
}
