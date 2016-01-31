package club.hanfeng.freewalk.core.collection;

import android.content.Context;

import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.collection.data.UserCollection;
import club.hanfeng.freewalk.core.photo.data.PhotoInfo;
import club.hanfeng.freewalk.core.serverpage.data.SceneListInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
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

    public void loadAllCollections(Context context, FindListener<UserCollection> listener) {
        BmobQuery<UserCollection> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(1000);
        bmobQuery.addWhereEqualTo("userName", BmobUser.getCurrentUser(context).getUsername());
        bmobQuery.findObjects(context, listener);
    }

    public void loadSceneInfo(Context context, String id, FindListener<SceneListInfo> listener) {
        BmobQuery<SceneListInfo> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(1000);
        bmobQuery.addWhereEqualTo("sid", FreeWalkApplication.getSid());
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.findObjects(context, listener);
    }

    public void saveStudioPhoto(Context context, String url, SaveListener saveListener) {
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setPhotoUrl(url);
        photoInfo.setUserName(BmobUser.getCurrentUser(context).getUsername());
        photoInfo.save(context, saveListener);
    }

    public void saveSceneCollection(Context context, String id, SaveListener saveListener) {
        saveCollection(context, id, CollectionConstants.TYPE_COLLECTION_SCENE, saveListener);
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
