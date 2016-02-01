package club.hanfeng.freewalk.download;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.maps.AMapException;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.download.DownloadConstants;
import club.hanfeng.freewalk.core.download.MapListBaseAdapter;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.framework.DataRefreshTask;
import club.hanfeng.freewalk.interfaces.view.IDataRefreshTask;
import club.hanfeng.freewalk.utils.OutputUtils;
import club.hanfeng.freewalk.utils.map.AMapUtils;

/**
 * Created by zhaoxunyi on 2016/2/1.
 */
public class MapListPage extends BaseViewGroup implements OfflineMapManager.OfflineMapDownloadListener {

    private OfflineMapManager offlineMapManager;
    private MapListBaseAdapter adapter;

    public MapListPage(Context context) {
        super(context);
        offlineMapManager = new OfflineMapManager(getContext(), null);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.map_list_page;
    }

    @Override
    public void onInitChildren() {
        MapsInitializer.sdcardDir = AMapUtils.getSdCacheDir(getContext());
        ListView listView = (ListView) getRootView().findViewById(R.id.listview);
        adapter = new MapListBaseAdapter(getContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    offlineMapManager.downloadByCityName(adapter.getItem(position).getCity());
                    OutputUtils.toastShort(getContext(), "已添加到下载列表");
                } catch (AMapException e) {
                    OutputUtils.toastShort(getContext(), "下载出错");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public List<IDataRefreshTask> getDataRefreshTasks() {
        List<IDataRefreshTask> list = new ArrayList<>();
        list.add(new DataRefreshTask(DownloadConstants.TASK_ID_MAP_LIST, 0));
        return list;
    }

    @Override
    public void onRequestLoadData(int key) {
        ArrayList<OfflineMapCity> list = offlineMapManager.getOfflineMapCityList();
        adapter.setData(list);
    }

    @Override
    public void onDownload(int i, int i1, String s) {
        Log.i("Progress", i1 + ">>>");
    }

    @Override
    public void onCheckUpdate(boolean b, String s) {

    }

    @Override
    public void onRemove(boolean b, String s, String s1) {

    }
}
