package club.hanfeng.freewalk.core.network;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/24.
 */
public abstract class NetworkTask implements INetworkTask {

    public abstract ArrayList<Pair<String, String>> getRequestParames();

    public <T> void requestNetwork(Context context, Class<T> t, FindListener findListener) {
        BmobQuery<T> bmobQuery = new BmobQuery();
        bmobQuery.setLimit(1000);
        if (getRequestParames() != null) {
            for (Pair<String, String> pair : getRequestParames()) {
                bmobQuery.addWhereEqualTo(pair.first, pair.second);
            }
        }
        bmobQuery.findObjects(context, findListener);
    }

}
