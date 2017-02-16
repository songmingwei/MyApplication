package com.corelibrary.network;

import android.content.Context;
import android.widget.ImageView;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.corelibrary.R;
import com.corelibrary.utils.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by terry-song on 2016/10/13.
 */

public class RequestManager {

    private static RequestManager requestManager = null;
    private static RequestQueue requestQueue = null;
    private static ImageLoader imageLoader = null;

    private RequestManager(Context context){
        init(context);
    }

    /**
     * 实例化RequestManager
     * @return
     */
    public static RequestManager getInstance(Context context){
        if (requestManager == null) {
            synchronized (RequestManager.class) {
                if (requestManager == null) {
                    requestManager = new RequestManager(context);
                }
            }
        }
        return requestManager;
    }

    /**
     * 初始化请求队列
     * @param context
     */
    public void init(Context context){
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new BitmapCache());
    }

    /**
     * 添加Request到队列
     * @param request
     * @param <T>
     */
    private <T> void addRequestQueue(Request<T> request){
        requestQueue.add(request);
    }

    /**
     * 添加Request到队列【带标签】
     * @param request
     * @param tag
     * @param <T>
     */
    private <T> void addRequestQueue(Request<T> request,String tag){
        if(!TextUtils.isEmpty(tag)){
            request.setTag(tag);
        }
        addRequestQueue(request);
    }

    /**
     * get请求
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public Request getString(String url, Response.Listener<String> listener,
                       Response.ErrorListener errorListener,String tag){

        StringRequest stringRequest = new StringRequest(url,listener,errorListener);

        addRequestQueue(stringRequest,tag);

        return stringRequest;
    }

    /**
     * get请求
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public Request getString(String url, Response.Listener<String> listener,
                             Response.ErrorListener errorListener,String tag,boolean isGarbled){
        StringRequest stringRequest = new StringRequest(url,listener,errorListener){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {//解决乱码
                String str = null;
                try {
                    str = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        addRequestQueue(stringRequest,tag);

        return stringRequest;
    }

    /**
     * post请求
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public Request postString(String url, Response.Listener<String> listener,
                        Response.ErrorListener errorListener, final Map<String, String> map,String tag){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                listener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addRequestQueue(stringRequest,tag);
        return stringRequest;
    }

    /**
     * 根据标签取消请求
     * @param tag
     */
    public void cancelRequest(String tag){
        requestQueue.cancelAll(tag);
    }

    /**
     * 加载图片
     * @param imageView
     * @param imageUrl
     */
    public void loadImage(final ImageView imageView, String imageUrl){
        if(imageUrl == null){
            return;
        }
        imageView.setTag(imageUrl);
        ImageLoader.ImageListener imageListener = (ImageLoader.ImageListener) imageView.getTag(imageView.getId());
        if(imageListener == null){
            imageListener = new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                    if(response.getBitmap() != null){//判断图片是否为空
                        if(imageView.getTag().toString().equals(response.getRequestUrl())){
                            imageView.setImageBitmap(response.getBitmap());
                        }
                    }else {//设置默认显示图片
                        imageView.setImageResource(R.mipmap.img_header);
                    }
                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    //下载出错显示的图片
                    imageView.setImageResource(R.mipmap.img_header);
                }
            };
            imageView.setTag(imageView.getId(),imageListener);
        }
        //4.通过ImageLoader中的get方法进行网络图片的加载
        imageLoader.get(imageUrl,imageListener);
    }

}
