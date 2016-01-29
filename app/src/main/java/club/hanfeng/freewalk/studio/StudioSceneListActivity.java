package club.hanfeng.freewalk.studio;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.serverpage.data.SceneListInfo;
import club.hanfeng.freewalk.core.studio.StudioManager;
import club.hanfeng.freewalk.framework.BaseActivity;
import cn.bmob.v3.listener.FindListener;

public class StudioSceneListActivity extends BaseActivity {

    private SceneListAdapter adapter;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_studio_scene_list;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected boolean initBackActionBar() {
        setTitle("请选择景点");
        return true;
    }

    @Override
    protected void initTopBar() {

    }

    @Override
    protected void initContent() {
        adapter = new SceneListAdapter();
        ListView listView = (ListView) findViewById(R.id.list_scene);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), StudioStep2Activity.class);
                intent.putExtra("name", adapter.getItem(position).name);
                intent.putExtra("id", adapter.getItem(position).id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {
        StudioManager.getInstance().getSceneList(getContext(), new FindListener<SceneListInfo>() {

            @Override
            public void onSuccess(List<SceneListInfo> list) {
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

    class SceneListAdapter extends BaseAdapter {

        private List<SceneListInfo> data;
        private LayoutInflater inflater;

        public SceneListAdapter() {
            inflater = LayoutInflater.from(getContext());
        }

        public void setData(List<SceneListInfo> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public SceneListInfo getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = null;
            if (convertView == null) {
                textView = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
                convertView = textView;
            }
            if (textView != null) {
                textView.setText(getItem(position).name);
            }
            return convertView;
        }
    }
}
