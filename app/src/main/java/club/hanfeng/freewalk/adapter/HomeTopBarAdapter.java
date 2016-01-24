package club.hanfeng.freewalk.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.tabbar.data.TabBarType;

/**
 * Created by HanFeng on 2016/1/23.
 */
public class HomeTopBarAdapter extends BaseAdapter {

    private Context context;
    private List<TabBarType> data = new ArrayList<>();
    private int selected;

    public HomeTopBarAdapter(Context context, List<TabBarType> data) {
        this.context = context;
        this.data = data;
    }

    public void setSelected(int position) {
        this.selected = position;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public TabBarType getItem(int position) {
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
        itemContent.setText(getItem(position).getName());
        if (position == selected) {
            itemContent.setTextColor(Color.BLUE);
        } else {
            itemContent.setTextColor(Color.BLACK);
        }
        return convertView;
    }
}
