package club.hanfeng.freewalk.mainpage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.mainpage.content.MainPageContent;
import club.hanfeng.freewalk.mainpage.topbar.MainPageTopBar;
import club.hanfeng.freewalk.utils.OutputUtils;
import club.hanfeng.freewalk.utils.map.AMapUtils;

public class MainPageActivity extends BaseActivity {

    private static final int REQUEST_USERPAGE = 1;
    private static final int REQUEST_FINDPAGE = 2;
    private static final int WHAT_UPDATE = 1;//应用更新
    private static final int WHAT_EXIT = 2;

    private MainPageContent mainPageContent;
    private MainPageBottomBar bottomBar;
    private MainPageTopBar mainPageTopBar;
    private MapView mapView;

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
        initAMap(savedInstanceState);
    }

    private void initAMap(Bundle savedInstanceState) {
        MapsInitializer.sdcardDir = AMapUtils.getSdCacheDir(getContext());
        mapView = (MapView) mainPageContent.getAMapView();
        mapView.onCreate(savedInstanceState);
        mainPageContent.setAMap(mapView.getMap());
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
        mainPageTopBar.setOnHomeTopBarSelectedListener(mainPageContent.getOnHomeTopBarSelectedListener());
        bottomBar.setOnBottomBarSelectedListeners(mainPageTopBar);
        bottomBar.setOnBottomBarSelectedListeners(mainPageContent);
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

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
