package club.hanfeng.freewalk.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import club.hanfeng.freewalk.base.BasePage;

/**
 * Created by HanFeng on 2015/10/22.
 */
public class MainPagerAdapter extends PagerAdapter {

    private Context context;
    private List<BasePage> data;

    public MainPagerAdapter(Context context, List<BasePage> list){
        this.context = context;
        this.data = list;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(data.get(position).rootView);
        Log.i("TAG","instantiateItem()");
        return data.get(position).rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.i("TAG","destroyItem()");
        container.removeView((View) object);
    }
}
