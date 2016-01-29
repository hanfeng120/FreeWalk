package club.hanfeng.freewalk.environment;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ConstantsEnv {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static float DENSITY;

    public static void init(Context context) {
        Resources resources = context.getResources();
        getDeviceMetrics(context);
    }

    private static void getDeviceMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        SCREEN_WIDTH = metric.widthPixels;
        SCREEN_HEIGHT = metric.heightPixels;
        DENSITY = metric.density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int pxValueOfdip(float dpValue) {
        return (int) (dpValue * DENSITY + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int dipValueOfpx(float pxValue) {
        return (int) (pxValue / DENSITY + 0.5f);
    }
}
