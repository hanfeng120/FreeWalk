package club.hanfeng.freewalk.core.download;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.maps.offlinemap.OfflineMapCity;

import java.util.List;

import club.hanfeng.freewalk.R;

/**
 * Created by zhaoxunyi on 2016/2/1.
 */
public class MapListBaseAdapter extends BaseAdapter {

    private Context context;
    private List<OfflineMapCity> data;
    private LayoutInflater inflater;

    public MapListBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<OfflineMapCity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public OfflineMapCity getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_map_list, null);
            holder.cityName = (TextView) convertView.findViewById(R.id.city_name);
            holder.citySize = (TextView) convertView.findViewById(R.id.city_size);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OfflineMapCity offlineMapCity = getItem(position);

        holder.cityName.setText(offlineMapCity.getCity());
        holder.citySize.setText(offlineMapCity.getSize() + "");

        return convertView;
    }

    class ViewHolder {
        TextView cityName;
        TextView citySize;
    }

}
