package club.hanfeng.freewalk.core.download.data;

import java.util.ArrayList;

/**
 * Created by zhaoxunyi on 2016/2/2.
 */
public class VoiceInfo {

    private String sid;
    private String id;
    private String sceneName;
    private long size;
    private ArrayList<String> voicesUrl;
    private String introduce;

    public VoiceInfo() {
    }

    public String getSid() {
        return sid;
    }

    public String getId() {
        return id;
    }

    public String getSceneName() {
        return sceneName;
    }

    public long getSize() {
        return size;
    }

    public ArrayList<String> getVoicesUrl() {
        return voicesUrl;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setVoicesUrl(ArrayList<String> voicesUrl) {
        this.voicesUrl = voicesUrl;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
