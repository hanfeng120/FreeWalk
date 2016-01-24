package club.hanfeng.freewalk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.x;

import java.util.ArrayList;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.bean.DirectSendBean;
import club.hanfeng.freewalk.interfaces.studio.OnRecyclerItemClickListener;

/**
 * Created by HanFeng on 2015/12/2.
 */
public class DirectRecyclerAdapter extends RecyclerView.Adapter<DirectRecyclerAdapter.DirectViewHolder> {

    private Context context;
    private ArrayList<DirectSendBean> data;
    private LayoutInflater inflater;

    public DirectRecyclerAdapter(Context context, ArrayList<DirectSendBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DirectRecyclerAdapter.DirectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_rv_direct, parent, false);
        return new DirectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DirectRecyclerAdapter.DirectViewHolder holder, final int position) {
        DirectSendBean directSendInfo = getItemt(position);


        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerItemClickListener.onItemClick(holder.rootView, position, position);
            }
        });

        x.image().bind(holder.imageView, directSendInfo.imagePath);
        if (!TextUtils.isEmpty(directSendInfo.introduce)) {
            holder.tvIntroduce.setVisibility(View.VISIBLE);
            holder.tvIntroduce.setText(directSendInfo.introduce);
        } else {
            holder.tvIntroduce.setVisibility(View.GONE);
        }
        holder.tvTime.setText(directSendInfo.time + position);
        holder.tvSceneName.setText(directSendInfo.sceneName);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public DirectSendBean getItemt(int position) {
        return data.get(position);
    }

    static class DirectViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout rootView;
        public ImageView imageView;
        public TextView tvTime;
        public TextView tvSceneName;
        public TextView tvIntroduce;

        public DirectViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.ll_item_gl_direct);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_gl_direct);
            tvTime = (TextView) itemView.findViewById(R.id.tv_item_gl_direct_time);
            tvSceneName = (TextView) itemView.findViewById(R.id.tv_item_gl_direct_name);
            tvIntroduce = (TextView) itemView.findViewById(R.id.tv_item_gl_introduce);
        }
    }

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

}
