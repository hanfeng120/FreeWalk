package club.hanfeng.freewalk.core.download;

import android.content.Context;

import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.download.data.VoiceInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhaoxunyi on 2016/2/2.
 */
public class DownloadManager {

    private static volatile DownloadManager instance;

    private DownloadManager() {

    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public void loadAllVoiceRes(Context context, FindListener<VoiceInfo> findListener) {
        BmobQuery<VoiceInfo> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(1000);
        bmobQuery.addWhereEqualTo("sid", FreeWalkApplication.getSid());
        bmobQuery.findObjects(context, findListener);
    }

}
