package club.hanfeng.freewalk.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.mainpage.MainPageActivity;
import club.hanfeng.freewalk.user.LoginActivity;
import club.hanfeng.freewalk.utils.sp.SpConstants;
import club.hanfeng.freewalk.utils.sp.SpUtils;
import cn.bmob.v3.BmobUser;


public class SplashActivity extends BaseActivity {

    private SharedPreferences mPrep;
    private long startTime, endTime;
    private String imgPath;//图片链接
    private String imgUrl;//点击打开浏览器的地址
    private ImageView imgLoad;
    private boolean isFirst;
    private static final int WHAT_ENTER = 1;
    private static final int WHAT_ENTER_DELAYED = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_ENTER:
                    if (isFirst) {
                        startActivity(new Intent(getContext(), GuideActivity.class));
                        finish();
                    } else {
                        checkLoginState();
                    }
                    break;
                case WHAT_ENTER_DELAYED:
                    if (isFirst) {
                        startActivity(new Intent(getContext(), GuideActivity.class));
                        finish();
                    } else {
                        checkLoginState();
                    }
            }
        }
    };

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_splash;
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
        imgLoad = (ImageView) findViewById(R.id.img_sp_net);
    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {
        startTime = System.currentTimeMillis();
        isFirst = SpUtils.getInstance(getContext()).getValue(SpConstants.IS_FIRST, true);
        getDataFromNet();
    }

    @Override
    protected void refreshView(int viewId) {

    }

    /**
     * 从网络上获取闪屏页面的信息
     */
    public void getDataFromNet() {

        try {
            Thread.sleep((long) (Math.random() * 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        if (spendTime < 4000) {
            handler.sendEmptyMessageDelayed(WHAT_ENTER_DELAYED, 4000 - spendTime);
        } else {
            handler.sendEmptyMessage(WHAT_ENTER);
        }

    }

    private void checkLoginState() {
        if (BmobUser.getCurrentUser(getContext()) == null) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        } else {
            startActivity(new Intent(getContext(), MainPageActivity.class));
        }
        finish();
    }

    @Override
    public void onClick(View v) {

    }
}
