package club.hanfeng.freewalk.comments;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.comments.CommentBaseAdapter;
import club.hanfeng.freewalk.core.comments.CommentConstants;
import club.hanfeng.freewalk.core.comments.CommentManager;
import club.hanfeng.freewalk.core.comments.data.CommentInfo;
import club.hanfeng.freewalk.framework.BaseViewGroup;
import club.hanfeng.freewalk.framework.DataRefreshTask;
import club.hanfeng.freewalk.interfaces.view.IDataRefreshTask;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by zhaoxunyi on 2016/2/2.
 */
public class CommentDetail extends BaseViewGroup {

    private CommentBaseAdapter adapter;
    private String id;

    public CommentDetail(Context context) {
        super(context);
    }

    @Override
    public int getRootViewResId() {
        return R.layout.comment_detail;
    }

    @Override
    public void onInitChildren() {
        ListView listView = (ListView) getRootView().findViewById(R.id.listview);
        adapter = new CommentBaseAdapter(getContext());
        listView.setAdapter(adapter);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<IDataRefreshTask> getDataRefreshTasks() {
        List<IDataRefreshTask> list = new ArrayList<>();
        list.add(new DataRefreshTask(CommentConstants.TASK_ID_COMMENT, 0));
        return list;
    }

    @Override
    public void onRequestLoadData(int key) {
        CommentManager.getInstance().loadAllComments(getContext(), id, new FindListener<CommentInfo>() {
            @Override
            public void onSuccess(List<CommentInfo> list) {
                adapter.setData(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
