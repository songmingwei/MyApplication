package com.terrysong.news;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.corelibrary.utils.L;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.terrysong.news.base.BaseActivity;
import com.terrysong.news.imageshow.Options;
import com.terrysong.news.imageshow.TouchImageView;

/*
 * 图片展示
 */
public class ImageShowActivity extends BaseActivity {
	/** 图片展示 */
	private ViewPager image_pager;
	private TextView page_number;
	/** 图片下载按钮 */
	private TextView download;
	/** 图片列表 */
	private ArrayList<String> imgsUrl;
	/** PagerAdapter */
	private Mypager mAdapter;
	private int index;
	private ArrayList<View> viewList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_imageshow);
	}

	@Override
	protected void init() {
		setActionBarTitle("图片浏览");
		initData();
		initView();
	}

	private void initData() {
		imgsUrl = getIntent().getStringArrayListExtra("infos");
		index = getIntent().getIntExtra("index",0);
		L.e("imgsUrl:"+ imgsUrl);
	}

	private void initView() {
		options = Options.getListOptions();
		mTipInfoLayout.completeLoading();
		image_pager = (ViewPager) findViewById(R.id.image_pager);
		page_number = (TextView) findViewById(R.id.page_number);
		download = (TextView) findViewById(R.id.download);
		/*download.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: 2017/2/12 保存图片
			}
		});*/

		//显示点击图片的位置
		page_number.setText((index+1)+"/"+imgsUrl.size());

		//确定ViewPager中组件的数量
		viewList = new ArrayList<View>();
		for (int i = 0; i < imgsUrl.size(); i++) {
			View view = getLayoutInflater().inflate(R.layout.details_imageshow_item, null);
//			ImageView imageView =  (ImageView) view.findViewById(R.id.iv_check);
			viewList.add(view);
		}

		image_pager.setAdapter(new Mypager());
		//打开页面加载点击的那张图片
		loadImage(index);

		//设置当前在展示的页面
		image_pager.setCurrentItem(index);

		/**
		 * 点击滑动事件
		 */
		image_pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				page_number.setText((arg0+1)+"/"+imgsUrl.size());
				loadImage(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}


	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	//view内控件
	private TouchImageView full_image;
	private TextView progress_text;
	private ProgressBar progress;
	private TextView retry;
	private void loadImage(int position){
		View view = viewList.get(position);
		full_image = (TouchImageView)view.findViewById(R.id.full_image);
		progress_text= (TextView)view.findViewById(R.id.progress_text);
		progress= (ProgressBar)view.findViewById(R.id.progress);
		retry= (TextView)view.findViewById(R.id.retry);//加载失败
		progress_text.setText(String.valueOf(position));

		imageLoader.displayImage(imgsUrl.get(position), full_image, options,new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {

				L.e("onLoadingStarted开始加载图片--------");
				// TODO Auto-generated method stub
				progress.setVisibility(View.VISIBLE);
				progress_text.setVisibility(View.VISIBLE);
				full_image.setVisibility(View.GONE);
				retry.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
										FailReason failReason) {
				L.e("onLoadingFailed图片加载失败--------");
				// TODO Auto-generated method stub
				progress.setVisibility(View.GONE);
				progress_text.setVisibility(View.GONE);
				full_image.setVisibility(View.GONE);
				retry.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				L.e("onLoadingComplete图片加载完成--------");
				progress.setVisibility(View.GONE);
				progress_text.setVisibility(View.GONE);
				full_image.setVisibility(View.VISIBLE);
				retry.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				L.e("onLoadingCancelled取消图片加载--------");
				progress.setVisibility(View.GONE);
				progress_text.setVisibility(View.GONE);
				full_image.setVisibility(View.GONE);
				retry.setVisibility(View.VISIBLE);
			}
		});
	}


	/**
	 * 给viewPager填充图片
	 * @author Administrator
	 *
	 */
	class Mypager extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {//得到第下标的布局
			container.addView(viewList.get(position));
			return viewList.get(position);
		}

		@Override
		public int getCount() {//得到布局的长度
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
}
