package club.hanfeng.freewalk.core.studio.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by HanFeng on 2015/12/2.
 */
public class StudioInfo extends BmobObject {

    public String uid;
    public String time;
    public String id;
    public String sid;
    public String introduce;
    public String imagePath;
    public String sceneName;
    public int comments;
    public int stars;

    public StudioInfo() {
    }

    public StudioInfo(String uid, String sid, String id, String introduce, String imagePath, String sceneName, int comments, int stars) {
        this.uid = uid;
        this.sid = sid;
        this.id = id;
        this.introduce = introduce;
        this.imagePath = imagePath;
        this.sceneName = sceneName;
        this.comments = comments;
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "StudioInfo{" +
                "uid='" + uid + '\'' +
                ", time='" + time + '\'' +
                ", sid='" + sid + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", sceneName='" + sceneName + '\'' +
                ", comments=" + comments +
                ", stars=" + stars +
                '}';
    }
}
