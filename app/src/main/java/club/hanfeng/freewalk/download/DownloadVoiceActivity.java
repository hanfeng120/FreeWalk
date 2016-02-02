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

public class DownloadVoiceActivity extends BaseActivity {

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
        VoiceManagerPage voiceManagerPage = new VoiceManagerPage(getContext());
        voiceManagerPage.createRootView(null);
        voiceManagerPage.init();

        VoiceListPage voiceListPage = new VoiceListPage(getContext());
        voiceListPage.createRootView(null);
        voiceListPage.init();

        list.add(voiceManagerPage);
        list.add(voiceListPage);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpage);
        adapter = new DownloadPagerAdapter();
        viewPager.setAdapter(adapter);
        adapter.setData(list);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.topbar);
        DownloadTopBar downloadTopBar = new DownloadTopBar(getContext());
        downloadTopBar.setOnClickListener(getOnClickListener());
        downloadTopBar.createRootView(null);
        downloadTopBar.init();
        downloadTopBar.initWithTitle(null, viewPager);
        frameLayout.addView(downloadTopBar.getRootView());
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
        if (v.getId() == R.id.menu_back) {
            finish();
        }
    }
}
