package club.hanfeng.freewalk.photo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.photo.PhotoBaseAdapter;
import club.hanfeng.freewalk.core.photo.PhotoManager;
import club.hanfeng.freewalk.core.photo.data.PhotoInfo;
import club.hanfeng.freewalk.core.utils.FreeWalkToast;
import club.hanfeng.freewalk.framework.BaseActivity;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

public class PhotoActivity extends BaseActivity {

    private PhotoBaseAdapter adapter;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_photo;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected boolean initBackActionBar() {
        setTitle("相册");
        return true;
    }

    @Override
    protected void initTopBar() {

    }

    @Override
    protected void initContent() {
        ListView listView = (ListView) findViewById(R.id.listview);
        adapter = new PhotoBaseAdapter(getContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    private void showConfirmDialog(final PhotoInfo info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("删除照片").setMessage("你确定删除吗？").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PhotoInfo photoInfo = new PhotoInfo();
                photoInfo.setObjectId(info.getObjectId());
                photoInfo.delete(getContext(), new DeleteListener() {
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
        PhotoManager.getInstance().loadAllPhotos(getContext(), new FindListener<PhotoInfo>() {
            @Override
            public void onSuccess(List<PhotoInfo> list) {
                adapter.setData(list);
            }

            @Override
            public void onError(int i, String s) {

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
