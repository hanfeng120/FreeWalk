package club.hanfeng.freewalk.core.homepage.imp;

import android.content.Context;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/24.
 */
public interface IHomePageManager {

    void getAllScene(Context context, FindListener findListener);

    void getHotScene(Context context, FindListener findListener);

    void getServerCenter(Context context, FindListener findListener);

    void getToilet(Context context, FindListener findListener);

    void getFood(Context context, FindListener findListener);

    void getHotel(Context context, FindListener findListener);

    void getShopping(Context context, FindListener findListener);

}
