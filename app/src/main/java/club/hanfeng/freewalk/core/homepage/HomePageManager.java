package club.hanfeng.freewalk.core.homepage;

import android.content.Context;

import club.hanfeng.freewalk.core.homepage.data.HomePagePoi;
import club.hanfeng.freewalk.core.homepage.imp.IHomePageManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/24.
 */
public class HomePageManager implements IHomePageManager {

    private volatile static HomePageManager instance = null;
    private HomePageNetwork homePageNetwork = new HomePageNetwork();

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
    public void getAllScene(Context context, FindListener findListener) {
        setBmobQuery(context, HomePageConstants.TYPE_NORMAL_SCENE, findListener);
    }

    @Override
    public void getHotScene(Context context, FindListener findListener) {
        setBmobQuery(context, HomePageConstants.TYPE_HOT_SCENE, findListener);
    }

    @Override
    public void getServerCenter(Context context, FindListener findListener) {
        setBmobQuery(context, HomePageConstants.TYPE_SERVER, findListener);
    }

    @Override
    public void getToilet(Context context, FindListener findListener) {
        setBmobQuery(context, HomePageConstants.TYPE_TOILET, findListener);
    }

    @Override
    public void getFood(Context context, FindListener findListener) {
        setBmobQuery(context, HomePageConstants.TYPE_FOOD, findListener);
    }

    @Override
    public void getHotel(Context context, FindListener findListener) {
        setBmobQuery(context, HomePageConstants.TYPE_HOTEL, findListener);
    }

    @Override
    public void getShopping(Context context, FindListener findListener) {
        setBmobQuery(context, HomePageConstants.TYPE_SHOPPING, findListener);
    }

    private void setBmobQuery(Context context, int type, FindListener findListener) {
        BmobQuery<HomePagePoi> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("type", type);
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(context, findListener);
    }
}
