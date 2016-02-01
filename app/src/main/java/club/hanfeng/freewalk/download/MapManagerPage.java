package club.hanfeng.freewalk.download;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.download.DownloadConstants;
import club.hanfeng.freewalk.core.download.MapManagerBaseAdapter;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.framework.DataCenter;
import club.hanfeng.freewalk.framework.DataRefreshTask;
import club.hanfeng.freewalk.interfaces.view.IDataRefreshTask;
import club.hanfeng.freewalk.utils.map.AMapUtils;

/**
 * Created by zhaoxunyi on 2016/2/1.
 */
public class MapManagerPage extends BaseViewGroup implements OfflineMapManager.OfflineMapDownloadListener {

    private OfflineMapManager offlineMapManager;
    private List<OfflineMapCity> data;
    private MapManagerBaseAdapter adapter;

    public MapManagerPage(Context context) {
        super(context);
        offlineMapManager = new OfflineMapManager(getContext(), this);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.map_manager_page;
    }

    @Override
    public void onInitChildren() {
        MapsInitializer.sdcardDir = AMapUtils.getSdCacheDir(getContext());
        ListView listView = (ListView) getRootView().findViewById(R.id.listview);
        adapter = new MapManagerBaseAdapter(getContext());
        listView.setAdapter(adapter);

    }

    @Override
    public void onDataChange(int key) {
        if (key == DownloadConstants.TASK_ID_MAP_MANAGER) {
            onRequestLoadData(key);
        }
    }

    @Override
    public List<IDataRefreshTask> getDataRefreshTasks() {
        DataCenter.getInstance().registerListener(DownloadConstants.TASK_ID_MAP_MANAGER, this);
        List<IDataRefreshTask> list = new ArrayList<>();
        list.add(new DataRefreshTask(DownloadConstants.TASK_ID_MAP_MANAGER, 0));
        return list;
    }

    @Override
    public void onRequestLoadData(int key) {
        combinData(requestDownloadingData(), requestDownloadData());
    }

    private ArrayList<OfflineMapCity> requestDownloadingData() {
        return offlineMapManager.getDownloadingCityList();
    }

    private ArrayList<OfflineMapCity> requestDownloadData() {
        return offlineMapManager.getDownloadOfflineMapCityList();
    }

    private void combinData(ArrayList<OfflineMapCity> downloading, ArrayList<OfflineMapCity> download) {
        data = new ArrayList<>();
        data.addAll(downloading);
        data.addAll(download);
        adapter.setData(data);
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
