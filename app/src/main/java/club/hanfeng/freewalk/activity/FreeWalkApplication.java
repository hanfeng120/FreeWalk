package club.hanfeng.freewalk.activity;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

import club.hanfeng.freewalk.utils.SpUtils;

/**
 * Created by HanFeng on 2016/1/10.
 */
public class FreeWalkApplication extends Application {

    private static Context context;
    public static String sid;

    @Override
    public void onCreate() {
        super.onCreate();
        FreeWalkApplication.context = getApplicationContext();

        initXUtils();
        setSid(null);
    }

    private void initXUtils() {
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }

    public static void setSid(String sid) {
        if (sid == null) {
            FreeWalkApplication.sid = SpUtils.getInstance(context).getValue(SpUtils.SCENIC_ID, null);
        } else {
            FreeWalkApplication.sid = sid;
        }
    }

}
