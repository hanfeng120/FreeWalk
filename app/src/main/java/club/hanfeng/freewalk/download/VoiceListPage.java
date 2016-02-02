package club.hanfeng.freewalk.download;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.download.DownloadConstants;
import club.hanfeng.freewalk.core.download.DownloadManager;
import club.hanfeng.freewalk.core.download.VoiceListBaseAdapter;
import club.hanfeng.freewalk.core.download.data.VoiceInfo;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.framework.DataRefreshTask;
import club.hanfeng.freewalk.interfaces.view.IDataRefreshTask;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhaoxunyi on 2016/2/1.
 */
public class VoiceListPage extends BaseViewGroup {

    private VoiceListBaseAdapter adapter;

    public VoiceListPage(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.voice_list_page;
    }

    @Override
    public void onInitChildren() {
        ListView listView = (ListView) getRootView().findViewById(R.id.listview);
        adapter = new VoiceListBaseAdapter(getContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public List<IDataRefreshTask> getDataRefreshTasks() {
        List<IDataRefreshTask> list = new ArrayList<>();
        list.add(new DataRefreshTask(DownloadConstants.TASK_ID_VOICE_LIST, 0));
        return list;
    }

    @Override
    public void onRequestLoadData(int key) {
        DownloadManager.getInstance().loadAllVoiceRes(getContext(), new FindListener<VoiceInfo>() {
            @Override
            public void onSuccess(List<VoiceInfo> list) {
                adapter.setData(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
