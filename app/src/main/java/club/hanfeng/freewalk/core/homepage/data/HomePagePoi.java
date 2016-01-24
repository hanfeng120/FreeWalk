package club.hanfeng.freewalk.core.homepage.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by HanFeng on 2016/1/24.
 */
public class HomePagePoi extends BmobObject {

    private String sid;
    private String id;
    private int type;
    private String name;
    private String lon;
    private String lat;

    public HomePagePoi() {

    }

    public String getSid() {
        return sid;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
