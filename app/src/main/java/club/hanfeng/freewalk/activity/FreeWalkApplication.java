package club.hanfeng.freewalk.activity;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

import club.hanfeng.freewalk.constants.Constants;
import club.hanfeng.freewalk.core.utils.ImageLoaderUtil;
import club.hanfeng.freewalk.environment.ConstantsEnv;
import club.hanfeng.freewalk.environment.DebugEnv;
import club.hanfeng.freewalk.utils.sp.SpConstants;
import club.hanfeng.freewalk.utils.sp.SpUtils;
import cn.bmob.v3.Bmob;

/**
 * Created by HanFeng on 2016/1/10.
 */
public class FreeWalkApplication extends Application {

    private static Context context;
    private static String sid;
    private static String sceneName;
    private static String cityCode;
    private static String cityName;

    @Override
    public void onCreate() {
        super.onCreate();
        FreeWalkApplication.context = getApplicationContext();
        initFreeWalkApplication();
    }

    private void initFreeWalkApplication() {
        initCommon();
        initBmob();
        initXUtils();
    }

    private void initCommon() {
        setSid(null, null, null, null);
        ConstantsEnv.init(this);
        ImageLoaderUtil.initImageLoader(getApplicationContext());
    }

    private void initBmob() {
        Bmob.initialize(this, Constants.BMOB_ID);
    }

    private void initXUtils() {
        x.Ext.init(this);
        if (DebugEnv.DEBUG) {
            x.Ext.setDebug(true);
        }
    }

    public static void setSid(String sid, String cityCode, String cityName, String sceneName) {
        if (sid == null || cityCode == null || cityName == null || sceneName == null) {
            FreeWalkApplication.sid = SpUtils.getInstance(context).getValue(SpConstants.SCENIC_ID, "32030001");
            FreeWalkApplication.sceneName = SpUtils.getInstance(context).getValue(SpConstants.SCENE_NAME, "天安门");
            FreeWalkApplication.cityCode = SpUtils.getInstance(context).getValue(SpConstants.CITY_CODE, "010");
            FreeWalkApplication.cityName = SpUtils.getInstance(context).getValue(SpConstants.CITY_NAME, "北京");
        } else {
            FreeWalkApplication.sid = sid;
            FreeWalkApplication.cityCode = cityCode;
            FreeWalkApplication.cityName = cityName;
            SpUtils.getInstance(context).save(SpConstants.SCENIC_ID, sid);
            SpUtils.getInstance(context).save(SpConstants.SCENE_NAME, sceneName);
            SpUtils.getInstance(context).save(SpConstants.CITY_CODE, cityCode);
            SpUtils.getInstance(context).save(SpConstants.CITY_NAME, cityName);
        }
    }

    public static String getSid() {
        return FreeWalkApplication.sid;
    }

    public static String getCityCode() {
        return FreeWalkApplication.cityCode;
    }

    public static String getCityName() {
        return FreeWalkApplication.cityName;
    }

    public static String getSceneName() {
        return FreeWalkApplication.sceneName;
    }

}
