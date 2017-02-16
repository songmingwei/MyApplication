package com.terrysong.news.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by terry-song on 2016/10/18.
 */

public abstract class BaseFragment extends Fragment {

    protected View rootView;
    protected BaseActivity baseActivity;

    @Override
    public void onAttach(Activity activity) {
        baseActivity = (BaseActivity) activity;
        super.onAttach(activity);
    }

    /**
     * onCreateView返回的就是fragment要显示的view。
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null){
            rootView = getRootView(inflater,container,savedInstanceState);
            builderView(rootView);
        }else{
            ViewGroup p = (ViewGroup) rootView.getParent();
            if (p != null) {
                p.removeAllViewsInLayout();
            }
        }
        return rootView;
    }

    public abstract View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void builderView(View rootView);
}
