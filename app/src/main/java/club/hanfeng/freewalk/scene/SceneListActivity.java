package club.hanfeng.freewalk.scene;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.FreeWalkApplication;
import club.hanfeng.freewalk.core.scene.SceneListBaseAdapter;
import club.hanfeng.freewalk.core.scene.SceneManager;
import club.hanfeng.freewalk.core.scene.data.SceneListItemInfo;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.framework.DataCenter;
import club.hanfeng.freewalk.mainpage.MainPageActivity;
import club.hanfeng.freewalk.mainpage.MainPageConstants;
import club.hanfeng.freewalk.utils.OutputUtils;
import cn.bmob.v3.listener.FindListener;

public class SceneListActivity extends BaseActivity {

    private ListView listView;
    private EditText searchContent;
    private SceneListBaseAdapter adapter;

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
        searchContent = (EditText) findViewById(R.id.et_content);
        findViewById(R.id.iv_back).setOnClickListener(getOnClickListener());
        findViewById(R.id.tv_search).setOnClickListener(getOnClickListener());
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
                OutputUtils.toastShort(getContext(), "数据加载失败...");
            }
        });
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).getType() == 0) {
                    return;
                }
                FreeWalkApplication.setSid(adapter.getItem(position).getSid(), adapter.getItem(position).getCityCode(), adapter.getItem(position).getCityName(), adapter.getItem(position).getName());
                Intent intent = new Intent(getContext(), MainPageActivity.class);
                intent.putExtra(MainPageConstants.EXTRA_TYPE_FROM, MainPageConstants.EXTRA_TYPE_FROM_SCENE_LIST);
                intent.putExtra(MainPageConstants.EXTRA_TYPE_SCENE_LIST_SCENE_NAME, adapter.getItem(position).getName());
                intent.putExtra(MainPageConstants.EXTRA_TYPE_SCENE_LIST_CITY_CODE, adapter.getItem(position).getCityCode());
                intent.putExtra(MainPageConstants.EXTRA_TYPE_SCENE_LIST_CITY_NAME, adapter.getItem(position).getCityName());
                startActivity(intent);
                DataCenter.getInstance().notifyDataChanged(MainPageConstants.TASK_ID_SCENE_CHANGED);
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
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                break;
        }
    }
}
