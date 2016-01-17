package club.hanfeng.freewalk.framework;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected boolean initBackActionBar() {
        return false;
    }

    private void init() {
        if (initBackActionBar()) {
            initBackBar();
        }
        initIntentData();
        initView();
        initData();
    }

    /**
     * step0 初始化ActionBar
     */
    private void initBackBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * step1 初始化view
     *
     * @see #getContentViewResId()
     * @see #getContentRootView()
     * @see #initTopBar()
     * @see #initContent()
     * @see #initBottomBar()
     */
    private void initView() {
        int contentViewId = getContentViewResId();
        View contentView = getContentRootView();
        if (contentViewId != 0) {
            setContentView(contentViewId);
        } else if (contentView != null) {
            setContentView(contentView);
        } else {
            throw new RuntimeException("content view not found");
        }
        initRootView();
        initTopBar();
        initContent();
        initBottomBar();
        initOtherView();
    }

    /**
     * step1.1.1 返回content view res Id，如果content view完全从xml加载，推荐使用
     *
     * @return content view Id 如果需要使用1.1.2的方式初始化请返回 0
     * @see #initView()
     * @see #getContentRootView()
     */
    protected abstract int getContentViewResId();

    /**
     * step1.1.2 返回content view，如果content view使用java code生成，推荐使用。
     * 需要注意此方法仅需返回最顶层root view，children view推荐在1.2.x的各方法中生成。
     *
     * @return content root view 如果使用1.1.1的方式初始化请返回 null
     * @see #initView()
     * @see #getContentViewResId()
     */
    protected abstract View getContentRootView();

    /**
     * step1.2.0 初始化本页面root view，仅在对顶级View有操作或更新需求的情况下用到
     */
    protected void initRootView() {

    }

    /**
     * step1.2.1 初始化top bar
     *
     * @see #getOnClickListener()
     */
    protected abstract void initTopBar();

    /**
     * step1.2.2 初始化content
     *
     * @see #getOnClickListener()
     */
    protected abstract void initContent();

    /**
     * step1.2.3 初始化bottom bar
     *
     * @see #getOnClickListener()
     */
    protected abstract void initBottomBar();

    /**
     * step1.2.4 初始化其他view
     */
    protected void initOtherView() {
    }

    /**
     * 推荐使用默认的view OnClickListener：没有特别原因，最好不要每次都new一个新的OnClickListener
     *
     * @return OnClickListener
     */
    protected OnClickListener getOnClickListener() {
        return this;
    }

    /**
     * 从Intent中获取数据加载数据
     */
    protected void initIntentData() {
    }

    /**
     * step2 从本地或网络加载数据
     */
    protected abstract void initData();

    /**
     * step3 用数据填充view
     *
     * @param viewId
     */
    protected abstract void refreshView(int viewId);

    public Context getContext() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
