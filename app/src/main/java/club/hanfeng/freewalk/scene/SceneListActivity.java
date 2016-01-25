package club.hanfeng.freewalk.scene;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.scene.SceneListBaseAdapter;
import club.hanfeng.freewalk.core.scene.SceneManager;
import club.hanfeng.freewalk.core.scene.data.SceneListItemInfo;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.mainpage.MainPageActivity;
import club.hanfeng.freewalk.mainpage.MainPageConstants;
import cn.bmob.v3.listener.FindListener;

public class SceneListActivity extends BaseActivity {

    private ListView listView;
    private SceneListBaseAdapter adapter;

    @Override
    protected boolean initBackActionBar() {
        setTitle("选择参观景区");
        return true;
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_scene_list;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected void initTopBar() {

    }

    @Override
    protected void initContent() {
        listView = (ListView) findViewById(R.id.listview);

        requestData();
        initListener();
    }

    private void requestData() {
        SceneManager.getInstance().getSceneList(getContext(), new FindListener<SceneListItemInfo>() {
            @Override
            public void onSuccess(List<SceneListItemInfo> list) {
                adapter = new SceneListBaseAdapter(getContext(), list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter == null) {
                    return;
                }
                FreeWalkApplication.setSid(adapter.getItem(position).getSid(), adapter.getItem(position).getCityCode());
                Intent intent = new Intent(getContext(), MainPageActivity.class);
                intent.putExtra(MainPageConstants.EXTRA_TYPE_SCENE_LIST_NAME, adapter.getItem(position).getName());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void refreshView(int viewId) {

    }

    @Override
    public void onClick(View v) {

    }
}
