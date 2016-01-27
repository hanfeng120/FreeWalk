package club.hanfeng.freewalk.core.studio;

import android.content.Context;

import com.bmob.btp.callback.UploadListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.studio.data.StudioInfo;
import club.hanfeng.freewalk.interfaces.studio.IStudioManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

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

    public File getCacheFile(String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    public String getPhotoName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return StudioConstants.STUDIO_CACHE_PATH + dateFormat.format(date) + ".jpg";
    }

    public void upLoadStudioPic(Context context, String filePath, UploadListener uploadListener) {

    }

    public void upLoadStudio(Context context, StudioInfo studioInfo, SaveListener listener) {
        studioInfo.save(context, listener);
    }
}
