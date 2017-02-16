package com.corelibrary.utils;

import android.content.Context;
import android.webkit.WebView;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by terrysong on 2017/2/11.
 */

public class AssetsUtils {

    /**
     * 从asset目录下读取文件
     * @param fileName
     * @return
     */
    public static String readAssetsHtml(Context context, String fileName){
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);

            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(bytes)) != -1){
                sb.append(new String(bytes,0,len));
            }

            String html = sb.toString();
            return html;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(is != null){
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "暂无数据";
    }


    /**
     * 从asset目录下读取文件
     * @param fileName
     * @return
     */
    public static void loadAssetsHtml(Context context, String fileName, WebView webView){
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);

            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(bytes)) != -1){
                sb.append(new String(bytes,0,len));
            }

            String html = sb.toString();
            webView.loadDataWithBaseURL(null,html,"text/html","utf-8",null);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(is != null){
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取Html数据
     */
    public void getHtml(Context context, List<ImageView> imageViews,String body, String filename) {
//        String html = readAssetsHtml(context,filename);

        if(!TextUtils.isEmpty(body)){
            // 获取html中一共有多少图片URL
            /*<img src="http://static.cnbetacdn.com/article/2016/0903/bd53f7aa5a3c923.jpg"/>*/
            Pattern p_img = Pattern.compile(		//获取url
                    "<\\s*img[^<]*src\\s*=\\s*\"([^<]+(jpg|gif|bmp|bnp|png){1})\"[^>]*>",
                    Pattern.CASE_INSENSITIVE);
            Matcher mImg = p_img.matcher(body);

            //将图片URL放入imageList
            /*for (; mImg.find(); imageList.add(mImg.group(1))) {
            }*/

            //添加本地的java方法。
            // 添加点击图片放大支持--》只设置宽，不设置高，图片就会等比例缩放
            body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                    "$1$2\" width='100%' onClick=\"javascript:wxhlListener.imageOnClick('$2')\"");

            body = body.replaceAll("href","");

//            html = html.replace("*****",body);
//            webView.loadDataWithBaseURL(null,html,"text/html","utf-8",null);
        }
    }

   /* class WXHLListener{
        @JavascriptInterface
        public void imageOnClick(String imageUrl){
            int index = imageList.indexOf(imageUrl);
//            Toast.makeText(Main3Activity.this, "imageUrl:"+imageUrl+",index:"+index, Toast.LENGTH_SHORT).show();
            SnackbarUtils.showShort(ReadMeActivity.this,getToolbar(),"index:"+index);
        }
    }*/
}
