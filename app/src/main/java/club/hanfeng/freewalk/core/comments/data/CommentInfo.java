package club.hanfeng.freewalk.core.comments.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhaoxunyi on 2016/2/2.
 */
public class CommentInfo extends BmobObject {

    private String sid;
    private String id;
    private String uid;
    private String nickName;
    private String portraitUrl;
    private int stars;
    private String comment;
    private String imgUrl;

    public CommentInfo() {
    }

    public String getSid() {
        return sid;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public int getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
