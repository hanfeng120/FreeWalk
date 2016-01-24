package club.hanfeng.freewalk.mainpage.content;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.BrowserActivity;
import club.hanfeng.freewalk.activity.SceneActivity;
import club.hanfeng.freewalk.adapter.ServerPageBaseAdapter;
import club.hanfeng.freewalk.adapter.ServerPageLvBaseAdapter;
import club.hanfeng.freewalk.bean.SceneListInfo;
import club.hanfeng.freewalk.bean.ServerInfo;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.utils.OutputUtils;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class ServerPage extends BaseViewGroup {

    private ListView listView;
    private GridView gridLayout;
    private List<ServerInfo> list;
    private List<SceneListInfo> listScene = new ArrayList<>();
    private String sid;//景区标识

    public ServerPage(Context activity) {
        super(activity);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.layout_serverpage;
    }

    @Override
    public void onInitChildren() {
        listView = (ListView) getRootView().findViewById(R.id.lv_sp);

        View header = View.inflate(getContext(), R.layout.header_lv_serverpage, null);
        gridLayout = (GridView) header.findViewById(R.id.gl_header_sp);
        listView.addHeaderView(header);

        checkSid();
    }

    private void checkSid() {
//        sid = SpUtils.getInstance(getContext()).getValue(SpConstants.SCENIC_ID, null);
//        if (sid != null) {
//            getDataFromServer();
//        } else {
//            //TODO 景区唯一编号为空，跳转到景区选择页面
//        }
        getDataFromServer();
    }

    public void getDataFromServer() {
        list = new ArrayList<>();
        list.add(new ServerInfo("景点分布", "http://192.168.20.75:8080/freewalk/images/btn_index_hotel.png", "null"));
        list.add(new ServerInfo("旅行贴士", "http://192.168.20.75:8080/freewalk/images/btn_index_graduate.png", "www.sina.cn"));
        list.add(new ServerInfo("本地交通", "http://192.168.20.75:8080/freewalk/images/btn_index_food.png", "www.qq.com"));
        list.add(new ServerInfo("更多攻略", "http://192.168.20.75:8080/freewalk/images/btn_index_amuse.png", ""));

        BmobQuery<SceneListInfo> bmob = new BmobQuery<>();
//        bmob.addWhereEqualTo("sid", sid);
        bmob.setLimit(1000);
        bmob.findObjects(getContext(), new FindListener<SceneListInfo>() {
            @Override
            public void onSuccess(List<SceneListInfo> list) {
                listScene = list;
                parseData();//解析数据
            }

            @Override
            public void onError(int i, String s) {
                OutputUtils.toastShort(getContext(), "网络错误，请重试");
            }
        });

    }

    /**
     * 解析数据
     */
    public void parseData() {
        gridLayout.setAdapter(new ServerPageBaseAdapter(getContext(), list));
        listView.setAdapter(new ServerPageLvBaseAdapter(getContext(), listScene));


        gridLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BrowserActivity.class);
                if (list.get(position).url.equals("null")) {
                    OutputUtils.toastShort(getContext(), "即将开放，敬请期待");
                } else {
                    intent.putExtra("url", list.get(position).url);
                    intent.putExtra("title", list.get(position).name);
                    getContext().startActivity(intent);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                Intent intent = new Intent(getContext(), SceneActivity.class);
                intent.putExtra("title", listScene.get(position).name);
                intent.putExtra("id", listScene.get(position).id);
                getContext().startActivity(intent);
            }
        });

    }

}
