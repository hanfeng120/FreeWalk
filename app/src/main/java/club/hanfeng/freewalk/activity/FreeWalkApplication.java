package club.hanfeng.freewalk.activity;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

import club.hanfeng.freewalk.constants.Constants;
import club.hanfeng.freewalk.utils.sp.SpConstants;
import club.hanfeng.freewalk.utils.sp.SpUtils;
import cn.bmob.v3.Bmob;

/**
 * Created by HanFeng on 2016/1/10.
 */
public class FreeWalkApplication extends Application {

    private static Context context;
    private static String sid;
    private static String cityCode;

    @Override
    public void onCreate() {
        super.onCreate();
        FreeWalkApplication.context = getApplicationContext();

        init();
        setSid(null, null);
    }

    private void init() {
        initBmob();
        initXUtils();
    }

    private void initBmob() {
        Bmob.initialize(this, Constants.BMOB_ID);
    }

    private void initXUtils() {
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }

    public static void setSid(String sid, String cityCode) {
        if (sid == null || cityCode == null) {
            FreeWalkApplication.sid = SpUtils.getInstance(context).getValue(SpConstants.SCENIC_ID, "0001");
            FreeWalkApplication.sid = SpUtils.getInstance(context).getValue(SpConstants.CITY_CODE, "");
        } else {
            FreeWalkApplication.sid = sid;
            FreeWalkApplication.cityCode = cityCode;
        }
    }

    public static String getSid() {
        return FreeWalkApplication.sid;
    }

    public static String getCityCode() {
        return FreeWalkApplication.cityCode;
    }

}
