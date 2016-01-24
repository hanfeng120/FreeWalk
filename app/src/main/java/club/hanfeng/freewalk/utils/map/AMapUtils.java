package club.hanfeng.freewalk.utils.map;

import android.content.Context;

import java.io.File;

import club.hanfeng.freewalk.utils.CommonUtils;

/**
 * Created by HanFeng on 2016/1/24.
 */
public class AMapUtils {

    public static String getSdCacheDir(Context context) {
        String sdPath = CommonUtils.getSDPath();
        File mapCache = new File(sdPath + "/vmap/");
        if (!mapCache.exists()) {
            mapCache.mkdirs();
        }
        return mapCache.getPath();
    }

}
