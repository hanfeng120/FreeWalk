package club.hanfeng.freewalk.bean;

/**
 * Created by HanFeng on 2015/11/4.
 */
public class UpdateInfo {

    public int versionCode;
    public String versionName;
    public String desc;//更新描述
    public String appUrl;//应用下载地址

    public UpdateInfo() {

    }

    public UpdateInfo(int versionCode, String versionName, String desc, String appUrl) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.desc = desc;
        this.appUrl = appUrl;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", desc='" + desc + '\'' +
                ", appUrl='" + appUrl + '\'' +
                '}';
    }
}
