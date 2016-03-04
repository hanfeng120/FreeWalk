package club.hanfeng.freewalk.guide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.core.guide.SplashInfo;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.mainpage.MainPageActivity;
import club.hanfeng.freewalk.user.LoginActivity;
import club.hanfeng.freewalk.utils.sp.SpConstants;
import club.hanfeng.freewalk.utils.sp.SpUtils;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;


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

    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {
        startTime = System.currentTimeMillis();
        isFirst = SpUtils.getInstance(getContext()).getValue(SpConstants.IS_FIRST, true);
        checkAppState();
    }

    @Override
    protected void refreshView(int viewId) {

    }

    private void checkAppState() {
        BmobQuery<SplashInfo> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(getContext(), new FindListener<SplashInfo>() {
            @Override
            public void onSuccess(List<SplashInfo> list) {
                if (list != null && list.size() > 0) {
                    if (list.get(0).isOpen()) {
                        enterMainPage();
                    } else {
                        showAlertDialog(list.get(0).getMessage(), true);
                    }
                } else {
                    showAlertDialog("应用出错了", true);
                }
            }

            @Override
            public void onError(int i, String s) {
                showAlertDialog("网络连接错误，请重试", false);
            }
        });

    }

    private void showAlertDialog(String message, final boolean isConnected) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提示").setMessage(message).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setPositiveButton(isConnected ? "确定" : "重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isConnected) {
                    finish();
                } else {
                    checkAppState();
                }
            }
        }).setCancelable(false).show();
    }

    private void enterMainPage() {
        endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        if (spendTime < 4000) {
            handler.sendEmptyMessageDelayed(WHAT_ENTER, 4000 - spendTime);
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
