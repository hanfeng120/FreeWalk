package club.hanfeng.freewalk.collection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.collection.CollectionBaseAdapter;
import club.hanfeng.freewalk.core.collection.CollectionConstants;
import club.hanfeng.freewalk.core.collection.CollectionManager;
import club.hanfeng.freewalk.core.collection.data.UserCollection;
import club.hanfeng.freewalk.core.scene.SceneConstants;
import club.hanfeng.freewalk.core.utils.FreeWalkToast;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.scene.SceneActivity;
import club.hanfeng.freewalk.utils.OutputUtils;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

public class CollectionActivity extends BaseActivity {

    private CollectionBaseAdapter adapter;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_collection;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected boolean initBackActionBar() {
        setTitle("藏宝箱");
        return true;
    }

    @Override
    protected void initTopBar() {

    }

    @Override
    protected void initContent() {
        ListView listView = (ListView) findViewById(R.id.listview);
        adapter = new CollectionBaseAdapter(getContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (adapter.getItemViewType(position)) {
                    case CollectionConstants.TYPE_SCENE:
                        Intent intent = new Intent(getContext(), SceneActivity.class);
                        intent.putExtra(SceneConstants.EXTRA_ID, adapter.getItem(position).getId());
                        startActivity(intent);
                        break;
                    case CollectionConstants.TYPE_TRAVELS:
                        break;
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showConfirmDialog(adapter.getItem(position));
                return true;
            }
        });
    }

    private void showConfirmDialog(final UserCollection info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("删除收藏").setMessage("你确定删除吗？").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserCollection userCollection = new UserCollection();
                userCollection.setObjectId(info.getObjectId());
                userCollection.delete(getContext(), new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        initData();
                        FreeWalkToast.shortToast("删除成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        FreeWalkToast.shortToast("删除失败");
                    }
                });
            }
        }).show();
    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {
        CollectionManager.getInstance().loadAllCollections(getContext(), new FindListener<UserCollection>() {
            @Override
            public void onSuccess(List<UserCollection> list) {
                adapter.setData(list);
            }

            @Override
            public void onError(int i, String s) {
                OutputUtils.toastShort(getContext(), "数据加载失败");
            }
        });
    }

    @Override
    protected void refreshView(int viewId) {

    }

    @Override
    public void onClick(View v) {

    }
}
