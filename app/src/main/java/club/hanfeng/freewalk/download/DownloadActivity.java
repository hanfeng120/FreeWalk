package club.hanfeng.freewalk.download;

import android.content.Intent;
import android.view.View;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseActivity;

public class DownloadActivity extends BaseActivity {

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_download;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected boolean initBackActionBar() {
        return true;
    }

    @Override
    protected void initTopBar() {
        setTitle("离线下载");
    }

    @Override
    protected void initContent() {
        findViewById(R.id.map_content).setOnClickListener(getOnClickListener());
        findViewById(R.id.voice_content).setOnClickListener(getOnClickListener());
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
        switch (v.getId()) {
            case R.id.map_content:
                startActivity(new Intent(getContext(), DownloadMapActivity.class));
                break;
            case R.id.voice_content:
                startActivity(new Intent(getContext(), DownloadVoiceActivity.class));
                break;
        }
    }
}
