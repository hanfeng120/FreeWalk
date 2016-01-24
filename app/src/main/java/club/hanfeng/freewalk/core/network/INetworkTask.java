package club.hanfeng.freewalk.core.network;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/24.
 */
public interface INetworkTask {

    ArrayList<Pair<String, String>> getRequestParames();

    <T> void requestNetwork(Context context, Class<T> t, FindListener findListener);

}
