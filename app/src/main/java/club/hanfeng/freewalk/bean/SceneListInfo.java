package club.hanfeng.freewalk.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class SceneListInfo extends BmobObject implements Parcelable {

    /**
     * 景区编号
     */
    public String sid;

    /**
     * 景点编号
     */
    public String id;

    /**
     * 景点名称
     */
    public String name;

    /**
     * 景点图片地址
     */
    public String imgPath;

    /**
     * 附加信息 热门程度，多少人去过
     */
    public String star;

    public SceneListInfo() {

    }


    @Override
    public String toString() {
        return "SceneListInfo{" +
                "sid='" + sid + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", star=" + star +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sid);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imgPath);
        dest.writeString(this.star);
    }

    protected SceneListInfo(Parcel in) {
        this.sid = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.imgPath = in.readString();
        this.star = in.readString();
    }

    public static final Parcelable.Creator<SceneListInfo> CREATOR = new Parcelable.Creator<SceneListInfo>() {
        public SceneListInfo createFromParcel(Parcel source) {
            return new SceneListInfo(source);
        }

        public SceneListInfo[] newArray(int size) {
            return new SceneListInfo[size];
        }
    };
}
