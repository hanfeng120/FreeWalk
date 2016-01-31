package club.hanfeng.freewalk.core.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.xutils.x;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.photo.data.PhotoInfo;

/**
 * Created by HanFeng on 2016/1/31.
 */
public class PhotoBaseAdapter extends BaseAdapter {

    private Context context;
    private List<PhotoInfo> data;
    private LayoutInflater inflater;

    public PhotoBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<PhotoInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public PhotoInfo getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_photo, null);
            holder.ivContent = (ImageView) convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PhotoInfo photoInfo = getItem(position);

        holder.ivContent.setImageResource(R.mipmap.default_photo);
        x.image().bind(holder.ivContent, photoInfo.getPhotoUrl());

        return convertView;
    }

    static class ViewHolder {
        ImageView ivContent;
    }
}
