package com.terrysong.news;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.corelibrary.network.OkHttpUtils;
import com.corelibrary.network.RequestManager;
import com.corelibrary.utils.L;
import com.corelibrary.utils.T;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by terrysong on 2017/2/10.
 */

public class Api {

    private static String BASIC_URL = "http://v.juhe.cn/toutiao/index?";

    public static String TOP = "top";//头条
    public static String SHEHUI = "shehui";//社会
    public static String GUONEI = "guonei";//国内
    public static String GUOJI = "guoji";//国内
    public static String YULE = "yule";//国内
    public static String TIYU = "tiyu";//国内
    public static String JUNSHI = "junshi";//国内
    public static String KEJI = "keji";//国内
    public static String CAIJING = "caijing";//国内
    public static String SHISHANG = "shishang";//国内


//        parms.put("key","31c0d819f81db59dc9bd60e328468ab7");

    public static void getData(final Context context, String type, final ResponseListener responseListener){
        OkHttpUtils.getInstance(context).getString(getUrl(type), new OkHttpUtils.ResponseListener() {
            @Override
            public void onResponse(Call call, okhttp3.Response response) {
                try {
                    String str = response.body().string();
                    L.e("response"+str);
                    NewsBean newsBean = JSON.parseObject(str,NewsBean.class);
                    List<NewsBean.ResultBean.DataBean> dataBeanList = newsBean.getResult().getData();
                    responseListener.success(dataBeanList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                L.e("请求数据出错："+e.getMessage());
            }
        });
    }

    /*public static void getData(Context context,String type, final ResponseListener responseListener){
        RequestManager.getInstance(context).getString(getUrl(type), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                response = response.replace("\\","");

                L.e("response"+response);
                NewsBean newsBean = JSON.parseObject(response,NewsBean.class);
                List<NewsBean.ResultBean.DataBean> dataBeanList = newsBean.getResult().getData();
                responseListener.success(dataBeanList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.failure(error);
            }
        }, context.getClass().getName());
    }*/

    /**
     * 获取真正的URL地址
     * @param type
     * @return
     */
    private static String getUrl(String type){
        String url = null;
        url = BASIC_URL + "type="+type+"&key="+"31c0d819f81db59dc9bd60e328468ab7";
        L.e("请求的url："+url);
        return url;
    }

    /**
     * 取消请求
     * @param context
     */
    private static void cancleRequest(Context context){
        RequestManager.getInstance(context).cancelRequest(context.getClass().getName());
    }

    public interface ResponseListener{
        void success(List<NewsBean.ResultBean.DataBean> dataBeanList);
        void failure(VolleyError error);
    }
}
