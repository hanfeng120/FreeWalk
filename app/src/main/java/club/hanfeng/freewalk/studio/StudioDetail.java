package club.hanfeng.freewalk.studio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.studio.StudioBaseAdapter;
import club.hanfeng.freewalk.core.studio.StudioConstants;
import club.hanfeng.freewalk.core.studio.StudioManager;
import club.hanfeng.freewalk.core.studio.data.StudioInfo;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.framework.DataCenter;
import club.hanfeng.freewalk.framework.DataRefreshTask;
import club.hanfeng.freewalk.interfaces.studio.OnRecyclerItemClickListener;
import club.hanfeng.freewalk.interfaces.view.IDataRefreshTask;
import club.hanfeng.freewalk.utils.FreeWalkProgress;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhaoxunyi on 2016/2/1.
 */
public class StudioDetail extends BaseViewGroup {

    private RecyclerView recycleView;
    private StudioBaseAdapter adapter;
    private int taskId;

    public StudioDetail(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.studio_detail;
    }

    @Override
    public void onInitChildren() {
        recycleView = (RecyclerView) getRootView().findViewById(R.id.rv_direct);

        adapter = new StudioBaseAdapter(getContext());
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycleView.setAdapter(adapter);
        adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                Intent intent = new Intent(getContext(), PictureViewerActivity.class);
                intent.putExtra(StudioConstants.EXTRA_TYPE_STUDIO, adapter.getItemt(position));
                getContext().startActivity(intent);
            }
        });
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public List<IDataRefreshTask> getDataRefreshTasks() {
        List<IDataRefreshTask> list = new ArrayList<>();
        list.add(new DataRefreshTask(taskId, 0));
        return list;
    }

    @Override
    public void onRequestLoadData(int key) {
        if (key == StudioConstants.STUDIO_TASKID) {
            DataCenter.getInstance().registerListener(StudioConstants.STUDIO_TASKID, this);
            StudioManager.getInstance().getAllStudios(getContext(), new FindListener<StudioInfo>() {
                @Override
                public void onSuccess(List<StudioInfo> list) {
                    adapter.setData(list);
                    FreeWalkProgress.dismiss(getContext());
                }

                @Override
                public void onError(int i, String s) {
                    FreeWalkProgress.dismiss(getContext());
                }
            });
        }

    }

    @Override
    public void onDataChange(int key) {
        if (key == StudioConstants.STUDIO_TASKID) {
            onRequestLoadData(key);
        }
    }
}
