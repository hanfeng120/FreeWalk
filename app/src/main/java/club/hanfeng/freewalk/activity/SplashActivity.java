package club.hanfeng.freewalk.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import org.xutils.x;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.mainpage.MainPageActivity;


public class SplashActivity extends Activity {

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
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainPageActivity.class));
                        finish();
                    }
                    break;
                case WHAT_ENTER_DELAYED:
                    if (isFirst) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainPageActivity.class));
                        finish();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startTime = System.currentTimeMillis();
        mPrep = getSharedPreferences("config", MODE_PRIVATE);
        isFirst = mPrep.getBoolean("isFirst", true);

        imgLoad = (ImageView) findViewById(R.id.img_sp_net);

        getDataFromNet();

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

    public void updateUI() {
        x.image().bind(imgLoad, imgPath);
    }


}
