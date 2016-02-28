package club.hanfeng.freewalk.download;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.download.DownloadConstants;
import club.hanfeng.freewalk.core.download.DownloadManager;
import club.hanfeng.freewalk.core.download.VoiceListBaseAdapter;
import club.hanfeng.freewalk.core.download.data.VoiceInfo;
import club.hanfeng.freewalk.core.utils.FreeWalkToast;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.framework.DataRefreshTask;
import club.hanfeng.freewalk.interfaces.view.IDataRefreshTask;
import club.hanfeng.freewalk.utils.CommonUtils;
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
                downloadFile(adapter.getItem(position).getVoicesUrl());
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

    public void downloadFile(String voicePath) {
        final String filePath = CommonUtils.combinePath(voicePath, "/voice/");
        if (createFileOnLocal(filePath).exists()) {
            FreeWalkToast.shortToast("文件已经存在");
        } else {
            downLoadFile(filePath, voicePath);
        }

    }

    private File createFileOnLocal(String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    private void downLoadFile(final String filePath, String voicePath) {
        RequestParams requestParams = new RequestParams(voicePath);
        requestParams.setSaveFilePath(filePath);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
            }

            @Override
            public void onSuccess(File result) {
                FreeWalkToast.shortToast("文件下载成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                FreeWalkToast.shortToast("资源加载失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
