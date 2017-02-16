package com.corelibrary.network;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by terry-song on 2016/10/8.
 */

public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String,Bitmap> lruCache = new LruCache<String,Bitmap>(10*1024*1024){
        @Override
        protected int sizeOf(String key, Bitmap value) {
            //一张图片的大小
            return value.getRowBytes()*value.getHeight();
        }
    };

    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url,bitmap);
    }
}
