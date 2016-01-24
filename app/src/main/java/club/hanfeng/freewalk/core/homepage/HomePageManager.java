package club.hanfeng.freewalk.core.homepage;

import android.content.Context;

import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.tabbar.data.TabBarType;
import club.hanfeng.freewalk.core.homepage.data.HomePagePoi;
import club.hanfeng.freewalk.core.homepage.imp.IHomePageManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/24.
 */
public class HomePageManager implements IHomePageManager {

    private volatile static HomePageManager instance = null;

    private HomePageManager() {
        initBmobQuery();
    }

    private void initBmobQuery() {
    }

    public static HomePageManager getInstance() {
        if (instance == null) {
            synchronized (HomePageManager.class) {
                if (instance == null) {
                    instance = new HomePageManager();
                }
            }
        }
        return instance;
    }

    @Override
    public void getHomeTabBars(Context context, FindListener findListener) {
        BmobQuery<TabBarType> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("sid", FreeWalkApplication.getSid());
        bmobQuery.addWhereEqualTo("type", HomePageConstants.TYPE_HOME_TAB_BAR);
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(context, findListener);
    }

    @Override
    public void getScenes(Context context, String type, FindListener findListener) {
        BmobQuery<HomePagePoi> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("type", type);
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(context, findListener);
    }

}
