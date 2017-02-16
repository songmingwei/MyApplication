package com.corelibrary.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by terry-song on 2016/10/13.
 */

public abstract class BaseCustomAdapter<T> extends BaseAdapter {

    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;
    protected Context mContext;
    protected Resources mResources;

    public BaseCustomAdapter(Context mContext, List<T> mList) {
        this.mContext = mContext;
        this.mData = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mResources = mContext.getResources();
    }

    /**
     * 获取所有数据
     * @return
     */
    public  List<T> getData(){
        return mData;
    }

    /**
     * 添加数据
     * @param data
     */
    public void setData(List<T> data) {
        if(this.mData != null && !this.mData.isEmpty()){
            this.mData.clear();
            this.mData = null;
        }
        this.mData = data;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param location
     * @param data
     */
    public void addAll(int location,List<T> data){
        this.mData.addAll(location,data);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param location
     * @param t
     */
    public void add(int location,T t){
        this.mData.add(location,t);
        notifyDataSetChanged();
    }

    /**
     * 删除数据
     * @param location
     */
    public void remove(int location){
        this.mData.remove(location);
        notifyDataSetChanged();
    }

    /**
     * 删除数据
     * @param t
     */
    public void remove(T t){
        this.mData.remove(t);
        notifyDataSetChanged();
    }

    /**
     * 删除数据
     * @param data
     */
    public void removeAll(List<T> data){
        this.mData.removeAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position,convertView,parent);
    }

    public abstract View getCustomView(int position,View contentView,ViewGroup parent);
}
