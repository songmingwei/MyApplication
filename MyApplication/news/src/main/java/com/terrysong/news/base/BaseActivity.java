package com.terrysong.news.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.corelibrary.utils.CoreUtils;
import com.corelibrary.utils.L;
import com.corelibrary.widget.TipInfoLayout;
import com.terrysong.news.MainActivity;
import com.terrysong.news.R;
import com.terrysong.news.app.AppApplication;
import java.lang.reflect.Method;

/**
 * Created by terry-song on 2016/10/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected boolean isTemplate=true; //是否使用模板
    private Toolbar toolbar;
    public LinearLayout mainBody;            //主体显示
    protected TipInfoLayout mTipInfoLayout;
    protected Fragment mFragmentContent;  //上一个Fragment

    protected AppApplication getApp(){
        return (AppApplication) getApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoreUtils.addAppActivity(this);

        if(isTemplate){
            setContentView(R.layout.core_template);
            initWidget();
        }

        if(this instanceof MainActivity){   //首页不需要返回键

        } else {
            if(getSupportActionBar()!=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //返回键
            }
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if(layoutResID == R.layout.core_template){
            super.setContentView(layoutResID);
        }else{
            if(mainBody!=null){
                mainBody.removeAllViews();
                View view = getLayoutInflater().inflate(layoutResID,null);
                mainBody.addView(view,
                        new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.MATCH_PARENT));
            }else{
                //不需要模板
                super.setContentView(layoutResID);
                initWidget();
            }

            init();
        }
    }

    private void initWidget() {
        mainBody = (LinearLayout) findViewById(R.id.view_mainBody_id);
//        toolbarShadowView = findView(R.id.toolbar_shadow_id);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        mTipInfoLayout = (TipInfoLayout)findViewById(R.id.fl_panent_id);
    }

    /**
     * 切换Fragment
     * @param to
     */
    protected void switchFragmentContent(int id,Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(mFragmentContent!=null){
            if (mFragmentContent != to) {
                if (!to.isAdded()) { // 先判断是否被add过
                    transaction.hide(mFragmentContent).add(id, to); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mFragmentContent).show(to); // 隐藏当前的fragment，显示下一个
                }
            }
        }else{
            transaction.add(id, to);
        }

        /**
         * Can not perform this action after onSaveInstanceState
         * onSaveInstanceState方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后
         * 再给它添加Fragment就会出错。解决办法就是把commit（）方法替换成 commitAllowingStateLoss()就行了，其效果是一样的。
         */
        transaction.commitAllowingStateLoss();  //推荐使用此方法，更安全，更方便
        mFragmentContent = to;
    }


    /**
     * 初始化控件
     */
    protected abstract void init();

    /**
     * 解决Toolbar中Menu中图标不显示的问题， onCreateOptionsMenu中无效
     * @param view
     * @param menu
     * @return
     */
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    L.e(getClass().getSimpleName() + "onMenuOpened...unable to set icons for overflow menu" + e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 返回Toolbar
     * @return
     */
    public Toolbar getToolbar(){
        return toolbar;
    }

    /**
     * 查找view
     */
    protected <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    /**
     * 设置正标题
     * @param titleName
     */
    public void setActionBarTitle(String titleName){
        getSupportActionBar().setTitle(titleName);
    }

    /**
     * 设置标题
     * @param strResId
     */
    public void setActionBarTitle(@StringRes int strResId){
        getSupportActionBar().setTitle(getString(strResId));
    }

    /**
     * 设置副标题
     * @param subtitleName
     */
    public void setActionBarSubtitleName(String subtitleName){
        getSupportActionBar().setSubtitle(subtitleName);
    }

    /**
     * 设置副标题
     * @param subtitleName
     */
    public void setActionBarSubtitleName(@StringRes int subtitleName){
        getSupportActionBar().setSubtitle(subtitleName);
    }

    @Override
    protected void onDestroy() {
        CoreUtils.removeAppActivity(this);
        super.onDestroy();
    }

}
