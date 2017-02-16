package com.terrysong.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.terrysong.news.Api;
import com.terrysong.news.R;
import com.terrysong.news.adapter.NewsAdapter;
import com.terrysong.news.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrysong on 2017/2/10.
 */

public class TabFragment extends BaseFragment {


    @Override
    public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }

    @Override
    public void builderView(View rootView) {
        TabLayout tableLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager_id);

        List<String> titleList = new ArrayList<>();
        titleList.add("头条");
        titleList.add("社会");
        titleList.add("国内");
        titleList.add("国际");
        titleList.add("娱乐");
        titleList.add("体育");
        titleList.add("军事");
        titleList.add("科技");
        titleList.add("财经");
        titleList.add("时尚");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(NewsFragment.getInstance(Api.TOP));
        fragmentList.add(NewsFragment.getInstance(Api.SHEHUI));
        fragmentList.add(NewsFragment.getInstance(Api.GUONEI));
        fragmentList.add(NewsFragment.getInstance(Api.GUOJI));
        fragmentList.add(NewsFragment.getInstance(Api.YULE));
        fragmentList.add(NewsFragment.getInstance(Api.TIYU));
        fragmentList.add(NewsFragment.getInstance(Api.JUNSHI));
        fragmentList.add(NewsFragment.getInstance(Api.KEJI));
        fragmentList.add(NewsFragment.getInstance(Api.CAIJING));
        fragmentList.add(NewsFragment.getInstance(Api.SHISHANG));

//        fAdapter = new FindTabAdapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);

//        NewsAdapter adapter = new NewsAdapter(getActivity().getSupportFragmentManager(),titleList,fragmentList);
        NewsAdapter adapter = new NewsAdapter(getChildFragmentManager(),titleList,fragmentList);

        //viewpager加载adapter
        viewPager.setAdapter(adapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tableLayout.setupWithViewPager(viewPager);
    }

}
