package club.hanfeng.freewalk.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import club.hanfeng.freewalk.R;

/**
 * Created by HanFeng on 2015/10/21.
 */
public class BasePage {

    public Context mActivity;
    public View rootView;
    public FrameLayout flBasePage;

    public BasePage(Context context) {
        this.mActivity = context;
        initView();
    }

    /**
     * 加载布局文件
     */
    public void initView() {
        rootView = View.inflate(mActivity, R.layout.layout_basepage, null);
        flBasePage = (FrameLayout) rootView.findViewById(R.id.fl_basepage);
    }

    /**
     * 加载数据，需要子类重写
     */
    public void initData() {

    }

}
