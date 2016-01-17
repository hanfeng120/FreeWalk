package club.hanfeng.freewalk.bean;

/**
 * Created by HanFeng on 2015/12/2.
 */
public class DirectSendBean {

    public String uid;
    public String time;
    public String id;
    public String sid;
    public String introduce;
    public String imagePath;
    public String sceneName;
    public int comments;
    public int stars;

    public DirectSendBean() {
    }

    public DirectSendBean(String uid, String time, String id, String sid, String introduce, String imagePath, String sceneName, int comments, int stars) {
        this.uid = uid;
        this.time = time;
        this.id = id;
        this.sid = sid;
        this.introduce = introduce;
        this.imagePath = imagePath;
        this.sceneName = sceneName;
        this.comments = comments;
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "DirectSendBean{" +
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
