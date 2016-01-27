package club.hanfeng.freewalk.core.studio.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by HanFeng on 2015/12/2.
 */
public class StudioInfo extends BmobObject {

    public String uid;
    public String id;
    public String sid;
    public String introduce;
    public String imageUrl;

    /**
     * 具体景点的名称
     */
    public String name;
    public int comments;
    public int stars;

    public StudioInfo() {
    }

    public String getUid() {
        return uid;
    }

    public String getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getComments() {
        return comments;
    }

    public int getStars() {
        return stars;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
