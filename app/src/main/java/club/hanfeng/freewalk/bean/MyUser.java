package club.hanfeng.freewalk.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by HanFeng on 2015/11/6.
 */
public class MyUser extends BmobUser {

    private String nickName;
    private String sex;
    private String tag;//个性签名
    private String iconUrl;
    private String codeUrl;

    private static final long serialVersionUID = 1L;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }
}
