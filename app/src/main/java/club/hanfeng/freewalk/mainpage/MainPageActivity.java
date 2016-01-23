package club.hanfeng.freewalk.mainpage;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.mainpage.content.MainPageContent;
import club.hanfeng.freewalk.utils.OutputUtils;

public class MainPageActivity extends BaseActivity {

    private static final int REQUEST_USERPAGE = 1;
    private static final int REQUEST_FINDPAGE = 2;
    private static final int WHAT_UPDATE = 1;//应用更新
    private static final int WHAT_EXIT = 2;
    private MainPageContent mainPageContent;
    private MainPageBottomBar bottomBar;
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
    protected int getContentViewResId() {
        return R.layout.activity_main_page;
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
        mainPageContent = new MainPageContent(getContext());
        mainPageContent.createRootView(null);
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
        ((FrameLayout) findViewById(R.id.fl_content)).addView(mainPageContent.getRootView());
        ((FrameLayout) findViewById(R.id.fl_bottom_bar)).addView(bottomBar.getRootView());
    }

    @Override
    protected void initData() {
        mainPageContent.setOnPageChangeListener(bottomBar.getOnPageChangeListener());
        bottomBar.setOnBottomBarSelectedListeners(mainPageContent.getOnBottomBarSelectedListener());
    }

    @Override
    protected void refreshView(int viewId) {

    }

    @Override
    public void onClick(View v) {

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
}
