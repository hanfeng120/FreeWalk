package club.hanfeng.freewalk.core.homepage.imp;

import android.content.Context;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/24.
 */
public interface IHomePageManager {

    void getHomeTabBars(Context context, FindListener findListener);

    void getScenes(Context context, String type, FindListener findListener);

}
