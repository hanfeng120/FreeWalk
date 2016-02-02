package club.hanfeng.freewalk.core.comments;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.comments.data.CommentInfo;
import club.hanfeng.freewalk.core.utils.ImageLoaderUtil;

/**
 * Created by zhaoxunyi on 2016/2/2.
 */
public class CommentBaseAdapter extends BaseAdapter {

    private Context context;
    private List<CommentInfo> data;
    private LayoutInflater inflater;

    public CommentBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<CommentInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public CommentInfo getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_comment, null);
            holder.portrait = (ImageView) convertView.findViewById(R.id.portrait);
            holder.tvNickName = (TextView) convertView.findViewById(R.id.nick_name);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
            holder.tvComment = (TextView) convertView.findViewById(R.id.comment);
            holder.tvTime = (TextView) convertView.findViewById(R.id.time);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_comment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        refreshData(position, holder);

        return convertView;
    }

    private void refreshData(int position, ViewHolder holder) {
        CommentInfo commentInfo = getItem(position);
        ImageLoaderUtil.displayImageInRound(commentInfo.getPortraitUrl(), holder.portrait);
        holder.tvNickName.setText(commentInfo.getNickName());
        holder.tvComment.setText(commentInfo.getComment());
        holder.tvTime.setText(commentInfo.getCreatedAt());
        holder.ratingBar.setRating(commentInfo.getStars());
        if (!TextUtils.isEmpty(commentInfo.getImgUrl())) {
            x.image().bind(holder.imageView, commentInfo.getImgUrl());
        }
    }

    static class ViewHolder {
        TextView tvNickName;
        TextView tvComment;
        TextView tvTime;
        RatingBar ratingBar;
        ImageView portrait;
        ImageView imageView;

    }
}
