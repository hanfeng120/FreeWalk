package club.hanfeng.freewalk.core.homepage;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.bean.SceneListInfo;
import club.hanfeng.freewalk.core.homepage.data.HomePagePoi;
import club.hanfeng.freewalk.core.homepage.imp.IHomePageNetwork;
import club.hanfeng.freewalk.core.network.INetworkTask;
import club.hanfeng.freewalk.core.network.NetworkTask;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/24.
 */
public class HomePageNetwork implements IHomePageNetwork {

    @Override
    public List<HomePagePoi> getAllScene(Context context, FindListener findListener) {
        INetworkTask networkTask = new NetworkTask() {
            @Override
            public ArrayList<Pair<String, String>> getRequestParames() {
                return null;
            }
        };
        networkTask.requestNetwork(context, SceneListInfo.class, findListener);
        return null;
    }

    @Override
    public List<HomePagePoi> getHotScene() {
        return null;
    }

    @Override
    public List<HomePagePoi> getServerCenter() {
        return null;
    }

    @Override
    public List<HomePagePoi> getToilet() {
        return null;
    }

    @Override
    public List<HomePagePoi> getFood() {
        return null;
    }

    @Override
    public List<HomePagePoi> getHotel() {
        return null;
    }

    @Override
    public List<HomePagePoi> getShopping() {
        return null;
    }
}
