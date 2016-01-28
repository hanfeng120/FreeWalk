package club.hanfeng.freewalk.core.user;

import android.content.Context;

import club.hanfeng.freewalk.core.user.data.MyUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhaoxunyi on 2016/1/28.
 */
public class UserManager {

    private static volatile UserManager instance;

    private UserManager() {

    }

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    public void loadUserInfo(Context context, String userName, FindListener findListener) {
        BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", userName);
        bmobQuery.findObjects(context, findListener);
    }

}
