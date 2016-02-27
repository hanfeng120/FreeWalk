package club.hanfeng.freewalk.mainpage.topbar;

import android.content.Context;

import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.interfaces.main.OnBottomBarSelectedListener;

/**
 * Created by HanFeng on 2016/1/23.
 */
public class MainPageTopBar extends BaseViewGroup implements OnBottomBarSelectedListener {

    private HomeTopBar homeTopBar;

    public MainPageTopBar(Context context) {
        super(context);
    }

    @Override
    public void onInitChildren() {
        homeTopBar = new HomeTopBar(getContext());
        homeTopBar.createRootView(null);
        homeTopBar.init();

        getRootView().addView(homeTopBar.getRootView());
    }

    @Override
    public void onBottomBarSelected(int index) {
        homeTopBar.setOnCheckedIndex(index);
    }

    public void setTopBarInfo(String sceneName, String cityName) {
        homeTopBar.setTopBarInfo(sceneName, cityName);
    }
}
