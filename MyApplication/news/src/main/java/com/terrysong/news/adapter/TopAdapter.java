package com.terrysong.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelibrary.adapter.BaseCustomAdapter;
import com.corelibrary.network.RequestManager;
import com.terrysong.news.NewsBean;
import com.terrysong.news.R;

import java.util.List;

/**
 * Created by terrysong on 2017/2/10.
 */

public class TopAdapter extends BaseCustomAdapter<NewsBean.ResultBean.DataBean> {

    public TopAdapter(Context mContext, List<NewsBean.ResultBean.DataBean> mList) {
        super(mContext, mList);
    }

    @Override
    public View getCustomView(int position, View contentView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(contentView == null){
            viewHolder = new ViewHolder();
            contentView = mLayoutInflater.inflate(R.layout.adapter_top,null);
            viewHolder.tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
            viewHolder.tvContent = (TextView) contentView.findViewById(R.id.tv_content);
            viewHolder.imgOne = (ImageView) contentView.findViewById(R.id.img_one);
            viewHolder.imgThree = (ImageView) contentView.findViewById(R.id.img_three);
            viewHolder.imgTwo = (ImageView) contentView.findViewById(R.id.img_two);

            contentView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) contentView.getTag();
        }

        NewsBean.ResultBean.DataBean dataBean = mData.get(position);
        viewHolder.tvTitle.setText(dataBean.getTitle());
        viewHolder.tvContent.setText(dataBean.getAuthor_name()+" "+dataBean.getDate());

        // TODO: 2017/2/10 加载图片

        RequestManager.getInstance(mContext).loadImage(viewHolder.imgOne,dataBean.getThumbnail_pic_s());
        RequestManager.getInstance(mContext).loadImage(viewHolder.imgTwo,dataBean.getThumbnail_pic_s02());
        RequestManager.getInstance(mContext).loadImage(viewHolder.imgThree,dataBean.getThumbnail_pic_s03());
        return contentView;
    }

    class ViewHolder {
        TextView tvTitle,tvContent;
        ImageView imgOne,imgTwo,imgThree;
    }
}
