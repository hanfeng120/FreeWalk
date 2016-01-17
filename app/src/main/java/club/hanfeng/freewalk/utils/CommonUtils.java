package club.hanfeng.freewalk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Formatter;

/**
 * Created by HanFeng on 2015/10/23.
 */
public class CommonUtils {

    /**
     * 返回版本名称
     *
     * @return
     */
    public static String getVerName(Context context) {
        String version = "";
        //1. 得到PackageManager对象
        PackageManager packageManager = context.getPackageManager();
        //2. 得到当前应用的PackageInfo对象
        try {

            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            //3. 得到当前版本号
            version = packageInfo.versionName;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return version;
    }

    /**
     * 返回版本号
     *
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = 1;
        //1. 得到PackageManager对象
        PackageManager packageManager = context.getPackageManager();
        //2. 得到当前应用的PackageInfo对象
        try {

            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            //3. 得到当前版本号
            verCode = packageInfo.versionCode;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 从Url中获取文件的名称
     *
     * @param url
     * @return 返回文件名
     */
    public static String getFileNameFromUrl(String url) {
        String fileName = "";
        if(url != null){
            fileName = url.substring(url.lastIndexOf("/") + 1);
        }
        return fileName;
    }

    public static String combinePath(String url, String dir) {
        String path = getSDPath() + dir + getFileNameFromUrl(url);
        Log.e("TAG",path);
        return path;
    }

    /**
     * 根据文件名判断文件是否存在
     *
     * @param path 默认到sd卡中的freewalk目录
     * @return
     */
    public static boolean isExist(String url,String path) {
        String fileName = getFileNameFromUrl(url);
        File file = new File(getSDPath() + path + fileName);
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 根据文件判断是否存在
     *
     * @param file
     * @return
     */
    public static boolean isExist(File file) {
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获得应用在SD卡中的路径
     *
     * @return
     */
    public static String getSDPath() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/freewalk");
        if(!file.exists()){
            file.mkdirs();
            Log.e("TAG","创建一个文件夹");
        }
        Log.e("TAG", "getSDPath()" + file.getAbsolutePath());
        return file.getAbsolutePath();
    }


//    /**
//     * 根据Url下载文件并保存到指定的路径下
//     *
//     * @param url 文件地址
//     * @param path 文件保存路径
//     * @param pb 进度条对象
//     */
//    public static void downLoadFile(String url, final String path, final ProgressBar pb) {
//        HttpUtils utils = new HttpUtils();
//        File parentFile = new File(getSDPath() + path).getParentFile();
//        if (!parentFile.exists()) {
//            parentFile.mkdirs();
//        }
//        String filePath = "/sdcard/freewalk" + path;
//
//        utils.download(url, filePath, true, true, new RequestCallBack<File>() {
//
//            @Override
//            public void onLoading(long total, long current, boolean isUploading) {
//                if (pb != null) {
//                    pb.setMax((int) total);
//                    pb.incrementProgressBy((int) current);
//                    Log.e("TAG", current + "" + total + "文件下载进度。。。");
//                }
//            }
//
//            @Override
//            public void onSuccess(ResponseInfo<File> responseInfo) {
//
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//
//            }
//        });
//    }

    /**
     * 把毫秒转换成：1:20:30这里形式
     * @param timeMs
     * @return
     */
    public static String stringForTime(long timeMs) {
        int totalSeconds = (int) (timeMs / 1000);
        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;

        int hours = totalSeconds / 3600;

        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter();

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

}
