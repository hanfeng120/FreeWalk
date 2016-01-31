package club.hanfeng.freewalk.core.user;

import android.content.Context;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;

import java.io.File;

import club.hanfeng.freewalk.core.user.data.MyUser;
import club.hanfeng.freewalk.utils.CommonUtils;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

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

    public void uploadPortrait(Context context, UploadListener listener) {
        BmobProFile.getInstance(context).upload(getPortraitFile().getPath(), listener);
    }

    public void updateUserPortrait(Context context, String portraitUrl, UpdateListener listener) {
        MyUser myUser = new MyUser();
        myUser.setPortraitUrl(portraitUrl);
        myUser.update(context, BmobUser.getCurrentUser(context).getObjectId(), listener);
    }

    public File getPortraitFile() {
        File file = new File(CommonUtils.getSDPath() + "/cache/portrait.jpg");
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        return file;
    }

}
