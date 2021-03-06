package club.hanfeng.freewalk.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.ArrayList;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.bean.SceneInfo;

/**
 * Created by HanFeng on 2015/11/26.
 */
public class SceneBaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SceneInfo> data;
    private LayoutInflater inflater;

    public SceneBaseAdapter(Context context, ArrayList<SceneInfo> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public SceneInfo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            switch (getItemViewType(position)) {
                case 0://景区中英文名称
                    convertView = inflater.inflate(R.layout.type_scene_zero, parent, false);
                    holder.tvName = (TextView) convertView.findViewById(R.id.tv_type_scene_name);
                    holder.tvNameEN = (TextView) convertView.findViewById(R.id.tv_type_scene_name_en);
                    holder.ivStar = (ImageView) convertView.findViewById(R.id.iv_type_scene_star);
                    holder.tvComment = (TextView) convertView.findViewById(R.id.tv_type_scene_comment);
                    break;
                case 1://列表头
                    convertView = inflater.inflate(R.layout.type_scene_one, parent, false);
                    holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_type_scene_one_title);
                    break;
                case 2://文本内容
                    convertView = inflater.inflate(R.layout.type_scene_two, parent, false);
                    holder.tvContent = (TextView) convertView.findViewById(R.id.tv_type_scene_two_content);
                    break;
                case 3://图片内容
                    convertView = inflater.inflate(R.layout.type_scene_three, parent, false);
                    holder.ivContent = (ImageView) convertView.findViewById(R.id.iv_type_scene_three);
                    break;
                case 4://门票
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SceneInfo sceneInfo = getItem(position);

        switch (getItemViewType(position)) {
            case 0:
                holder.tvName.setText(sceneInfo.name);
                holder.tvNameEN.setText(sceneInfo.nameEN);
                holder.tvComment.setText(sceneInfo.comment);
                break;
            case 1:
                holder.tvTitle.setText(sceneInfo.name);
                break;
            case 2:
                holder.tvContent.setText(sceneInfo.content);
                break;
            case 3:
                x.image().bind(holder.ivContent, sceneInfo.imagePath);
                break;
            case 4:
                break;
        }

        return convertView;
    }

    static class ViewHolder{
        public TextView tvName;
        public TextView tvNameEN;
        public ImageView ivStar;
        public TextView tvComment;
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView ivContent;
    }

}
