package club.hanfeng.freewalk.core.collection.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhaoxunyi on 2016/1/27.
 */
public class UserCollection extends BmobObject {

    private String userName;
    private String sid;
    private String id;
    private String type;

    public String getUserName() {
        return userName;
    }

    public String getSid() {
        return sid;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }
}
