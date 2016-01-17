package club.hanfeng.freewalk.bean;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class ServerInfo {

    public String name;
    public String imgPath;
    public String url;

    public ServerInfo() {
    }

    public ServerInfo(String name, String imgPath, String url) {
        this.name = name;
        this.imgPath = imgPath;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "name='" + name + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
