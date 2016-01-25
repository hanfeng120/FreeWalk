package club.hanfeng.freewalk.core.scene;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.scene.data.SceneListItemInfo;

/**
 * Created by zhaoxunyi on 2016/1/25.
 */
public class SceneListBaseAdapter extends BaseAdapter {

    private Context context;
    private List<SceneListItemInfo> data;
    private LayoutInflater inflater;

    public SceneListBaseAdapter(Context context, List<SceneListItemInfo> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return SceneConstants.SCENE_LIST_VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public SceneListItemInfo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            switch (getItemViewType(position)) {
                case SceneConstants.SCENE_LIST_ITEM_TYPE_TITLE:
                    convertView = inflater.inflate(R.layout.item_scene_list_title, null);
                    holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                    break;
                case SceneConstants.SCENE_LIST_ITEM_TYPE_CONTENT:
                    convertView = inflater.inflate(R.layout.item_scene_list_content, null);
                    holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        refreshData(position, holder);
        return convertView;
    }

    private void refreshData(int position, ViewHolder holder) {
        SceneListItemInfo itemInfo = getItem(position);
        switch (getItemViewType(position)) {
            case SceneConstants.SCENE_LIST_ITEM_TYPE_TITLE:
                holder.tvContent.setText(itemInfo.getName());
                break;
            case SceneConstants.SCENE_LIST_ITEM_TYPE_CONTENT:
                holder.tvContent.setText(itemInfo.getName());
                break;
        }
    }

    public static class ViewHolder {
        TextView tvContent;
    }
}
