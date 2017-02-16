package com.corelibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelibrary.R;
import com.pnikosis.materialishprogress.ProgressWheel;

public class TipInfoLayout extends FrameLayout {
    private ProgressWheel mPbProgressBar;
    public ImageView mTvTipState;
    private TextView mTvTipMsg;

    private Context context;

    public TipInfoLayout(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.core_tip_info_layout, null, false);
        mPbProgressBar = (ProgressWheel) view.findViewById(R.id.tv_tip_loading);
        mTvTipState = (ImageView) view.findViewById(R.id.tv_tip_state);
        mTvTipMsg = (TextView) view.findViewById(R.id.tv_tip_msg);
//        setLoading();
//        completeLoading();
        addView(view);
    }

    /*public void setOnClick(OnClickListener onClik) {
        this.setOnClickListener(onClik);
    }*/

    public void setLoading() {
        setLoading(context.getString(R.string.tip_loading));
    }

    public void setLoading(String message) {
        //this.setVisibility(VISIBLE);
        this.mPbProgressBar.setVisibility(View.VISIBLE);
        this.mTvTipState.setVisibility(View.GONE);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_loading));
    }

    /**
     * 完成加载
     */
    public void completeLoading(){
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.GONE);
        this.mTvTipMsg.setVisibility(View.GONE);
    }

    public void setNetworkError() {
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.core_page_icon_network);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_load_network_error));
    }

    public void setLoadError(String message){
        setLoadError();
        this.mTvTipMsg.setText(message);
    }

    public void setLoadError() {
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.core_page_icon_loaderror);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_load_error));
    }

    public void setEmptyData(String message){
        setEmptyData();
        this.mTvTipMsg.setText(message);
    }

    public void setEmptyData() {
        this.setVisibility(VISIBLE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.core_page_icon_empty);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_load_empty));
    }

    /**
     * 不显示任何提示
     */
    public void setCancelShow(){
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.GONE);
        this.mTvTipMsg.setVisibility(View.GONE);
    }
}
