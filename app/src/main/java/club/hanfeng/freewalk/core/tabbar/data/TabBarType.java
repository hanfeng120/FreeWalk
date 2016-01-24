package club.hanfeng.freewalk.core.tabbar.data;

import cn.bmob.v3.BmobObject;

/**
 * 选项卡类型
 * Created by HanFeng on 2016/1/24.
 */
public class TabBarType extends BmobObject {

    private String sid;
    private String tid;
    private String type;
    private String name;
    private int num;

    public String getSid() {
        return sid;
    }

    public String getId() {
        return tid;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setId(String tid) {
        this.tid = tid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
