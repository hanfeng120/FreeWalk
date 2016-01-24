package club.hanfeng.freewalk.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import club.hanfeng.freewalk.R;

/**
 * Created by HanFeng on 2016/1/23.
 */
public class HomeTopBarAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> data = new ArrayList<>();

    public HomeTopBarAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.home_topbar_item, null);
        }
        TextView itemContent = (TextView) convertView.findViewById(R.id.tv_item);
        itemContent.setText(getItem(position));
        itemContent.setTag(position);
        return convertView;
    }
}
