package club.hanfeng.freewalk.mainpage.content;

import android.content.Context;
import android.widget.FrameLayout;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.interfaces.main.OnBottomBarSelectedListener;
import club.hanfeng.freewalk.interfaces.view.IView;
import club.hanfeng.freewalk.mainpage.MainPageConstants;
import club.hanfeng.freewalk.mainpage.content.server.ServerPage;

/**
 * Created by HanFeng on 2016/1/23.
 */
public class MainPageContent extends BaseViewGroup implements OnBottomBarSelectedListener {

    private IView homePage, serverPage, findPage;
    private UserPage userPage;

    public MainPageContent(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.main_page_content;
    }

    @Override
    public void onInitChildren() {
        homePage = new HomePage(getContext());
        homePage.createRootView(null);
        homePage.init();

        serverPage = new ServerPage(getContext());
        serverPage.createRootView(null);
        serverPage.init();

        findPage = new FindPage(getContext());
        findPage.createRootView(null);
        findPage.init();

        userPage = new UserPage(getContext());
        userPage.setTaskId(MainPageConstants.TASK_ID_USERPAGE);
        userPage.createRootView(null);
        userPage.init();

        children.add(homePage);
        children.add(serverPage);
        children.add(findPage);
        children.add(userPage);

        onBottomBarSelected(0);

    }

    @Override
    public void onBottomBarSelected(int index) {
        FrameLayout rootView = (FrameLayout) getRootView();
        if (rootView.getChildAt(0) != children.get(index).getRootView()) {
            rootView.removeAllViews();
            rootView.addView(children.get(index).getRootView());
        }
    }

}
