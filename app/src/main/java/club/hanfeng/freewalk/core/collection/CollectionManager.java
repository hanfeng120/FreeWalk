package club.hanfeng.freewalk.core.collection;

import android.content.Context;

import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.collection.data.UserCollection;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zhaoxunyi on 2016/1/28.
 */
public class CollectionManager {

    private static volatile CollectionManager instance;

    private CollectionManager() {

    }

    public static CollectionManager getInstance() {
        if (instance == null) {
            synchronized (CollectionManager.class) {
                if (instance == null) {
                    instance = new CollectionManager();
                }
            }
        }
        return instance;
    }

    public void saveSceneCollection(Context context, String id, SaveListener saveListener) {
        saveCollection(context, id, CollectionConstants.TYPE_COLLECTION_SCENE, saveListener);
    }

    public void saveStudioCollection(Context context, String id, SaveListener saveListener) {
        saveCollection(context, id, CollectionConstants.TYPE_COLLECTION_STUDIO, saveListener);
    }

    public void saveTravelCollection(Context context, String id, SaveListener saveListener) {
        saveCollection(context, id, CollectionConstants.TYPE_COLLECTION_TRAVELS, saveListener);
    }

    private void saveCollection(Context context, String id, String type, SaveListener saveListener) {
        UserCollection userCollection = new UserCollection();
        if (BmobUser.getCurrentUser(context) == null) {
            saveListener.onFailure(-1, "用户未登录");
        }
        userCollection.setUserName(BmobUser.getCurrentUser(context).getUsername());
        userCollection.setSid(FreeWalkApplication.getSid());
        userCollection.setId(id);
        userCollection.setType(type);
        userCollection.save(context, saveListener);
    }

}
