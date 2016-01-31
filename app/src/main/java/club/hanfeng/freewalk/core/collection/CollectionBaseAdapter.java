package club.hanfeng.freewalk.core.collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.collection.data.UserCollection;
import club.hanfeng.freewalk.core.serverpage.data.SceneListInfo;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanFeng on 2016/1/31.
 */
public class CollectionBaseAdapter extends BaseAdapter {

    private Context context;
    private List<UserCollection> data;
    private LayoutInflater inflater;

    public CollectionBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<UserCollection> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return getType(position);
    }

    @Override
    public UserCollection getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case CollectionConstants.TYPE_SCENE:
                convertView = refreshTypeScene(position, convertView);
                break;
            case CollectionConstants.TYPE_TRAVELS:

                break;
        }
        return convertView;
    }

    private View refreshTypeScene(int position, View convertView) {
        SceneHolder holder = null;
        if (convertView == null) {
            holder = new SceneHolder();
            convertView = inflater.inflate(R.layout.item_collection_scene, null);
            holder.sceneView = (ImageView) convertView.findViewById(R.id.iv_scene);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.wanted);
            convertView.setTag(holder);
        } else {
            holder = (SceneHolder) convertView.getTag();
        }
        loadSceneData(position, holder);
        return convertView;
    }

    private void loadSceneData(int position, final SceneHolder holder) {
        UserCollection collection = getItem(position);
        CollectionManager.getInstance().loadSceneInfo(context, collection.getId(), new FindListener<SceneListInfo>() {
            @Override
            public void onSuccess(List<SceneListInfo> list) {
                if (list != null && list.size() > 0) {
                    x.image().bind(holder.sceneView, list.get(0).imgPath);
                    holder.title.setText(list.get(0).name);
                    holder.content.setText(list.get(0).star + "人想去");
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private int getType(int position) {
        if (CollectionConstants.TYPE_COLLECTION_SCENE.equals(getItem(position).getType())) {
            return CollectionConstants.TYPE_SCENE;
        } else if (CollectionConstants.TYPE_COLLECTION_STUDIO.equals(getItem(position).getType())) {
            return CollectionConstants.TYPE_STUDIO;
        } else if (CollectionConstants.TYPE_COLLECTION_TRAVELS.equals(getItem(position).getType())) {
            return CollectionConstants.TYPE_TRAVELS;
        } else {
            return -1;
        }
    }

    class SceneHolder {
        TextView title;
        TextView content;
        ImageView sceneView;
    }


}
