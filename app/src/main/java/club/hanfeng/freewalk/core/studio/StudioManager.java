package club.hanfeng.freewalk.core.studio;

import android.content.Context;

import java.io.File;

import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.studio.data.StudioInfo;
import club.hanfeng.freewalk.interfaces.studio.IStudioManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhaoxunyi on 2016/1/27.
 */
public class StudioManager implements IStudioManager {

    private static volatile StudioManager instance;

    private StudioManager() {

    }

    public static StudioManager getInstance() {
        if (instance == null) {
            synchronized (StudioManager.class) {
                if (instance == null) {
                    instance = new StudioManager();
                }
            }
        }
        return instance;
    }


    @Override
    public void getAllStudios(Context context, FindListener findListener) {
        BmobQuery<StudioInfo> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(1000);
        bmobQuery.order("createdAt");
        bmobQuery.addWhereEqualTo("sid", FreeWalkApplication.getSid());
        bmobQuery.findObjects(context, findListener);
    }

    public File getCacheFile() {
        File file = new File(StudioConstants.STUDIO_CACHE_PATH);
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        return file;
    }
}
