package com.terrysong.news;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.corelibrary.network.RequestManager;
import com.corelibrary.utils.AssetsUtils;
import com.corelibrary.utils.IntentUtils;
import com.corelibrary.utils.L;
import com.corelibrary.utils.TextUtils;
import com.terrysong.news.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebViewActivity extends BaseActivity {

    private WebView webView;

    private ArrayList<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
    }

    @Override
    protected void init() {
        webView = (WebView)findViewById(R.id.web_view);
        initWebView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("url");
       /* webView.loadUrl(url);*/

        RequestManager.getInstance(this).getString(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int titleStartIndex = response.indexOf("<title>");
                int titleLastIndex = response.indexOf("</title>");
                String titleResult = response.substring(titleStartIndex+7, titleLastIndex);

                //设置标题
                setActionBarTitle(titleResult);

                int startIndex = response.indexOf("<article");
                int lastIndex = response.indexOf("</article>");
                String result = response.substring(startIndex, lastIndex+10);
                getHtml(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getHtml(error.getMessage());
            }
        },getClass().getName());
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();

        /* 设置支持Js */
        webSettings.setJavaScriptEnabled(true);
        /* 设置缓存模式 */
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        /* 设置为true表示支持使用js打开新的窗口 */
        //        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        /* 大部分网页需要自己保存一些数据,这个时候就的设置下面这个属性 */
        webSettings.setDomStorageEnabled(true);
        /* 设置为使用webview推荐的窗口 */
        webSettings.setUseWideViewPort(true);
        /* 设置网页自适应屏幕大小 ---这个属性应该是跟上面一个属性一起用 */
        webSettings.setLoadWithOverviewMode(true);
        /* HTML5的地理位置服务,设置为true,启用地理定位 */
        webSettings.setGeolocationEnabled(true);
        /* 设置是否允许webview使用缩放的功能, 设为false,不允许 */
        webSettings.setBuiltInZoomControls(false);
        /* 提高网页渲染的优先级 */
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        // 设置编码
        webSettings.setDefaultTextEncodingName("utf-8");

        webSettings.setDefaultFontSize(16);

        /* 设置显示水平滚动条,就是网页右边的滚动条, 这里设置的不显示 */
        webView.setHorizontalScrollBarEnabled(false);
        /* 指定垂直滚动条是否有叠加样式 */
        webView.setVerticalScrollbarOverlay(true);
        /* 设置滚动条的样式 */
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        /* 这个不用说了,重写WebChromeClient监听网页加载的进度,从而实现进度条 */
//        webView.setWebChromeClient(new WebChromeClient());
        /* 同上,重写WebViewClient可以监听网页的跳转和资源加载等等... */
        webView.setWebViewClient(new WebViewClient());
        // 设置背景颜色 透明
        webView.setBackgroundColor(Color.argb(0,0,0,0));
        // webview闪烁问题
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
        webView.addJavascriptInterface(new WXHLListener(),"wxhlListener");
    }

    /**
     * 获取Html数据
     */
    public void getHtml(String body) {
        String html = AssetsUtils.readAssetsHtml(this,"newspage.html");

        if(!TextUtils.isEmpty(body)){
            // 获取html中一共有多少图片URL
            /*<img src="http://static.cnbetacdn.com/article/2016/0903/bd53f7aa5a3c923.jpg"/>*/
            Pattern p_img = Pattern.compile(		//获取url
                    "<\\s*img[^<]*src\\s*=\\s*\"([^<]+(jpeg|jpg|gif|bmp|bnp|png){1})\"[^>]*>",
                    Pattern.CASE_INSENSITIVE);
            Matcher mImg = p_img.matcher(body);

            //将图片URL放入imageList
            for (; mImg.find(); imageList.add(mImg.group(1))) {
            }

            L.e("imageList:"+imageList);

            //添加本地的java方法。
            // 添加点击图片放大支持--》只设置宽，不设置高，图片就会等比例缩放
            /*body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                    "$1$2\" width='100%' onClick=\"javascript:wxhlListener.imageOnClick('$2')\"");*/

            body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                    "$1$2\"  onClick=\"javascript:wxhlListener.imageOnClick('$2')\"");

            body = body.replaceAll("href","");

            html = html.replace("*****",body);

            L.e("body:"+body);
            webView.loadDataWithBaseURL(null,html,"text/html","utf-8",null);
        }
    }

    class WXHLListener{
        @JavascriptInterface
        public void imageOnClick(String imageUrl){
            int index = imageList.indexOf(imageUrl);
//            Toast.makeText(WebViewActivity.this, "imageUrl:"+imageUrl+",index:"+index, Toast.LENGTH_SHORT).show();
//            SnackbarUtils.showShort(WebViewActivity.this,getToolbar(),"index:"+index);

            Intent intent = new Intent();
            intent.putExtra("index",index);
            intent.putStringArrayListExtra("infos",imageList);
            IntentUtils.startActivity(WebViewActivity.this,ImageShowActivity.class,intent);
        }
    }

}
