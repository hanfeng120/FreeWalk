package club.hanfeng.freewalk.core.photo.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by HanFeng on 2016/1/31.
 */
public class PhotoInfo extends BmobObject {

    private String photoName;
    private String photoUrl;
    private String userName;

    public PhotoInfo() {
    }

    public String getPhotoName() {
        return photoName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
