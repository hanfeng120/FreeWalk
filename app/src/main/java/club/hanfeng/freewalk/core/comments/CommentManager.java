package club.hanfeng.freewalk.core.comments;

import android.content.Context;

import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.comments.data.CommentInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhaoxunyi on 2016/2/2.
 */
public class CommentManager {

    private static volatile CommentManager instance;

    private CommentManager() {

    }

    public static CommentManager getInstance() {
        if (instance == null) {
            synchronized (CommentManager.class) {
                if (instance == null) {
                    instance = new CommentManager();
                }
            }
        }
        return instance;
    }

    public void loadAllComments(Context context, String id, FindListener<CommentInfo> findListener) {
        BmobQuery<CommentInfo> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(1000);
        bmobQuery.addWhereEqualTo("sid", FreeWalkApplication.getSid());
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(context, findListener);
    }

}
