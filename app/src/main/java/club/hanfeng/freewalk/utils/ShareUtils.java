package club.hanfeng.freewalk.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by HanFeng on 2016/2/28.
 */
public class ShareUtils {

    public static void shareInfo(Context context) {
        share(context, "", "");
    }

    public static void shareInfo(Context context, String info) {
        share(context, info, "");
    }

    public static void shareInfo(Context context, String info, String url) {
        share(context, info, url);
    }

    private static void share(Context context, String info, String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, "欢迎使用自由行智能景区语音导览APP！" + info + url + "http://www.xunger.cn");
        context.startActivity(Intent.createChooser(intent, "分享当前页面"));
    }

}
