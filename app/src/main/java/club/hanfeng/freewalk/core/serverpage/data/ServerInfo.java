package club.hanfeng.freewalk.core.serverpage.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class ServerInfo extends BmobObject {

    private String sid;
    private String id;
    private String name;
    private String imgPath;
    private String url;
    private boolean isOpen;

    public ServerInfo() {
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
}
