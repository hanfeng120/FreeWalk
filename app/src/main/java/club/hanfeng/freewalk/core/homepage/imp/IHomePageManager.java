package club.hanfeng.freewalk.core.homepage.imp;

import java.util.List;

import club.hanfeng.freewalk.core.homepage.data.HomePagePoi;

/**
 * Created by HanFeng on 2016/1/24.
 */
public interface IHomePageManager {

    List<HomePagePoi> getAllScene();

    List<HomePagePoi> getHotScene();

    List<HomePagePoi> getServerCenter();

    List<HomePagePoi> getToilet();

    List<HomePagePoi> getFood();

    List<HomePagePoi> getHotel();

    List<HomePagePoi> getShopping();

}
