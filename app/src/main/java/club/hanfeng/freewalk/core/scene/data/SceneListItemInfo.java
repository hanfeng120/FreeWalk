package club.hanfeng.freewalk.core.scene.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhaoxunyi on 2016/1/25.
 */
public class SceneListItemInfo extends BmobObject {

    private String sid;
    private String name;
    private String aid;
    private String cityCode;
    private String cityName;
    private Integer type;
    private Integer num;

    public SceneListItemInfo() {

    }

    public String getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public String getAid() {
        return aid;
    }

    public String getCityCode() {
        return cityCode;
    }

    public Integer getType() {
        return type;
    }

    public Integer getNum() {
        return num;
    }

    public String getCityName() {
        return cityName;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
