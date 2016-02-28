package club.hanfeng.freewalk.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xutils.x;

import java.util.List;

/**
 * 景点详情头部的图片列表
 * <p/>
 * Created by HanFeng on 2015/10/22.
 */
public class ScenePagerAdapter extends PagerAdapter {

    private Context context;
    private List<String> data;

    public ScenePagerAdapter(Context context, List<String> list) {
        this.context = context;
        this.data = list;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size() * 1000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % data.size();
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        x.image().bind(imageView, data.get(position));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
