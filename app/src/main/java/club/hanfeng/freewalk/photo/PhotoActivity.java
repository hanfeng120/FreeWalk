package club.hanfeng.freewalk.photo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.photo.PhotoBaseAdapter;
import club.hanfeng.freewalk.core.photo.PhotoManager;
import club.hanfeng.freewalk.core.photo.data.PhotoInfo;
import club.hanfeng.freewalk.framework.BaseActivity;
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
