package club.hanfeng.freewalk.core.photo;

import android.content.Context;

import club.hanfeng.freewalk.core.photo.data.PhotoInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/31.
 */
public final class PhotoManager {

    private static volatile PhotoManager instance;

    private PhotoManager() {

    }

    public static PhotoManager getInstance() {
        if (instance == null) {
            synchronized (PhotoManager.class) {
                if (instance == null) {
                    instance = new PhotoManager();
                }
            }
        }
        return instance;
    }

    public void loadAllPhotos(Context context, FindListener<PhotoInfo> listener) {
        BmobQuery<PhotoInfo> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(context, listener);
    }

}
