package club.hanfeng.freewalk.interfaces.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public interface IView {

    Context getContext();

    /**
     * step1.1 外部注入root view
     *
     * @param rootView
     * @see #createRootView(ViewGroup)
     */
    void setRootView(View rootView);

    /**
     * step1.2.1 内部生成root view
     *
     * @see #setRootView(View)
     */
    void createRootView(ViewGroup parent);

    /**
     * step1.2.2 获得root view的res id
     *
     * @return 0 如果不使用内部生成的方式
     */
    int getRootViewResId();

    View getRootView();

    /**
     * step2 设置OnClickListener，如果事件自己不处理，需要向上层传则由外部调用
     *
     * @param onClickListener
     */
    void setOnClickListener(OnClickListener onClickListener);

    OnClickListener getOnClickListener();

    /**
     * step3 初始化children
     */
    void onInitChildren();

    /**
     * step4 绑定View和Listener
     *
     * @see #setRootView(View)
     * @see #setOnClickListener(OnClickListener)
     * @see #getRootView()
     * @see #getOnClickListener()
     */
    void init();

    /**
     * 刷新view数据
     *
     * @param viewId
     */
    void onRefresh(int viewId);

    boolean handbleBackKeyPress();

}
