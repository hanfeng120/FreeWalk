package club.hanfeng.freewalk.core.scene.data;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 景点详情页面信息
 * <p/>
 * Created by HanFeng on 2015/10/22.
 * Update on 2015/11/26     1.增加分类型图文混排内容
 */
public class SceneInfo extends BmobObject {

    public String sid;
    public String id;
    public Integer type;
    public String name;
    public String nameEN;
    public Integer star;
    public String comment;
    public Integer num;
    /**
     * 分类型纯文本内容
     */
    public String content;

    /**
     * 分类型图片的地址
     */
    public String imagePath;

    /**
     * 语音导览资源文件地址
     */
    public String voicePath;

    /**
     * 景点头部展示图片
     */
    public List<String> headImages;
    public String lon;
    public String lat;

    public SceneInfo() {
    }

}
