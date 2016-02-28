package club.hanfeng.freewalk.core.serverpage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.serverpage.data.ServerInfo;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class ServerPageBaseAdapter extends BaseAdapter {

    private List<ServerInfo> data;
    private Context context;

    public ServerPageBaseAdapter(Context context, List<ServerInfo> list) {
        this.context = context;
        this.data = list;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gl_sp, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_item_gl_sp);
        TextView textView = (TextView) convertView.findViewById(R.id.tv_item_gl_sp);
        x.image().bind(imageView, data.get(position).getImgPath());
        textView.setText(data.get(position).getName());
        return convertView;
    }
}
