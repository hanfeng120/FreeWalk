package club.hanfeng.freewalk.core.scene;

import android.content.Context;

import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.scene.data.SceneInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/24.
 */
public class SceneManager {

    private volatile static SceneManager sceneManager;

    private SceneManager() {

    }

    public static SceneManager getInstance() {
        if (sceneManager == null) {
            synchronized (SceneManager.class) {
                if (sceneManager == null) {
                    sceneManager = new SceneManager();
                }
            }
        }
        return sceneManager;
    }

    public void getSceneInfo(Context context, String id, FindListener findListener) {
        BmobQuery<SceneInfo> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("sid", FreeWalkApplication.getSid());
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.order("num");
        bmobQuery.findObjects(context, findListener);
    }

}
