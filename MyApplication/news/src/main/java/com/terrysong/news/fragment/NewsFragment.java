package com.terrysong.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.VolleyError;
import com.corelibrary.utils.IntentUtils;
import com.corelibrary.utils.L;
import com.corelibrary.widget.TipInfoLayout;
import com.terrysong.news.Api;
import com.terrysong.news.NewsBean;
import com.terrysong.news.R;
import com.terrysong.news.WebViewActivity;
import com.terrysong.news.adapter.TopAdapter;
import com.terrysong.news.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrysong on 2017/2/10.
 */

public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static NewsFragment instance = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private String loadPage;
    private TopAdapter topAdapter;
    private TipInfoLayout tipInfoLayout;

    private List<NewsBean.ResultBean.DataBean> dataBeanList;

    //google 推荐的传参方式，不推荐构造器直接new

    public static NewsFragment getInstance(String loadPage){
        instance = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("loadPage",loadPage);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    public interface LoadPage{
        int TOP = 0;   //发现--推荐项目
        int SHEHUI =1;         //发现--热门项目
        int GUONEI = 2;     //发现--最近更新
        int GUOJI = 3;     //发现--最近更新
        int YULE = 4;     //发现--最近更新
        int TIYU = 5;     //发现--最近更新
        int JUNSHI = 6;     //发现--最近更新
        int KEJI = 7;     //发现--最近更新
        int CAIJING = 8;     //发现--最近更新
        int SHISHANG = 9;     //发现--最近更新
    }

    @Override
    public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_list_view,null);
        return view;
    }

    @Override
    public void builderView(View rootView) {
        tipInfoLayout = (TipInfoLayout) rootView.findViewById(R.id.tip_info_id);

        listView = (ListView) rootView.findViewById(R.id.lv_id);

        dataBeanList = new ArrayList<>();
        topAdapter = new TopAdapter(baseActivity,dataBeanList);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.sw_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean.ResultBean.DataBean dataBean = (NewsBean.ResultBean.DataBean) parent.getAdapter().getItem(position);
                String url = dataBean.getUrl();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("url",url);
                intent.putExtras(bundle);
                IntentUtils.startActivity(getActivity(),WebViewActivity.class,intent);
            }
        });

        Bundle bundle = getArguments();
        loadPage = bundle.getString("loadPage");
        L.e("loadType:"+loadPage);

        loadData();
    }

    /**
     * 请求数据
     */
    private void loadData() {
        Api.getData(getActivity(), loadPage, new Api.ResponseListener() {
            @Override
            public void success(final List<NewsBean.ResultBean.DataBean> resultList) {
                L.e("-----------------:"+Thread.currentThread().getName());
                //okHttp请求
                baseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        topAdapter.setData(resultList);
                        tipInfoLayout.completeLoading();
                        swipeRefreshLayout.setRefreshing(false);
                        listView.setAdapter(topAdapter);
                    }
                });
            }
            @Override
            public void failure(VolleyError error) {
//                L.e("error"+error.getMessage());
            }
        });
    }


}
