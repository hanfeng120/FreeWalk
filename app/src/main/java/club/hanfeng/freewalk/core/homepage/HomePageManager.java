package club.hanfeng.freewalk.core.homepage;

import java.util.List;

import club.hanfeng.freewalk.core.homepage.data.HomePagePoi;
import club.hanfeng.freewalk.core.homepage.imp.IHomePageManager;

/**
 * Created by HanFeng on 2016/1/24.
 */
public class HomePageManager implements IHomePageManager {

    private volatile HomePageManager instance = null;
    private HomePageNetwork homePageNetwork = new HomePageNetwork();

    private HomePageManager() {
    }

    public HomePageManager getInstance() {
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
    public List<HomePagePoi> getAllScene() {
        return homePageNetwork.getAllScene();
    }

    @Override
    public List<HomePagePoi> getHotScene() {
        return homePageNetwork.getHotScene();
    }

    @Override
    public List<HomePagePoi> getServerCenter() {
        return homePageNetwork.getServerCenter();
    }

    @Override
    public List<HomePagePoi> getToilet() {
        return homePageNetwork.getToilet();
    }

    @Override
    public List<HomePagePoi> getFood() {
        return homePageNetwork.getFood();
    }

    @Override
    public List<HomePagePoi> getHotel() {
        return homePageNetwork.getHotel();
    }

    @Override
    public List<HomePagePoi> getShopping() {
        return homePageNetwork.getShopping();
    }
}
