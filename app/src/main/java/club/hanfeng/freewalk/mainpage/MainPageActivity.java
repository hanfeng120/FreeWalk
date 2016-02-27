package club.hanfeng.freewalk.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.mainpage.content.MainPageContent;
import club.hanfeng.freewalk.mainpage.topbar.MainPageTopBar;
import club.hanfeng.freewalk.utils.OutputUtils;

public class MainPageActivity extends BaseActivity {

    private static final int WHAT_UPDATE = 1;//应用更新
    private static final int WHAT_EXIT = 2;

    private MainPageContent mainPageContent;
    private MainPageBottomBar bottomBar;
    private MainPageTopBar mainPageTopBar;

    private boolean isExit = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_UPDATE:

                    break;
                case WHAT_EXIT:
                    isExit = false;
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main_page;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected void initIntentData() {
        handlerIntent();
    }

    @Override
    protected void initTopBar() {
        mainPageTopBar = new MainPageTopBar(getContext());
        mainPageTopBar.setRootView(findViewById(R.id.fl_topbar));
        mainPageTopBar.init();
    }

    @Override
    protected void initContent() {
        mainPageContent = new MainPageContent(getContext());
        mainPageContent.setRootView(findViewById(R.id.fl_content));
        mainPageContent.init();
    }

    @Override
    protected void initBottomBar() {
        bottomBar = new MainPageBottomBar(getContext());
        bottomBar.createRootView(null);
        bottomBar.init();
    }

    @Override
    protected void initOtherView() {
        ((FrameLayout) findViewById(R.id.fl_bottom_bar)).addView(bottomBar.getRootView());
    }

    @Override
    protected void initData() {
        bottomBar.setOnBottomBarSelectedListeners(mainPageTopBar);
        bottomBar.setOnBottomBarSelectedListeners(mainPageContent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handlerIntent();
    }

    @Override
    protected void refreshView(int viewId) {

    }

    private void handlerIntent() {
        Intent fromIntent = getIntent();
        if (fromIntent != null) {
            Intent intent = new Intent();
            switch (fromIntent.getIntExtra(MainPageConstants.EXTRA_TYPE_FROM, -1)) {
                case MainPageConstants.EXTRA_TYPE_FROM_SCENE_LIST:
                    setTopBarInfo(fromIntent.getStringExtra(MainPageConstants.EXTRA_TYPE_SCENE_LIST_SCENE_NAME), fromIntent.getStringExtra(MainPageConstants.EXTRA_TYPE_SCENE_LIST_CITY_NAME));
                    break;
            }
        }
    }

    private void setTopBarInfo(String sceneName, String cityName) {
        mainPageTopBar.setTopBarInfo(sceneName, cityName);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (isExit) {
                    finish();
                } else {
                    OutputUtils.toastShort(this, "再按一次退出应用");
                    isExit = true;
                    handler.sendEmptyMessageDelayed(WHAT_EXIT, 2000);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
