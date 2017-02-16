package com.corelibrary.network;

import android.app.Activity;
import android.content.Context;
import com.corelibrary.utils.L;
import com.corelibrary.utils.T;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by terrysong on 2017/2/14.
 */

public class OkHttpUtils {

    private static OkHttpUtils okHttpUtils;
    private OkHttpClient mOkHttpClient;

    private OkHttpUtils(Context context){
        init(context);
    }

    private void init(Context context) {
        mOkHttpClient=new OkHttpClient();
    }

    /**
     * 实例化RequestManager
     * @return
     */
    public static OkHttpUtils getInstance(Context context){
        if (okHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpUtils == null) {
                    okHttpUtils = new OkHttpUtils(context);

                }
            }
        }
        return okHttpUtils;
    }

    /**
     * Get请求
     * @param url
     */
    public void getString(String url, final ResponseListener listener) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
        requestBuilder.method("GET",null);
        //创建请求
        Request request = requestBuilder.build();
        //发送请求
        Call mcall= mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(call,e);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
//                String str1 = response.body().string();
                listener.onResponse(call,response);
                L.e("-----------------:"+Thread.currentThread().getName());
                /*if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    L.e("cache---" + str);
                    listener.onResponse(call,response);
                } else {
                    response.body().string();
                    String str = response.networkResponse().toString();
                    L.e("network---" + str);
                    listener.onResponse(call,response);
                }*/
            }
        });
    }

    /**
     * Post请求
     * @param url
     * @param parms
     * @param listener
     */
    private void postString(String url, HashMap<String ,String> parms, final ResponseListener listener) {
        FormBody.Builder formBody = new FormBody.Builder();
        Set<String> keys = parms.keySet();
        for (String key :
                keys) {
            formBody.add(key, parms.get(key));
        }

        RequestBody requestBody = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取数据
//                String str = response.body().string();
                listener.onResponse(call, response);
            }
        });
    }

    /**
     * 定义上传文件类型
     * 文本
     */
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    /**
     * 图片
     */
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    /**
     * 上传文件
     * 记得添加存储卡权限
     * @param url
     * @param mediaType  上传的数据类型
     * @param filePath
     * @param listener
     */
    public void postFile(String url, MediaType mediaType, String filePath, final ResponseListener listener) {
        File file = new File(filePath);
        Request.Builder builder = new Request.Builder() .url(url);

       if(MEDIA_TYPE_MARKDOWN.equals(mediaType)){
           builder.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file));
       }

        if(MEDIA_TYPE_PNG.equals(mediaType)){
            builder.post(RequestBody.create(MEDIA_TYPE_PNG, file));
        }
        Request request = builder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onResponse(call,response);
            }
        });
    }

    /**
     * 下载文件
     * @param url
     * @param filePath
     */
    private void downFile(String url,final String filePath) {
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("下载文件失败："+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(new File(filePath));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    L.e("下载文件失败："+e.getMessage());
                    e.printStackTrace();
                }finally {
                    try {
                        if(inputStream != null){
                            inputStream.close();
                            inputStream = null;
                        }

                        if(fileOutputStream != null){
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            fileOutputStream = null;
                        }
                    } catch (IOException e) {
                        L.e("下载文件失败："+e.getMessage());
                        e.printStackTrace();
                    }
                }
                L.e("下载成功！");
            }
        });
    }

    public interface ResponseListener{
        void onResponse(Call call, Response response);
        void onFailure(Call call, IOException e);
    }
}
