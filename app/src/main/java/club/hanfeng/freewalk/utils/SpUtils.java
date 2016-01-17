package club.hanfeng.freewalk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HanFeng on 2015/10/27.
 */
public final class SpUtils {

    /**
     * 景区标识sid
     */
    public static final String SCENIC_ID = "SCENIC_ID";
    public static final String USER_ICON = "USER_ICON";//头像，保存头像imagePath
    public static final String USER_TDCODE = "USER_ICON";//二维码，保存二维码imagePath

    public static final String SETTING_NEWS = "SETTING_NEWS";
    public static final String SETTING_AUTOPLAY = "SETTING_AUTOPLAY";
    public static final String SETTING_DOWNLOAD = "SETTING_DOWNLOAD";
    public static final String VERSION_CODE = "VERSION_CODE";
    public static final String VERSION_NAME = "VERSION_NAME";

    private static SharedPreferences sp;
    private static SpUtils instance;

    private SpUtils() {

    }

    public static SpUtils getInstance(Context context) {
        if (instance == null) {
            sp = context.getSharedPreferences("freewalk", Context.MODE_PRIVATE);
            instance = new SpUtils();
        }
        return instance;
    }

    /**
     * 保存数据
     *
     * @param key
     * @param value 支持String,int,boolean三种类型
     */
    public void save(String key, Object value) {
        if (value instanceof String) {
            sp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Integer) {
            sp.edit().putInt(key, (Integer) value).commit();
        } else if (value instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) value).commit();
        }
    }

    /**
     * 根据key获取对应的值，返回结果或者默认值
     *
     * @param key
     * @param defValue
     * @return
     */
    public <T> T getValue(String key, T defValue) {
        T t = null;
        if (defValue == null || defValue instanceof String) {
            t = (T) sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            Integer value = sp.getInt(key, (Integer) defValue);
            t = (T) value;
        } else if (defValue instanceof Boolean) {
            Boolean value = sp.getBoolean(key, (Boolean) defValue);
            t = (T) value;
        }
        return t;
    }

    /**
     * 根据key移除对应的value
     *
     * @param key
     */
    public void remove(String key) {
        sp.edit().remove(key);
    }


}
