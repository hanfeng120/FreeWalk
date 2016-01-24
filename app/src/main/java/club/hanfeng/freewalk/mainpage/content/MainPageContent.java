package club.hanfeng.freewalk.mainpage.content;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.amap.api.maps.AMap;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.implement.main.OnBottomBarSelectedListener;
import club.hanfeng.freewalk.implement.view.IView;

/**
 * Created by HanFeng on 2016/1/23.
 */
public class MainPageContent extends BaseViewGroup implements OnBottomBarSelectedListener {

    private IView homePage, serverPage, findPage, userPage;

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
        reLoadAMap(index);
        FrameLayout rootView = (FrameLayout) getRootView();
        if (rootView.getChildAt(0) != children.get(index).getRootView()) {
            rootView.removeAllViews();
            rootView.addView(children.get(index).getRootView());
        }
    }

    private void reLoadAMap(int index) {
        if (index == 0) {
            ((HomePage) homePage).reLoadMap();
        }
    }

    public View getAMapView() {
        return homePage.getRootView().findViewById(R.id.amap);
    }

    public void setAMap(AMap aMap) {
        ((HomePage) homePage).setAMap(aMap);
    }
}
