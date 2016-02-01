package club.hanfeng.freewalk.download;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.download.DownloadPagerAdapter;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.framework.BaseViewGroup;

/**
 * Created by zhaoxunyi on 2016/2/1.
 */
public class DownloadMapActivity extends BaseActivity {

    private DownloadPagerAdapter adapter;
    private List<BaseViewGroup> list = new ArrayList<>();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_download_map;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected void initTopBar() {

    }

    @Override
    protected void initContent() {
        MapManagerPage mapManagerPage = new MapManagerPage(getContext());
        mapManagerPage.createRootView(null);
        mapManagerPage.init();

        MapListPage mapListPage = new MapListPage(getContext());
        mapListPage.createRootView(null);
        mapListPage.init();

        list.add(mapManagerPage);
        list.add(mapListPage);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.topbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpage);
        adapter = new DownloadPagerAdapter();
        viewPager.setAdapter(adapter);
        adapter.setData(list);
    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void refreshView(int viewId) {

    }

    @Override
    public void onClick(View v) {

    }
}
