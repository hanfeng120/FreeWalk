package club.hanfeng.freewalk.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.bean.SceneListInfo;

/**
 * 景区服务景点列表 Adapter
 * <p/>
 * Created by HanFeng on 2015/10/22.
 */
public class ServerPageLvBaseAdapter extends BaseAdapter {

    private Context context;
    private List<SceneListInfo> data;

    public ServerPageLvBaseAdapter(Context context, List<SceneListInfo> list) {
        this.context = context;
        this.data = list;
    }

    @Override
    public int getCount() {
        return data.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_lv_sp, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_item_lv_sp);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvStar = (TextView) convertView.findViewById(R.id.tv_star);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SceneListInfo sceneListInfo = getItem(position);

        x.image().bind(viewHolder.imageView, sceneListInfo.imgPath);
        viewHolder.tvName.setText(sceneListInfo.name);
        viewHolder.tvStar.setText(String.format("%s人去过", sceneListInfo.star));
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView tvName, tvStar;
    }

}
