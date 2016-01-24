package club.hanfeng.freewalk.core.homepage.imp;

import android.content.Context;

import java.util.List;

import club.hanfeng.freewalk.core.homepage.data.HomePagePoi;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/24.
 */
public interface IHomePageNetwork {

    List<HomePagePoi> getAllScene(Context context, FindListener findListener);

    List<HomePagePoi> getHotScene();

    List<HomePagePoi> getServerCenter();

    List<HomePagePoi> getToilet();

    List<HomePagePoi> getFood();

    List<HomePagePoi> getHotel();

    List<HomePagePoi> getShopping();

}
